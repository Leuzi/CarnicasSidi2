# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'c:\Users\kaos\workspace\ali\Ali1\principal.ui'
#
# Created: Mon Jan 02 12:59:23 2012
#      by: PyQt4 UI code generator 4.9
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_Principal(object):
    def setupUi(self, Form):
        Form.setObjectName(_fromUtf8("Form"))
        Form.resize(800, 600)
        Form.setMinimumSize(QtCore.QSize(800, 600))
        Form.setStyleSheet(_fromUtf8(""))
        self.frame = QtGui.QFrame(Form)
        self.frame.setGeometry(QtCore.QRect(0, 0, 800, 600))
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Minimum, QtGui.QSizePolicy.Minimum)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.frame.sizePolicy().hasHeightForWidth())
        self.frame.setSizePolicy(sizePolicy)
        self.frame.setMinimumSize(QtCore.QSize(800, 600))
        self.frame.setMaximumSize(QtCore.QSize(800, 600))
        self.frame.setBaseSize(QtCore.QSize(800, 600))
        self.frame.setAutoFillBackground(False)
        self.frame.setStyleSheet(_fromUtf8("QFrame{background-color:#fff9a2;}"))
        self.frame.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame.setFrameShadow(QtGui.QFrame.Raised)
        self.frame.setObjectName(_fromUtf8("frame"))
        self.frame_2 = QtGui.QFrame(self.frame)
        self.frame_2.setGeometry(QtCore.QRect(200, 120, 221, 161))
        self.frame_2.setStyleSheet(_fromUtf8("QFrame{background-color:#bbfa62;\n"
"border: 3px solid black;\n"
"}"))
        self.frame_2.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_2.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_2.setObjectName(_fromUtf8("frame_2"))
        self.buttonClientes = QtGui.QPushButton(self.frame_2)
        self.buttonClientes.setGeometry(QtCore.QRect(30, 45, 151, 61))
        font = QtGui.QFont()
        font.setPointSize(13)
        self.buttonClientes.setFont(font)
        self.buttonClientes.setObjectName(_fromUtf8("buttonClientes"))
        self.frame_3 = QtGui.QFrame(self.frame)
        self.frame_3.setGeometry(QtCore.QRect(410, 120, 221, 161))
        self.frame_3.setStyleSheet(_fromUtf8("QFrame{background-color:#bbfa62;\n"
"border: 3px solid black;\n"
"}"))
        self.frame_3.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_3.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_3.setObjectName(_fromUtf8("frame_3"))
        self.buttonProductos = QtGui.QPushButton(self.frame_3)
        self.buttonProductos.setGeometry(QtCore.QRect(35, 45, 151, 61))
        font = QtGui.QFont()
        font.setPointSize(13)
        self.buttonProductos.setFont(font)
        self.buttonProductos.setObjectName(_fromUtf8("buttonProductos"))
        self.frame_4 = QtGui.QFrame(self.frame)
        self.frame_4.setGeometry(QtCore.QRect(200, 270, 221, 161))
        self.frame_4.setStyleSheet(_fromUtf8("QFrame{background-color:#bbfa62;\n"
"border: 3px solid black;\n"
"}"))
        self.frame_4.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_4.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_4.setObjectName(_fromUtf8("frame_4"))
        self.buttonDiaria = QtGui.QPushButton(self.frame_4)
        self.buttonDiaria.setGeometry(QtCore.QRect(30, 45, 151, 61))
        font = QtGui.QFont()
        font.setPointSize(13)
        self.buttonDiaria.setFont(font)
        self.buttonDiaria.setObjectName(_fromUtf8("buttonDiaria"))
        self.frame_5 = QtGui.QFrame(self.frame)
        self.frame_5.setGeometry(QtCore.QRect(410, 270, 221, 161))
        self.frame_5.setStyleSheet(_fromUtf8("QFrame{background-color:#bbfa62;\n"
"border: 3px solid black;\n"
"}"))
        self.frame_5.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_5.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_5.setObjectName(_fromUtf8("frame_5"))
        self.buttonMensual = QtGui.QPushButton(self.frame_5)
        self.buttonMensual.setGeometry(QtCore.QRect(35, 45, 151, 61))
        font = QtGui.QFont()
        font.setPointSize(13)
        self.buttonMensual.setFont(font)
        self.buttonMensual.setObjectName(_fromUtf8("buttonMensual"))
        self.buttonSalir = QtGui.QPushButton(self.frame)
        self.buttonSalir.setGeometry(QtCore.QRect(604, 482, 101, 41))
        self.buttonSalir.setObjectName(_fromUtf8("buttonSalir"))

        self.retranslateUi(Form)
        QtCore.QObject.connect(self.buttonSalir, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.close)
        QtCore.QObject.connect(self.buttonMensual, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarMensual)
        QtCore.QObject.connect(self.buttonDiaria, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarDiaria)
        QtCore.QObject.connect(self.buttonClientes, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarClientes)
        QtCore.QObject.connect(self.buttonProductos, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarProductos)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        Form.setWindowTitle(QtGui.QApplication.translate("Form", "Carnicas sidi", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonClientes.setText(QtGui.QApplication.translate("Form", "Clientes", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonProductos.setText(QtGui.QApplication.translate("Form", "Productos", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonDiaria.setText(QtGui.QApplication.translate("Form", "Factura diaria", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonMensual.setText(QtGui.QApplication.translate("Form", "Factura Mensual \n"
" Anual", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonSalir.setText(QtGui.QApplication.translate("Form", "Salir", None, QtGui.QApplication.UnicodeUTF8))

