# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'c:\Users\kaos\workspace\ali\Ali1\detalleClientes.ui'
#
# Created: Tue Jan 03 01:40:41 2012
#      by: PyQt4 UI code generator 4.9
#
# WARNING! All changes made in this file will be lost!

#Casi todo generado

from PyQt4 import QtCore, QtGui
import Variables
try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_DetalleClientes(object):
    def setupUi(self, Form):
        Form.setObjectName(_fromUtf8("Form"))
        Form.resize(722, 498)
        self.frame = QtGui.QFrame(Form)
        self.frame.setGeometry(QtCore.QRect(0, 0, 800, 600))
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Minimum, QtGui.QSizePolicy.Minimum)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.frame.sizePolicy().hasHeightForWidth())
        self.frame.setSizePolicy(sizePolicy)
        self.frame.setMinimumSize(QtCore.QSize(800, 600))
        self.frame.setMaximumSize(QtCore.QSize(800, 600))
        self.frame.setStyleSheet(_fromUtf8("QFrame{background-color:#fff9a2;}"))
        self.frame.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame.setFrameShadow(QtGui.QFrame.Raised)
        self.frame.setObjectName(_fromUtf8("frame"))
        self.frame_2 = QtGui.QFrame(self.frame)
        self.frame_2.setGeometry(QtCore.QRect(100, 80, 531, 281))
        self.frame_2.setStyleSheet(_fromUtf8("QFrame{background-color:#bbfa62;\n"
"border: 2px solid black;\n"
"}"))
        self.frame_2.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_2.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_2.setObjectName(_fromUtf8("frame_2"))
        self.lineaDNI = QtGui.QLineEdit(self.frame_2)
        self.lineaDNI.setGeometry(QtCore.QRect(90, 160, 113, 20))
        self.lineaDNI.setObjectName(_fromUtf8("lineaDNI"))
        self.lineaCP = QtGui.QLineEdit(self.frame_2)
        self.lineaCP.setGeometry(QtCore.QRect(90, 185, 113, 20))
        self.lineaCP.setObjectName(_fromUtf8("lineaCP"))
        self.label = QtGui.QLabel(self.frame_2)
        self.label.setGeometry(QtCore.QRect(90, 20, 80, 13))
        self.label.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label.setObjectName(_fromUtf8("label"))
        self.lineaCIudad = QtGui.QLineEdit(self.frame_2)
        self.lineaCIudad.setGeometry(QtCore.QRect(260, 160, 111, 20))
        self.lineaCIudad.setObjectName(_fromUtf8("lineaCIudad"))
        self.lineaPais = QtGui.QLineEdit(self.frame_2)
        self.lineaPais.setGeometry(QtCore.QRect(260, 185, 111, 20))
        self.lineaPais.setObjectName(_fromUtf8("lineaPais"))
        self.lineaDetalle = QtGui.QLineEdit(self.frame_2)
        self.lineaDetalle.setGeometry(QtCore.QRect(90, 100, 281, 20))
        self.lineaDetalle.setObjectName(_fromUtf8("lineaDetalle"))
        self.label_3 = QtGui.QLabel(self.frame_2)
        self.label_3.setGeometry(QtCore.QRect(40, 100, 46, 13))
        self.label_3.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_3.setObjectName(_fromUtf8("label_3"))
        self.label_4 = QtGui.QLabel(self.frame_2)
        self.label_4.setGeometry(QtCore.QRect(40, 130, 46, 13))
        self.label_4.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_4.setObjectName(_fromUtf8("label_4"))
        self.label_6 = QtGui.QLabel(self.frame_2)
        self.label_6.setGeometry(QtCore.QRect(210, 160, 46, 13))
        self.label_6.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_6.setObjectName(_fromUtf8("label_6"))
        self.label_7 =  QtGui.QLabel(self.frame_2)
        self.label_7.setGeometry(QtCore.QRect(210, 185, 46, 13))
        self.label_7.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_7.setObjectName(_fromUtf8("label_7"))
        
        self.label_2 = QtGui.QLabel(self.frame_2)
        self.label_2.setGeometry(QtCore.QRect(40, 70, 46, 21))
        self.label_2.setAutoFillBackground(False)
        self.label_2.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.lineaNombre = QtGui.QLineEdit(self.frame_2)
        self.lineaNombre.setGeometry(QtCore.QRect(90, 70, 281, 20))
        self.lineaNombre.setText(_fromUtf8(""))
        self.lineaNombre.setObjectName(_fromUtf8("lineaNombre"))
        self.label_5 = QtGui.QLabel(self.frame_2)
        self.label_5.setGeometry(QtCore.QRect(40, 160, 46, 13))
        self.label_5.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_5.setObjectName(_fromUtf8("label_5"))
        self.label_8 = QtGui.QLabel(self.frame_2)
        self.label_8.setGeometry(QtCore.QRect(40, 185, 46, 13))
        self.label_8.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_8.setObjectName(_fromUtf8("label_8"))        
        self.lineaDireccion = QtGui.QLineEdit(self.frame_2)
        self.lineaDireccion.setGeometry(QtCore.QRect(90, 130, 281, 20))
        self.lineaDireccion.setObjectName(_fromUtf8("lineaDireccion"))
        self.buttonBorrar = QtGui.QPushButton(self.frame)
        self.buttonBorrar.setGeometry(QtCore.QRect(590, 10, 75, 23))
        self.buttonBorrar.setObjectName(_fromUtf8("buttonBorrar"))
        self.buttonAtras = QtGui.QPushButton(self.frame)
        self.buttonAtras.setGeometry(QtCore.QRect(40, 422, 101, 41))
        self.buttonAtras.setObjectName(_fromUtf8("buttonAtras"))
        self.buttonAceptar = QtGui.QPushButton(self.frame)
        self.buttonAceptar.setGeometry(QtCore.QRect(580, 410, 101, 41))
        self.buttonAceptar.setObjectName(_fromUtf8("buttonAceptar"))

        self.retranslateUi(Form)
        QtCore.QObject.connect(self.buttonBorrar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.borrarCliente)
        QtCore.QObject.connect(self.buttonAtras, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarListarClientes)
        QtCore.QObject.connect(self.buttonAceptar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarModificarCliente)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        Form.setWindowTitle(QtGui.QApplication.translate("Form", "Form", None, QtGui.QApplication.UnicodeUTF8))
        idCliente=Variables.Variables.cliente.idCliente
        idCliente= "Cliente "+str(idCliente)
        self.label.setText(QtGui.QApplication.translate("Form", str(idCliente) , None, QtGui.QApplication.UnicodeUTF8))
        self.label_3.setText(QtGui.QApplication.translate("Form", "Detalle", None, QtGui.QApplication.UnicodeUTF8))
        self.label_4.setText(QtGui.QApplication.translate("Form", "Direccion", None, QtGui.QApplication.UnicodeUTF8))
        self.label_6.setText(QtGui.QApplication.translate("Form", "Ciudad", None, QtGui.QApplication.UnicodeUTF8))
        self.label_7.setText(QtGui.QApplication.translate("Form", "Pais", None, QtGui.QApplication.UnicodeUTF8))
        self.label_2.setText(QtGui.QApplication.translate("Form", "Nombre", None, QtGui.QApplication.UnicodeUTF8))
        self.label_5.setText(QtGui.QApplication.translate("Form", "DNI/CIF", None, QtGui.QApplication.UnicodeUTF8))
        self.label_8.setText(QtGui.QApplication.translate("Form", "C.P",None,QtGui.QApplication.UnicodeUTF8))
        self.buttonBorrar.setText(QtGui.QApplication.translate("Form", "Borrar", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAtras.setText(QtGui.QApplication.translate("Form", "Atras", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAceptar.setText(QtGui.QApplication.translate("Form", "Aceptar", None, QtGui.QApplication.UnicodeUTF8))
        self.lineaNombre.setText(Variables.Variables.cliente.nombre)
        self.lineaDetalle.setText(Variables.Variables.cliente.detalles)
        self.lineaCIudad.setText(Variables.Variables.cliente.ciudad)
        self.lineaDireccion.setText(Variables.Variables.cliente.direccion)
        self.lineaDNI.setText(Variables.Variables.cliente.cif)
        self.lineaPais.setText(Variables.Variables.cliente.pais)
        self.lineaCP.setText(str(Variables.Variables.cliente.CP))

