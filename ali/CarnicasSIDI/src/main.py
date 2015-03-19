# -*- coding: iso-8859-15 -*-
import Variables
import Clientes
import Productos
import Facturas
from datetime import date

import sys
from PyQt4 import QtGui
from principal import Ui_Principal
from listadoClientes import Ui_ListadoClientes
from detalleClientes import Ui_DetalleClientes
from listadoProductos import Ui_ListadoProductos
from detalleProductos import Ui_DetalleProductos
from formularioFactura import Ui_FormularioFactura
from listadoFacturasDiarias import Ui_ListadoFacturasDiarias
from formularioFinal import Ui_FormularioFinal
from formularioSeleccionFechas import Ui_FormularioSeleccionFechas

'''
Created on 30/12/2011

@author: kaos
'''
#Ventana principal
class formularioPrincipal(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_Principal()
        self.ui.setupUi(self)
    
    #Lanzamos el men� para seleccionar cliente    
    def lanzarClientes(self):
        self.close()
        Variables.Variables.seleccionandoClientes=0
        self.ui=formularioSeleccionCliente()
        self.ui.showNormal()
    
    #Lanzamos el men� para seleccionar productos
    def lanzarProductos(self):
        self.close()
        self.ui=formularioSeleccionProducto()
        self.ui.show()
    
    #Lanzamos el menu para la facturacion diaria
    def lanzarDiaria(self):
        self.close()
        self.ui=formularioFacturasDiarias()
        self.ui.show()
    
    #Lanzamos la facturacion mensual    ##########################################
                                        ################TERMINAR##################
                                        ##########################################
    def lanzarMensual(self):
        
        self.close()
        Variables.Variables.seleccionandoClientes=2
        self.ui=formularioSeleccionCliente()
        self.ui.show()
    
class formularioSeleccionCliente(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_ListadoClientes()
        self.ui.setupUi(self)
    
    #Lanzamos el formulario del cliente
    def lanzarDetalleCliente(self):
        #Numero de clientes que tenemos en el widget
        count = self.ui.listWidget.count()
        
        itemseleccionado=-1
        #para cada elemento
        for i in range(count):
            #Lo capturamos
            item=self.ui.listWidget.item(i)
            #Vemos si esta seleccionado
            if(self.ui.listWidget.isItemSelected(item)):
                #Si esto pasa, rompemos el bucle
                itemseleccionado=item
                break
        
        if(itemseleccionado!=-1):
            #Si hemos obtenido alguno, seleccionamos el nombre
            nombre=itemseleccionado.text().toUtf8().data()
            #Si se trata de un nuevo cliente
            if(nombre=="Nuevo Cliente"):
                #Creamos un cliente vacio
                Variables.Variables.cliente=Clientes.Clientes("","","","","","","")
            else:
                #Obtenemos el cliente
                Variables.Variables.cliente=Variables.Variables.getCliente(nombre)
            #Lanzamos el menú con el cliente seleccionado
            self.close()
            if(Variables.Variables.seleccionandoClientes==0):
                self.ui=formularioDetalleCliente()
            elif (Variables.Variables.seleccionandoClientes==1) :
                self.ui=formularioFactura()
            else:
                self.ui=formularioSeleccionFechas()
            self.ui.show()
    
    #Lanzamos el menu anterior
    def lanzarPrincipal(self):
        self.close()
        self.ui=formularioPrincipal()
        self.ui.show()
    
    #Si estamos seleccionando un cliente(No creandolo)
    def lanzarDetalleFactura(self):
        #Hacemos casi lo mismo pero sin la opcion de crear cliente
        count = self.ui.listWidget.count()
        
        itemseleccionado=0
        for i in range(count):
            item=self.ui.listWidget.item(i)
            if(self.ui.listWidget.isItemSelected(item)):
                itemseleccionado=item
                break
        
        if(itemseleccionado!=0):
            nombre=itemseleccionado.text().toUtf8().data()
            print ("El item seleccionado es %s",nombre)
            Variables.Variables.cliente=Variables.Variables.getCliente(nombre)
        self.close()
        if(Variables.Variables.seleccionandoClientes==0):
            self.ui=formularioDetalleCliente()
        elif (Variables.Variables.seleccionandoClientes==1) :
            self.ui=formularioFactura()
        else:
            self.ui=formularioSeleccionFechas()
        #Devolvemos la variable global a 0
        Variables.Variables.seleccionandoClientes=0
        self.ui.show()
        
 
class formularioDetalleCliente(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_DetalleClientes()
        self.ui.setupUi(self)
    
    #Lanzamos el borrado del cliente y volvemos al menu de seleccion de cliente
    def borrarCliente(self):
        self.close()
        Variables.Variables.eliminarCliente(Variables.Variables.cliente)
        self.ui=formularioSeleccionCliente()
        self.ui.show()
    
    #Boton Volver, nos devuelve a la lista
    def lanzarListarClientes(self):
        self.close()
        self.ui=formularioSeleccionCliente()
        self.ui.show()
    
    #Obtenemos todos los datos del formulario
    def lanzarModificarCliente(self):
        self.close()
        Variables.Variables.cliente.nombre=str(self.ui.lineaNombre.text())
        Variables.Variables.cliente.detalles=str(self.ui.lineaDetalle.text())
        Variables.Variables.cliente.ciudad=str(self.ui.lineaCIudad.text())
        Variables.Variables.cliente.direccion=str(self.ui.lineaDireccion.text())
        Variables.Variables.cliente.cif=str(self.ui.lineaDNI.text())
        Variables.Variables.cliente.pais=str(self.ui.lineaPais.text())
        Variables.Variables.cliente.CP=str(self.ui.lineaCP.text())
        #Modificamos el cliente realmente
        Variables.Variables.modificarCliente(Variables.Variables.cliente)
        
        self.ui=formularioSeleccionCliente()
        self.ui.show()
        
class formularioSeleccionProducto(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_ListadoProductos()
        self.ui.setupUi(self)
    
    #Boton volver
    def lanzarPrincipal(self):
        self.close()
        self.ui=formularioPrincipal()
        self.ui.show()
    
    ##lanzar el formulario de detalle de productos
    def lanzarDetalleProductos(self):
        #Basicamente lo mismo que con los clientes
        count = self.ui.listWidget.count()
        itemseleccionado=0
        for i in range(count):
            item=self.ui.listWidget.item(i)
            if(self.ui.listWidget.isItemSelected(item)):
                itemseleccionado=item
                break
        
        if(itemseleccionado!=0):
            nombre=itemseleccionado.text().toUtf8().data()
            print ("El item seleccionado es %s",nombre)
            if(nombre=="Nuevo Producto"):
                Variables.Variables.producto=Productos.Productos("","","","","")
            else:
                Variables.Variables.producto=Variables.Variables.getProducto(nombre)
            self.close()
            self.ui=formularioDetalleProducto()
            self.ui.show()
            
    def lanzarFormularioFactura(self):
        #Lo mismo que la seleccion de clientes
        count = self.ui.listWidget.count()
        itemseleccionado=0
        for i in range(count):
            item=self.ui.listWidget.item(i)
            if(self.ui.listWidget.isItemSelected(item)):
                itemseleccionado=item
                break
        if(itemseleccionado!=0):
            nombre=itemseleccionado.text().toUtf8().data()
            print ("El item seleccionado es %s",nombre)
            add=True
            for linea in Variables.Variables.factura.lista:
                if(nombre==linea.nombre):
                    add=False
            
            if(add):
                Variables.Variables.nuevaLineaFactura(nombre)
            
            self.close()
            self.ui=formularioFactura()
            Variables.Variables.seleccionandoProductos=0
            self.ui.show()

class formularioSeleccionFechas(QtGui.QMainWindow):
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_FormularioSeleccionFechas()
        self.ui.setupUi(self)
    
    def lanzarImprimir(self):
        desde=self.ui.Calendario1.selectedDate()
        hasta=self.ui.Calendario2.selectedDate()
        desde=date(desde.year(),desde.month(),desde.day())
        hasta=date(hasta.year(),hasta.month(),hasta.day())
        todas=True

        if(self.ui.Button2.isChecked()):
            todas=False

        Variables.Variables.imprimirFacturaMensual(desde,hasta,todas)
    
    def lanzarPrincipal(self):
        self.close()
        self.ui=formularioPrincipal()
        self.ui.show()
    

        
class formularioDetalleProducto(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_DetalleProductos()
        self.ui.setupUi(self)
    
    #Borramos el producto
    def borrarProductos(self):
        self.close()
        Variables.Variables.eliminarProducto(Variables.Variables.producto)
        self.ui=formularioSeleccionProducto()
        self.ui.show()
    
    #Boton Volver
    def lanzarListarProductos(self):
        self.close()
        self.ui=formularioSeleccionProducto()
        self.ui.show()
    
    #Modificar los detalles del producto  
    def lanzarModificarProductos(self):
        self.close()
        Variables.Variables.producto.nombre=str(self.ui.lineaNombre.text())
        Variables.Variables.producto.detalle=str(self.ui.lineaDetalle.text())
        Variables.Variables.producto.precio=str(self.ui.lineaPrecio.text())
        if(self.ui.radioButton.isChecked()):
            Variables.Variables.producto.unidades=1
        else:
            Variables.Variables.producto.unidades=0
        Variables.Variables.modificarProducto(Variables.Variables.producto)
        self.ui=formularioSeleccionProducto()
        self.ui.show()
        
class formularioFacturasDiarias(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_ListadoFacturasDiarias()
        self.ui.setupUi(self)
    
    #Boton Volver
    def lanzarPrincipal(self):  
        self.close()
        self.ui=formularioPrincipal()
        self.ui.show()
    
    def lanzarImprimir(self):
        
        #Buscamos la factura
        count = self.ui.tableWidget.rowCount()
        
        itemseleccionado=-1
        
        for i in range(count):
            item = self.ui.tableWidget.item(i,1)
            if(self.ui.tableWidget.isItemSelected(item)):
                itemseleccionado=i
                break
        
        #Obtenemos el nombre del cliente
        itemnombre=self.ui.tableWidget.item(itemseleccionado,0)
        #obtenemos el id de la factura
        itemseleccionado=self.ui.tableWidget.item(itemseleccionado,1)
  
        print itemseleccionado.text().toUtf8()
        
        #Cargamos la factura,el cliente e imprimimos la factura
        Variables.Variables.factura=Variables.Variables.getFactura(itemseleccionado.text().toUtf8().toLong()[0])
        Variables.Variables.cliente=Variables.Variables.getCliente(itemnombre.text().toUtf8())
        Variables.Variables.imprimirFacturaDiaria()   
        
        #Como el anterior
    def lanzarDetallesFactura(self):
        
        count = self.ui.tableWidget.rowCount()
        
        itemseleccionado=-1
        
        for i in range(count):
            item = self.ui.tableWidget.item(i,1)
            if(self.ui.tableWidget.isItemSelected(item)):
                itemseleccionado=i
                break

        itemnombre=self.ui.tableWidget.item(itemseleccionado,0)
        itemseleccionado=self.ui.tableWidget.item(itemseleccionado,1)
        

        print itemseleccionado.text().toUtf8()
    
        Variables.Variables.factura=Variables.Variables.getFactura(itemseleccionado.text().toUtf8().toLong()[0])
        Variables.Variables.cliente=Variables.Variables.getCliente(itemnombre.text().toUtf8())
        self.close()
        self.ui=formularioFactura()
        
        self.ui.show()
        
        #Creamos un nuevo cliente, una nueva factura y lanzamos
    def lanzarNuevaFactura(self):
        self.close()
        Variables.Variables.factura=Facturas.Facturas("","","")
        Variables.Variables.cliente=Clientes.Clientes("","","","","","","")
        self.ui=formularioFactura()
        self.ui.show()
        

class formularioFactura(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_FormularioFactura()
        self.ui.setupUi(self)   
    

    def lazarFinFactura(self):
        if(Variables.Variables.cliente.idCliente!=""):
            self.close()
            #Obtenemos las observaciones
            Variables.Variables.factura.observaciones=self.ui.texto.toPlainText() 
            self.ui=formularioFinal()
            self.ui.show()
        
    #Boton volver
    def lazarVolver(self):
        self.close()
        self.ui=formularioFacturasDiarias()
        self.ui.show()
    
    #Boton seleccionar cliente
    def lazarSeleccionarCliente(self):
        self.close()
        Variables.Variables.seleccionandoClientes=1
        self.ui=formularioSeleccionCliente()
        self.ui.show()
    
    
    def lazarBorrarProducto(self,fila,columna):
        
        #Si pincha en la ultima linea, se creq un linea nueva
        if(fila==(self.ui.tableWidget.rowCount()-1)):
            print "Nueva linea"
            self.close()
            #Seleccionamos el producto para esa nueva linea
            Variables.Variables.seleccionandoProductos=1
            self.ui=formularioSeleccionProducto()
            self.ui.show()
        else:
            #Pincha en la columna 6(icono)
            #Borramos el producto de la factura
            if(columna==6):
                print "Borrar"
                nombre=self.ui.tableWidget.item(fila,1).text().toUtf8()
                self.close()
                for linea in Variables.Variables.factura.lista:
                    if (linea.nombre==nombre):
                        Variables.Variables.factura.lista.remove(linea)
                        break
                #Recargamos el formulario
                self.ui=Ui_FormularioFactura()
                self.ui.setupUi(self)
                self.show()
                
           
    def lazarModificarProducto(self,fila,columna):
        #si modifica el albaran, buscamos el producto y lo cambiarmos
        if(columna==0):
            nombre=self.ui.tableWidget.item(fila,1).text().toUtf8()
            self.close()
            for linea in Variables.Variables.factura.lista:
                if (linea.nombre==nombre):
                    try:
                        linea.albaran=int(self.ui.tableWidget.item(fila,0).text())
                        break
                    except ValueError:
                        print "Oops!  That was no valid number.  Try again..."
            #Recargamos el formulario
            self.ui=Ui_FormularioFactura()
            self.ui.setupUi(self)
            self.show()        
        
        #modifica la cantidad lo mismo
        elif(columna==2):
            nombre=self.ui.tableWidget.item(fila,1).text().toUtf8()
            self.close()
            for linea in Variables.Variables.factura.lista:
                if (linea.nombre==nombre):
                    linea.cantidad=float(self.ui.tableWidget.item(fila,2).text())
                    break
        
            self.ui=Ui_FormularioFactura()
            self.ui.setupUi(self)
            self.show()
        
        #modifica el total
        elif(columna==3):
            nombre=self.ui.tableWidget.item(fila,1).text().toUtf8()
            
            self.close()
            for linea in Variables.Variables.factura.lista:
                if (linea.nombre==nombre):
                    #Creamos un descuento basandonos en el total actual
                    antiguoTotal=float(self.ui.tableWidget.item(fila,5).text())
                    nuevoTotal=linea.cantidad*float(self.ui.tableWidget.item(fila,3).text())
                    linea.descuento=100-(nuevoTotal*100)/antiguoTotal
                    break
        
            self.ui=Ui_FormularioFactura()
            self.ui.setupUi(self)
            self.show()
            
        elif(columna==4):
            #Modifica el descuento
            nombre=self.ui.tableWidget.item(fila,1).text().toUtf8()
            self.close()
            for linea in Variables.Variables.factura.lista:
                if (linea.nombre==nombre):
                    linea.descuento=float(self.ui.tableWidget.item(fila,4).text())
        
            self.ui=Ui_FormularioFactura()
            self.ui.setupUi(self)
            self.show()

class formularioFinal(QtGui.QMainWindow):
    #Creacion del formulario (Igual en todos)
    #Creamos un Widget
    #Le asignamos el correspondiente Ui
    #Lo colocamos
    #definimos los slots
    def __init__(self,parent=None):
        QtGui.QWidget.__init__(self,parent)
        self.ui=Ui_FormularioFinal()
        self.ui.setupUi(self)
    
    #Borra la factura
    def lazarBorrarFactura(self):
        
        Variables.Variables.borrarFactura()
        self.close()
        self.ui=formularioPrincipal()
        self.ui.show()
    
    #Vuelve atras
    def lazarCancelar(self):
        
        self.close()
        self.ui=formularioFactura()
        self.ui.show()
    
    #Acepta e imprime      
    def lazarImprimir(self):
        
        Variables.Variables.guardarFactura()
        Variables.Variables.imprimirFacturaDiaria()
    
    #Acepta
    def lazarAceptar(self):
        
        Variables.Variables.guardarFactura()
        self.close()
        self.ui=formularioFacturasDiarias()
        self.ui.show()
    
    def lazarCambiarFecha(self):
        
        fecha=self.ui.Calendario.selectedDate()
        fecha=date(fecha.year(),fecha.month(),fecha.day())
        Variables.Variables.factura.fecha=fecha
        self.ui.labelFecha.setText(Variables.Variables.factura.fecha.strftime("%d-%m-%Y"))
        
if __name__ == '__main__':
    #Creaci�n de las variables globales
    Variables.Variables()
    #Se crea la interfaz de usuario
    app = QtGui.QApplication(sys.argv)
    #Creamos el formulario principal
    myapp = formularioPrincipal()
    #Mostramos el formulario
    myapp.show()
    #Ejecutamos
    sys.exit(app.exec_())

    
    