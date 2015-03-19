# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'C:\Users\kaos\workspace\ali\Ali1\formularioFactura.ui'
#
# Created: Sat Jan 14 04:33:06 2012
#      by: PyQt4 UI code generator 4.9
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui
import Variables

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_FormularioFactura(object):
    def setupUi(self, Form):
        Form.setObjectName(_fromUtf8("Form"))
        Form.resize(800, 600)
        Form.setMinimumSize(QtCore.QSize(800, 600))
        Form.setMaximumSize(QtCore.QSize(800, 600))
        self.frame = QtGui.QFrame(Form)
        self.frame.setGeometry(QtCore.QRect(0, 0, 800, 600))
        self.frame.setMinimumSize(QtCore.QSize(800, 600))
        self.frame.setStyleSheet(_fromUtf8("QFrame{background-color:#fff9a2;}"))
        self.frame.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame.setFrameShadow(QtGui.QFrame.Raised)
        self.frame.setObjectName(_fromUtf8("frame"))
        self.frame_2 = QtGui.QFrame(self.frame)
        self.frame_2.setGeometry(QtCore.QRect(20, 30, 751, 531))
        self.frame_2.setStyleSheet(_fromUtf8("QFrame{background-color:white;\n"
"border: 2px solid black;\n"
"}"))
        self.frame_2.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_2.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_2.setObjectName(_fromUtf8("frame_2"))
        self.buttonFinalizar = QtGui.QPushButton(self.frame_2)
        self.buttonFinalizar.setGeometry(QtCore.QRect(20, 10, 101, 41))
        self.buttonFinalizar.setObjectName(_fromUtf8("buttonFinalizar"))
        self.buttonVolver = QtGui.QPushButton(self.frame_2)
        self.buttonVolver.setGeometry(QtCore.QRect(200,10,101,41))
        self.buttonVolver.setObjectName(_fromUtf8("buttonVolver"))
        self.frame_3 = QtGui.QFrame(self.frame_2)
        self.frame_3.setGeometry(QtCore.QRect(30, 70, 691, 281))
        self.frame_3.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_3.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_3.setObjectName(_fromUtf8("frame_3"))
        self.tableWidget = QtGui.QTableWidget(self.frame_3)
        self.tableWidget.setGeometry(QtCore.QRect(0, 0, 691, 281))
        self.tableWidget.setObjectName(_fromUtf8("tableWidget"))
        self.tableWidget.setColumnCount(7)
        self.tableWidget.setRowCount(0)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(0, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(1, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(2, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(3, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(4, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(5, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(6, item)
        self.frame_6 = QtGui.QFrame(self.frame_2)
        self.frame_6.setGeometry(QtCore.QRect(520, 410, 201, 71))
        self.frame_6.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_6.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_6.setObjectName(_fromUtf8("frame_6"))
        self.labelTotal = QtGui.QLabel(self.frame_6)
        self.labelTotal.setGeometry(QtCore.QRect(10, 12, 181, 41))
        self.labelTotal.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.labelTotal.setObjectName(_fromUtf8("labelTotal"))
        self.label = QtGui.QLabel(self.frame_2)
        self.label.setGeometry(QtCore.QRect(30, 365, 91, 21))
        self.label.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.label.setObjectName(_fromUtf8("label"))
        self.label_2 = QtGui.QLabel(self.frame_2)
        self.label_2.setGeometry(QtCore.QRect(520, 380, 91, 21))
        self.label_2.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.label_3 = QtGui.QLabel(self.frame_2)
        self.label_3.setGeometry(QtCore.QRect(320, 40, 91, 21))
        self.label_3.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.label_3.setObjectName(_fromUtf8("label_3"))
        self.texto = QtGui.QTextEdit(self.frame_2)
        self.texto.setGeometry(QtCore.QRect(30, 390, 441, 111))
        self.texto.setObjectName(_fromUtf8("texto"))
        if(Variables.Variables.factura.idFactura==""):
            self.buttonSeleccionar = QtGui.QPushButton(self.frame_2)
            self.buttonSeleccionar.setGeometry(QtCore.QRect(490, 20, 81, 31))
            self.buttonSeleccionar.setObjectName(_fromUtf8("buttonSeleccionar"))
        self.nombreCliente = QtGui.QLabel(self.frame_2)
        self.nombreCliente.setGeometry(QtCore.QRect(580, 20, 131, 31))
        self.nombreCliente.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.nombreCliente.setObjectName(_fromUtf8("nombreCliente"))

        i=0

        lineas=Variables.Variables.factura.lista
        total=0
        #Obtenemos todas las lineas
        for linea in lineas:

            self.tableWidget.insertRow(i)
            if(linea.albaran=="" and i!=0):
                if(self.tableWidget.item(i-1,0).text()!=""):
                    #Si el albaran esta vacio y el de arriba no, copiamos el albaran
                    linea.albaran=int(self.tableWidget.item(i-1,0).text())
            self.tableWidget.setItem(i,0,QtGui.QTableWidgetItem(str(linea.albaran)))
            self.tableWidget.setItem(i,1,QtGui.QTableWidgetItem(str(linea.nombre)))
            self.tableWidget.item(i,1).setFlags( QtCore.Qt.ItemIsSelectable |  QtCore.Qt.ItemIsEnabled)
            producto=Variables.Variables.getProducto(linea.nombre)
            if(producto.unidades==1):
                #casting a entero
                self.tableWidget.setItem(i,2,QtGui.QTableWidgetItem(str(int(linea.cantidad))))
            else:
                self.tableWidget.setItem(i,2,QtGui.QTableWidgetItem(str(linea.cantidad)))
            self.tableWidget.setItem(i,3,QtGui.QTableWidgetItem(str(linea.precio)))
            self.tableWidget.setItem(i,4,QtGui.QTableWidgetItem(str(linea.descuento)+" %"))
            
            #formula para el precio
            self.tableWidget.setItem(i,5,QtGui.QTableWidgetItem(str((linea.cantidad*linea.precio)*((100-linea.descuento))/100)))
            self.tableWidget.item(i,5).setFlags( QtCore.Qt.ItemIsSelectable |  QtCore.Qt.ItemIsEnabled)
            total=total+float(self.tableWidget.item(i,5).text())
            self.tableWidget.setItem(i,6,QtGui.QTableWidgetItem(QtGui.QIcon("../iconos/menos.png"),""))             
            i=i+1
        self.tableWidget.insertRow(i)
        self.tableWidget.setItem(i,1,QtGui.QTableWidgetItem("Nuevo Producto"))
        self.tableWidget.setItem(i,2,QtGui.QTableWidgetItem(QtGui.QIcon("../iconos/mas.png"),""))
        self.labelTotal.setText(str(total)+" E")   
            
        QtCore.QObject.connect(self.tableWidget, QtCore.SIGNAL(_fromUtf8("cellClicked(int,int)")), Form.lazarBorrarProducto)
        QtCore.QObject.connect(self.tableWidget,QtCore.SIGNAL(_fromUtf8("cellChanged(int,int)")),Form.lazarModificarProducto)
        QtCore.QObject.connect(self.buttonVolver,QtCore.SIGNAL(_fromUtf8("clicked()")),Form.lazarVolver)
        self.retranslateUi(Form)
        
        QtCore.QObject.connect(self.buttonFinalizar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lazarFinFactura)
        if(Variables.Variables.factura.idFactura==""):
            QtCore.QObject.connect(self.buttonSeleccionar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lazarSeleccionarCliente)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        Form.setWindowTitle(QtGui.QApplication.translate("Form", "Form", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonFinalizar.setText(QtGui.QApplication.translate("Form", "Finalizar", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonVolver.setText(QtGui.QApplication.translate("Form", "Volver", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(0)
        item.setText(QtGui.QApplication.translate("Form", "Albaran", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(1)
        item.setText(QtGui.QApplication.translate("Form", "Producto", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(2)
        item.setText(QtGui.QApplication.translate("Form", "Cantidad", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(3)
        item.setText(QtGui.QApplication.translate("Form", "Precio U", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(4)
        item.setText(QtGui.QApplication.translate("Form", "Descuento", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(5)
        item.setText(QtGui.QApplication.translate("Form", "Total", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(6)
        item.setText(QtGui.QApplication.translate("Form", "Icono", None, QtGui.QApplication.UnicodeUTF8))
        
        self.label.setText(QtGui.QApplication.translate("Form", "Observaciones", None, QtGui.QApplication.UnicodeUTF8))
        self.label_2.setText(QtGui.QApplication.translate("Form", "Importe Total", None, QtGui.QApplication.UnicodeUTF8))
        self.label_3.setText(QtGui.QApplication.translate("Form", "Factura N", None, QtGui.QApplication.UnicodeUTF8))
        
        if(Variables.Variables.factura.idFactura==""):
            self.buttonSeleccionar.setText(QtGui.QApplication.translate("Form", "Seleccionar \n"
" Cliente", None, QtGui.QApplication.UnicodeUTF8))
        if(Variables.Variables.cliente.nombre==""): 
            self.nombreCliente.setText(QtGui.QApplication.translate("Form","Seleccione Cliente" , None, QtGui.QApplication.UnicodeUTF8))
        else:
            self.nombreCliente.setText(QtGui.QApplication.translate("Form",str(Variables.Variables.cliente.nombre), None, QtGui.QApplication.UnicodeUTF8))

        observaciones=Variables.Variables.factura.observaciones
        self.texto.setText(QtGui.QApplication.translate("Form",str(observaciones) , None, QtGui.QApplication.UnicodeUTF8))
