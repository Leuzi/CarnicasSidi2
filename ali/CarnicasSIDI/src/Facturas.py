'''
Created on 30/12/2011

@author: kaos
'''

class Facturas(object):
    '''
    classdocs
    '''
    #Constructor (lista vacia en el principio
    def __init__(self,idFactura,Fecha,Observaciones,lista=[]):
        '''
        Constructor
        '''
        self.idFactura=idFactura
        self.fecha=Fecha
        self.observaciones=Observaciones
        self.lista=lista#Lista de LineaFactura
        
    def anadirLinea(self,linea):
        self.lista.append(linea)
    
    def eliminarLinea(self,linea):
        self.lista.remove(linea)
    
    def modificarLinea(self,lineaold,lineanew):
        self.lista.remove(lineaold)
        self.lista.append(lineanew)