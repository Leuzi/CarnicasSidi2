# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'c:\Users\kaos\workspace\ali\Ali1\detalleProductos.ui'
#
# Created: Fri Jan 06 01:33:17 2012
#      by: PyQt4 UI code generator 4.9
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui
import Variables,Productos

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_DetalleProductos(object):
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
        self.label = QtGui.QLabel(self.frame_2)
        self.label.setGeometry(QtCore.QRect(230, 20, 71, 16))
        self.label.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label.setObjectName(_fromUtf8("label"))
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
        self.label_2 = QtGui.QLabel(self.frame_2)
        self.label_2.setGeometry(QtCore.QRect(40, 70, 46, 21))
        self.label_2.setAutoFillBackground(False)
        self.label_2.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.lineaNombre = QtGui.QLineEdit(self.frame_2)
        self.lineaNombre.setGeometry(QtCore.QRect(90, 70, 281, 20))
        self.lineaNombre.setText(_fromUtf8(""))
        self.lineaNombre.setObjectName(_fromUtf8("lineaNombre"))
        self.lineaPrecio = QtGui.QLineEdit(self.frame_2)
        self.lineaPrecio.setGeometry(QtCore.QRect(90, 130, 281, 20))
        self.lineaPrecio.setObjectName(_fromUtf8("lineaPrecio"))
        self.radioButton = QtGui.QRadioButton(self.frame_2)
        self.radioButton.setGeometry(QtCore.QRect(250, 160, 82, 17))
        self.radioButton.setText(_fromUtf8(""))
        self.radioButton.setObjectName(_fromUtf8("radioButton"))
        self.label_5 = QtGui.QLabel(self.frame_2)
        self.label_5.setGeometry(QtCore.QRect(40, 160, 209, 16))
        self.label_5.setStyleSheet(_fromUtf8("QLabel{border:0px}"))
        self.label_5.setObjectName(_fromUtf8("label_5"))
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
        QtCore.QObject.connect(self.buttonBorrar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.borrarProductos)
        QtCore.QObject.connect(self.buttonAceptar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarModificarProductos)
        QtCore.QObject.connect(self.buttonAtras, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarListarProductos)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        Form.setWindowTitle(QtGui.QApplication.translate("Form", "Form", None, QtGui.QApplication.UnicodeUTF8))
        self.label.setText(QtGui.QApplication.translate("Form", "Producto N", None, QtGui.QApplication.UnicodeUTF8))
        self.label_3.setText(QtGui.QApplication.translate("Form", "Detalle", None, QtGui.QApplication.UnicodeUTF8))
        self.label_4.setText(QtGui.QApplication.translate("Form", "Precio", None, QtGui.QApplication.UnicodeUTF8))
        self.label_2.setText(QtGui.QApplication.translate("Form", "Nombre", None, QtGui.QApplication.UnicodeUTF8))
        self.label_5.setText(QtGui.QApplication.translate("Form", "Marcar si el producto se mide en unidades", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonBorrar.setText(QtGui.QApplication.translate("Form", "Borrar", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAtras.setText(QtGui.QApplication.translate("Form", "Atras", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAceptar.setText(QtGui.QApplication.translate("Form", "Aceptar", None, QtGui.QApplication.UnicodeUTF8))
        idProducto=Variables.Variables.producto.idProducto
        idProducto= "Producto "+str(idProducto)
        self.label.setText(idProducto)
        self.lineaNombre.setText(Variables.Variables.producto.nombre)
        self.lineaDetalle.setText(str(Variables.Variables.producto.detalle))
        self.lineaPrecio.setText(str(Variables.Variables.producto.precio))
        #Ver el QCheckButton
        if(Variables.Variables.producto.unidades==0):
            self.radioButton.setChecked(0)
        else:
            self.radioButton.setChecked(1)
