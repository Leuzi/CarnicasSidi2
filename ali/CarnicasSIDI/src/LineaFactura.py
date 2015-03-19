'''
Created on 31/12/2011

@author: kaos
'''

class LineaFactura(object):
    '''
    classdocs
    '''


    def __init__(self,albaran,nombre,precio,descuento,cantidad):
        '''
        Constructor
        '''
        self.albaran=albaran
        self.nombre=nombre
        self.precio=precio
        self.descuento=descuento
        self.cantidad=cantidad
