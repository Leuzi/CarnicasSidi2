'''
Created on 30/12/2011

@author: kaos
'''

class Productos(object):
    '''
    classdocs
    '''


    def __init__(self,idProducto,nombre,detalle,precio,unidades):
        '''
        Constructor
        '''
        self.idProducto=idProducto;
        self.nombre=nombre;
        self.detalle=detalle;
        self.precio=precio;
        self.unidades=unidades;