# -*- coding: iso-8859-15 -*-
import MySQLdb
import Clientes
import Facturas
import Productos
import LineaFactura
import time
from datetime import timedelta
import calendar
from datetime import date
from reportlab.pdfgen import canvas
from reportlab.lib.units import cm
from reportlab.lib.pagesizes import A4
from reportlab.platypus import PageBreak
import os

'''
Created on 30/12/2011

@author: kaos
'''

class Variables:
    '''
    classdocs
    '''
    #Listas globales
    clientes=[]
    productos=[]
    facturas=[]
    cliente=Clientes.Clientes()
    producto=0
    factura=0
    cliente=0
    #Flags para la seleccion de clientes
    seleccionandoClientes=0
    seleccionandoProductos=0
    
    def __init__(self):
        '''
        Constructor
        '''
        Variables.cargarClientes()    #Cargamos clientes
        Variables.cargarProductos()   #Cargamos productos
        Variables.cargarFacturas()    #Cargamos facturas
    @classmethod  
    def cargarClientes(self):
        #Creamos conexion para la base de datos
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        #Obtenemos el un cursor para la base de datos
        cursor=db.cursor()
        #Obtenemos los diferentes nombres para los clientes
        sql='Select nombre from clientes'
        #Ejecutamos la sentencia
        cursor.execute(sql)
        #Obtenemos el resultado
        resultado=cursor.fetchall()
        print 'Nombre de los clientes'
        #definimos la lista de clientes
        Variables.clientes=[]
        #Para cada linea en resultado
        for registro in resultado:
            print 'Cargar usuario'
            #Cargamos el registro
            print registro[0]
            cursor.execute('Select * from clientes where nombre= %s',registro[0])
            resultado2=cursor.fetchall()
            #Obtenemos datos
            idCliente=resultado2[0][0]
            nombreCliente=resultado2[0][1]
            detalleCliente=resultado2[0][2]
            direccionCliente=resultado2[0][3]
            CIFCliente=resultado2[0][4]
            ciudadCliente=resultado2[0][5]
            paisCliente=resultado2[0][6]
            CPCliente=resultado2[0][7]
            #Creamos un cliente
            cliente = Clientes.Clientes(idCliente,nombreCliente,detalleCliente,direccionCliente,
                              CIFCliente,ciudadCliente,paisCliente,CPCliente)
            #Lo aÃ±adimos a la lista
            Variables.clientes.append(cliente)
        print 'Fin de la carga de clientes'
        #Cerramos la conexion
        db.commit()
        db.close()
    @classmethod 
    def getNombresClientes(self):
        
        listaNombres=[]
        
        for cliente in Variables.clientes:
            listaNombres.append(cliente.nombre)
        
        return listaNombres
    
    @classmethod
    def getNombresProductos(self):
        
        listaNombres=[]
        
        for producto in Variables.productos:
            listaNombres.append(producto.nombre)
        
        return listaNombres
    @classmethod 
    def cargarProductos(self):
        #Hacemos lo mismo con los productos
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        cursor=db.cursor()
        sql='Select nombre from Productos'
        cursor.execute(sql)
        resultado=cursor.fetchall()
        Variables.productos=[]
        for registro in resultado:
            print 'Cargando productos'
            print registro[0]
            cursor.execute('Select * from productos where nombre=%s',registro[0])
            resultado2=cursor.fetchall()
            idProducto=resultado2[0][0]
            nombreProducto=resultado2[0][1]
            detalleProducto=resultado2[0][2]
            precioProducto=resultado2[0][3]
            unidadesProducto=resultado2[0][4]
            producto = Productos.Productos(idProducto,nombreProducto,detalleProducto,
                                          precioProducto,unidadesProducto)
            Variables.productos.append(producto)
        print 'Fin de la carga de productos'
        db.commit()
        db.close()
    
    @classmethod 
    def cargarFacturas(self):
        #Mas complicado para las facturas
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        cursor=db.cursor()
        Variables.facturas=[]
        #Para cada uno de los clientes
        for cliente in Variables.clientes:
            #Obtenemos el id del cliente
            idCliente = cliente.idCliente
            #Seleccionamos los id de las facturas que tiene
            cursor.execute('Select idFactura from posee where idCliente=%s',idCliente)
            idFacturas=cursor.fetchall()
            #Para cada factura
            for idFactura in idFacturas:
                #Obtenemos los datos de la factura
                cursor.execute('Select * from Facturas where idFacturas=%s',idFactura[0])
                facturas=cursor.fetchall()
                idFactura=facturas[0][0]
                fechaFactura=facturas[0][1]
                observaciones=facturas[0][2]
                #Creamos una lista, por ahora vacia, para obtener todos los datos de las filas
                lista=[]
                #Seleccionamos todos los detalles de la fila
                cursor.execute('Select idProductos,cantidad,precio,descuento,albaran from contiene where idFacturas=%s',idFactura)
                lineas=cursor.fetchall()
                for linea in lineas:
                    idProducto=linea[0]
                    cantidadProducto=linea[1]
                    precioProducto=linea[2]
                    descuentoProducto=linea[3]
                    albaranProducto=linea[4]
                    #Obtenemos el nombre del producto
                    cursor.execute('Select nombre from productos where idProductos=%s',idProducto)
                    detalleProducto=cursor.fetchall()
                    nombreProducto=detalleProducto[0][0]
                    #Creamos una nueva linea
                    nuevaLinea = LineaFactura.LineaFactura(albaranProducto,nombreProducto
                                                           ,precioProducto,descuentoProducto,cantidadProducto)
                    lista.append(nuevaLinea)
                factura=Facturas.Facturas(idFactura,fechaFactura,observaciones,lista)
                cliente.facturas.append(factura)                           
                Variables.facturas.append(factura)
        db.commit()
        db.close()
    ##fin del cargarFacturas
    
    @classmethod  
    def getCliente(self,nombre):
        
        valor=0
        for cliente in Variables.clientes:
            if(cliente.nombre==nombre):
                valor=cliente
        return valor
    
    @classmethod
    def getProducto(self,nombre):
        
        valor=0
        for producto in Variables.productos:
            if(producto.nombre==nombre):
                valor=producto
        
        return valor
    
    @classmethod
    def getFactura(self,idFactura):
        
        valor=0
        for factura in Variables.facturas:
            if(factura.idFactura==idFactura):
                valor=factura
        
        return valor
    
    @classmethod
    def getClienteId(self,idCliente):
        
        valor=0
        for cliente in Variables.clientes:
            if(cliente.idCliente==idCliente):
                valor=cliente
        
        return valor
    @classmethod
    def getProductoId(self,idProducto):
        valor=0
        for producto in Variables.productos:
            if(producto.idProducto==idProducto):
                valor=producto
        
        return valor
    
    @classmethod  
    def getFacturasEntre(self,nombre,desde,hasta):
        
        listaFacturas=self.getFacturas(nombre)
        
        
        nuevaLista=[]
        
        for factura in listaFacturas:
            if(factura.fecha>=desde and factura.fecha<=hasta):
                nuevaLista.append(factura)
        return nuevaLista
    
    @classmethod  
    def getFacturas(self,nombre):
        
        cliente=self.getCliente(nombre)
        return cliente.getFacturas()
    
    @classmethod
    def eliminarCliente(self,cliente):
        Variables.clientes.remove(cliente)
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        cursor=db.cursor()
        for factura in cliente.facturas:
            idFactura=factura.idFactura
            cursor.execute('DELETE FROM contiene WHERE idFacturas=%s',idFactura)
            cursor.execute('DELETE FROM posee WHERE idFactura=%s',idFactura)
        
        cursor.execute('DELETE FROM facturas WHERE idFacturas=%s',cliente)
        cursor.execute('DELETE FROM clientes WHERE idClientes=%s',cliente.idCliente)
        db.commit()
        db.close()
    
    @classmethod
    def eliminarProducto(self,producto):
        Variables.productos.remove(producto)
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        cursor=db.cursor()
        cursor.execute('DELETE FROM productos WHERE idProductos=%s',producto.idProducto)
    
    @classmethod  
    def modificarCliente(self,cliente):
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        cursor=db.cursor()
        if(cliente.idCliente==""):
            print "Nuevo Cliente"
            insertar=True
            for clientein in Variables.clientes:
                if(clientein.nombre==cliente.nombre):
                    insertar=False
                    break
            if(insertar):
                cursor.execute('INSERT INTO Clientes(nombre,detalle,direccion,cif,ciudad,pais) VALUES(%s,%s,%s,%s,%s,%s)',(cliente.nombre,cliente.detalles,cliente.direccion,cliente.cif,cliente.ciudad,cliente.pais))
            else:
                print "Existe un cliente con el mismo nombre"
            db.commit()
            Variables.cargarClientes()
        else:
            print "Modificar cliente"
            insertar=True
            for clientein in Variables.clientes:
                if clientein.nombre==cliente.nombre:
                    insertar=False
                    break
            if(insertar):
                clienteold=Variables.getClienteId(cliente.idCliente)
                Variables.clientes.remove(clienteold)
                Variables.clientes.append(cliente)
            
                cursor.execute('UPDATE clientes SET nombre=%s,detalle=%s,direccion=%s,cif=%s,ciudad=%s,pais=%s,CP=%s WHERE (idClientes=%s)',(
                           cliente.nombre,cliente.detalles,cliente.direccion,cliente.cif,cliente.ciudad,cliente.pais,cliente.CP,cliente.idCliente))
            else:
                print "Existe un cliente con el mismo nombre"
            db.commit()
        db.close()
    
    @classmethod
    def modificarProducto(self,producto):
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        cursor=db.cursor()
        if(producto.idProducto==""):
            insertar=True
            for productodentro in Variables.productos:
                if(productodentro.nombre==producto.nombre):
                    insertar=False
                    break
            if(insertar):
                print "Nuevo Producto"
                if(producto.precio==""):
                    producto.precio=0
                precio=float(producto.precio)
                cursor.execute('INSERT INTO Productos(nombre,detalle,precio,unidades) VALUES(%s,%s,%s,%s)',(producto.nombre,producto.detalle,precio,producto.unidades))
                db.commit()
                Variables.cargarProductos()
            else:
                print "Existe un producto con el mismo nombre"
        else:
            print "Modificar Producto"
            modificar=True
            for productodentro in Variables.productos:
                if(productodentro.nombre==producto.nombre):
                    modificar=False
                    break
            if(modificar):
                productoold=Variables.getProductoId(producto.idProducto)
                Variables.productos.remove(productoold)
                Variables.productos.append(producto)
                precio=float(producto.precio)
                cursor.execute('UPDATE productos SET nombre=%s,detalle=%s,precio=%s,unidades=%s WHERE (idProductos=%s)',(producto.nombre,producto.detalle,precio,producto.unidades,producto.idProducto))
            else:
                print "Existe un producto con el mismo nombre"
            db.commit()
        db.close()
    
    @classmethod
    def nuevaLineaFactura(self,producto):
        precio=Variables.getProducto(producto)
        precio=precio.precio
        nuevalinea=LineaFactura.LineaFactura("",producto,precio,0,0)
        Variables.factura.lista.append(nuevalinea)

    @classmethod
    def borrarFactura(self):
        if(Variables.factura.idFactura==""):
            print "Borrando nada de la BD"
            Variables.factura=Facturas.Facturas("","","")
            Variables.cliente=Clientes.Clientes("","","","","","","")
        
        else:
            db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
            cursor=db.cursor()
            cursor.execute("DELETE FROM contiene WHERE idFacturas=%s",Variables.factura.idFactura)
            cursor.execute("DELETE FROM posee WHERE idFactura=%s",Variables.factura.idFactura)
            cursor.execute("DELETE FROM facturas WHERE idFacturas=%s",Variables.factura.idFactura)
            db.commit()
            db.close()
            for cliente in Variables.clientes:
                for factura in cliente.facturas:
                    if(factura.idFactura==Variables.factura.idFactura):
                        cliente.facturas.remove(factura)
            for factura in Variables.facturas:
                if(factura.idFactura==Variables.factura.idFactura):
                    Variables.facturas.remove(factura)
    
    @classmethod                
    def guardarFactura(self):
        db=MySQLdb.connect(host='localhost',user='root',passwd='root',db='mydb')
        cursor=db.cursor()
        
        if(Variables.factura.idFactura==""):
            Variables.factura.fecha=date.today()
            dia=date.today()
            cursor.execute('INSERT INTO facturas(fecha,observaciones) VALUES(%s,%s)',(dia.isoformat(),Variables.factura.observaciones))
            cursor.execute('SELECT MAX(idFacturas) FROM facturas')
            idFactura=cursor.fetchall()
            idFactura=idFactura[0][0]
            Variables.factura.idFactura=idFactura
            Variables.cliente.facturas.append(Variables.factura)
            Variables.facturas.append(Variables.factura)
            cursor.execute('INSERT INTO posee VALUES(%s,%s)',(Variables.cliente.idCliente,Variables.factura.idFactura))
            
        else:
            cursor.execute('UPDATE facturas SET observaciones=%s,fecha=%s WHERE (idFacturas=%s)',(Variables.factura.observaciones,Variables.factura.fecha,Variables.factura.idFactura))
            cursor.execute('DELETE FROM contiene WHERE idFacturas=%s',Variables.factura.idFactura)
            
            for factura in Variables.facturas:
                if(factura.idFactura==Variables.factura.idFactura):
                    Variables.facturas.remove(factura)
                    Variables.facturas.append(Variables.factura)
        
            for cliente in Variables.clientes:
                for factura in cliente.facturas:
                    if(factura.idFactura==Variables.factura.idFactura):
                        Variables.facturas.remove(factura)
                        Variables.facturas.append(Variables.factura)
                        
        for linea in Variables.factura.lista:
            idProducto=Variables.getProducto(linea.nombre)
            idProducto=idProducto.idProducto
            cursor.execute("INSERT INTO contiene VALUES(%s,%s,%s,%s,%s,%s)",(Variables.factura.idFactura,
                           idProducto,linea.cantidad,linea.precio,linea.descuento,linea.albaran))
        
        db.commit()
        db.close()

    @classmethod
    def imprimirFacturaDiaria(self):
        
        numero=len(Variables.factura.lista)
        
               
        final=False
        
        total=0
        hoja=1
        aux = canvas.Canvas("factura.pdf",pagesize=A4)
        while final==False:
            local=0
            aux.setLineWidth(.3)
            aux.setFont('Helvetica',12)   
            
            aux.drawImage("../iconos/logoFactura.png",1*cm, 23*cm, 257, 150)
            aux.setFont('Helvetica',18)   
            aux.drawString(15*cm, 28*cm, "Factura")
            aux.setFont('Helvetica',12) 
            
            
            aux.drawImage("../iconos/bordes.png",11*cm,21*cm,9*cm,6*cm)
            aux.drawString(12.5*cm, 26.8*cm, "Datos Cliente")
            aux.setFont('Helvetica',11) 
            idCliente=unicode("Nº"+str(Variables.cliente.idCliente))
            aux.drawString(18*cm, 26.0*cm,idCliente)
            aux.drawString(12*cm, 25.4*cm,str(Variables.cliente.nombre))
            aux.drawString(12*cm,24.7*cm,"C." +unicode(str(Variables.cliente.direccion)))
            aux.drawString(12*cm,24*cm,unicode(str(Variables.cliente.cif)))
            aux.drawString(12*cm,23.3*cm,str(Variables.cliente.CP))       
            aux.drawString(13.5*cm,23.3*cm,str(Variables.cliente.ciudad))
            aux.drawString(12*cm,22.6*cm,unicode(str(Variables.cliente.pais)))
            aux.drawString(12*cm,21.9*cm,"DNI-CIF "+str(Variables.cliente.cif))
            
            aux.drawImage("../iconos/bordes.png",1*cm,21*cm,5.5*cm,1*cm)
            aux.setFont('Helvetica',10)
            aux.drawString(2.1*cm,21.9*cm,"Factura")
            aux.setFont('Helvetica',11)
            aux.drawString(1.6*cm,21.2*cm,unicode("Nº "+str(Variables.factura.idFactura)))
            aux.drawString(3.8*cm,21.2*cm,unicode(Variables.factura.fecha.strftime("%d-%m-%Y")))
            
            aux.setFont('Helvetica',17)
            
            aux.drawImage("../iconos/bordes2.png",1*cm,5.5*cm,19*cm,15*cm)
            aux.drawString(4.2*cm,20.2*cm,unicode("Conceptos y facturación"))
            
            aux.setFont('Helvetica',12)
            aux.drawImage("../iconos/bordes.png",1*cm,2*cm,11*cm,3*cm)
            aux.drawImage("../iconos/bordes.png",12.5*cm,2*cm,7.5*cm,3*cm)
            aux.drawString(3*cm,4.8*cm,"Observaciones")
            aux.drawString(14*cm,4.8*cm,"Total")
            
            importe=0
            subtotal=0
            
            aux.setFont('Helvetica',12)
            while local<17 and total <numero:
                linea=Variables.factura.lista.pop(0)
                Variables.factura.lista.append(linea)
                local=local+1
                total=total+1
                espacio=18.1*cm-(local*0.7*cm)
                aux.drawString(1.5*cm,espacio,str(linea.albaran))
                aux.drawString(4.3*cm,espacio,str(linea.nombre))
                aux.drawString(10*cm,espacio,unicode(str(linea.precio) + " E"))
                aux.drawString(12.3*cm,espacio,str(linea.cantidad))
                aux.drawString(15.3*cm,espacio,unicode(str(linea.descuento)+" %"))
                subtotal=subtotal+(linea.cantidad*linea.precio)
                importelocal=(linea.cantidad*linea.precio)*((100-linea.descuento)/100)
                importe=importe+importelocal
                aux.drawString(18*cm,espacio,unicode(str(importelocal)+" E"))
            
            if((total)==numero):
                final=True
                #Poner observaciones
                aux.drawString(1.5*cm,4.1*cm,str(Variables.factura.observaciones))
                
                #Poner el total
                aux.drawString(12.9*cm,4.3*cm,str("SUBTOTAL"))
                aux.drawString(18.3*cm,4.3*cm,str(subtotal))
                aux.drawString(12.9*cm,3.5*cm,str("Descuento"))
                aux.drawString(18.3*cm,3.5*cm,str(subtotal-importe))
                aux.drawString(12.9*cm,2.5*cm,str("IMPORTE TOTAL"))
                aux.drawString(18.3*cm,2.5*cm,str(importe))
                
            
            else:
                aux.drawString(2.5*cm,4.8*cm,"Hoja "+str(hoja))
                hoja=hoja+1
                aux.showPage()
            
            
        aux.save()  
        
        os.startfile("factura.pdf")
        
    @classmethod
    def imprimirFacturaMensual(self,desde,hasta,todas):
        meses=['ENERO','FEBRERO','MARZO','ABRIL','MAYO','JUNIO','JULIO','AGOSTO','SEPTIEMBRE','OCTUBRE','NOVIEMBRE','DICIEMBRE']
        if(desde>hasta):
            print "Error en la fecha"
        else:
            if(desde.month==hasta.month and desde.year==hasta.year):
                actual1=desde
                actual2=hasta
            else:
                
                actual1=desde
                dia2=calendar.monthrange(actual1.year, actual1.month)
                dia2=dia2[1]
                actual2=date(actual1.year,actual1.month,dia2)
                
            final=False
    
            
            hoja=1
            
            aux = canvas.Canvas("factura.pdf",pagesize=A4)
            while final==False:
                
                local=0
                
                aux.setLineWidth(.3)
                aux.setFont('Helvetica',12)   
                
                aux.drawImage("../iconos/logoFactura.png",1*cm, 23*cm, 257, 150)
                aux.setFont('Helvetica',18)   
                aux.drawString(15*cm, 28*cm, "Factura")
                aux.setFont('Helvetica',12) 
                
                
                aux.drawImage("../iconos/bordes.png",11*cm,21*cm,9*cm,6*cm)
                aux.drawString(12.5*cm, 26.8*cm, "Datos Cliente")
                aux.setFont('Helvetica',11) 
                idCliente=unicode("Nº"+str(Variables.cliente.idCliente))
                aux.drawString(18*cm, 26.0*cm,idCliente)
                aux.drawString(12*cm, 25.4*cm,str(Variables.cliente.nombre))
                aux.drawString(12*cm,24.7*cm,"C." +unicode(str(Variables.cliente.direccion)))
                aux.drawString(12*cm,24*cm,unicode(str(Variables.cliente.cif)))
                aux.drawString(12*cm,23.3*cm,str(Variables.cliente.CP))       
                aux.drawString(13.5*cm,23.3*cm,str(Variables.cliente.ciudad))
                aux.drawString(12*cm,22.6*cm,unicode(str(Variables.cliente.pais)))
                aux.drawString(12*cm,21.9*cm,"DNI-CIF "+str(Variables.cliente.cif))
                
                aux.drawImage("../iconos/bordes.png",1*cm,21*cm,5.5*cm,1*cm)
                aux.setFont('Helvetica',10)
                aux.drawString(2.1*cm,21.9*cm,"Factura")
                aux.setFont('Helvetica',13)
                aux.drawString(1.8*cm,21.2*cm,unicode(date.today().strftime("%d-%m-%Y")))
                
                aux.setFont('Helvetica',17)
                
                aux.drawImage("../iconos/bordes2.png",1*cm,5.5*cm,19*cm,15*cm)
                aux.drawString(4.2*cm,20.2*cm,unicode("Conceptos y facturación"))
                
                aux.setFont('Helvetica',12)
                aux.drawImage("../iconos/bordes.png",1*cm,2*cm,11*cm,3*cm)
                aux.drawImage("../iconos/bordes.png",12.5*cm,2*cm,7.5*cm,3*cm)
                aux.drawString(3*cm,4.8*cm,"Observaciones")
                aux.drawString(14*cm,4.8*cm,"Total")
                
                importetotal=0
                undia=timedelta(days=1)
                fin=False
                
                while(local<17 and fin==False):
            
                    lista=Variables.getFacturasEntre(Variables.cliente.nombre, actual1, actual2)
                    print actual1.strftime("%d-%m-%Y")
                    print actual2.strftime("%d-%m-%Y")
                    print len(lista)
                    
                    importe=0
                    
                    if(lista!=[]):
                        for factura in lista:
                            if(todas==True):
                                for linea in factura.lista:
                                    importe=importe+(linea.cantidad*linea.precio)*((100-linea.descuento)/100)
                            else:
                                if(factura.observaciones!=""):
                                    primero=factura.observaciones[0]
                                    segundo=factura.observaciones[1]
                                    if(primero=='N'):
                                        if(segundo=='P'):
                                            for linea in factura.lista:
                                                importe=importe+(linea.cantidad*linea.precio)*((100-linea.descuento)/100)
                        importetotal=importetotal+importe
                        local=local+1
                        espacio=18.1*cm-(local*0.7*cm)
                        aux.drawString(4.3*cm,espacio,"TOTAL " + str(meses[(actual1.month)-1]) +" " +str(actual1.year))
                        aux.drawString(18*cm,espacio,unicode(str(importe)+" E"))
                        print str(meses[(actual1.month)-1])
                        
                    actual1=actual2+undia
                    dia2=calendar.monthrange(actual1.year, actual1.month)
                    dia2=dia2[1]
                    actual2=date(actual1.year,actual1.month,dia2)
                    if(actual2>hasta):
                        actual2=hasta
                        
                    if(actual1>hasta):
                        fin=True
                        final=True
                
                if(final==False):
                    aux.drawString(1.5*cm,4.1*cm,str("Hoja ") + str(hoja))
                    hoja=hoja+1                        
  
                    
                else:
                    aux.drawString(1.5*cm,4.1*cm,str("Facturas desde el dia ") + desde.strftime("%d-%m-%Y") + str(" hasta ") +hasta.strftime("%d-%m-%Y"))
                    
                    #Poner el total
                    aux.drawString(12.9*cm,4.3*cm,str("SUBTOTAL"))
                    aux.drawString(18.3*cm,4.3*cm,str("-"))
                    aux.drawString(12.9*cm,3.5*cm,str("Descuento"))
                    aux.drawString(18.3*cm,3.5*cm,str("-"))
                    aux.drawString(12.9*cm,2.5*cm,str("IMPORTE TOTAL"))
                    aux.drawString(18.3*cm,2.5*cm,str(importetotal))
                    
                aux.showPage()
                aux.save()  
            os.startfile("factura.pdf")            
                            
                    