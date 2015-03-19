# -*- coding: iso-8859-15 -*-

# Form implementation generated from reading ui file 'c:\Users\kaos\workspace\ali\Ali1\listadoClientes.ui'
#
# Created: Tue Jan 03 00:04:30 2012
#      by: PyQt4 UI code generator 4.9
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui
import Variables
try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_ListadoClientes(object):
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
        self.label = QtGui.QLabel(self.frame)
        self.label.setGeometry(QtCore.QRect(350, 40, 91, 16))
        self.label.setObjectName(_fromUtf8("label"))
        self.buttonAtras = QtGui.QPushButton(self.frame)
        self.buttonAtras.setGeometry(QtCore.QRect(110, 500, 75, 23))
        self.buttonAtras.setObjectName(_fromUtf8("buttonAtras"))
        self.buttonAceptar = QtGui.QPushButton(self.frame)
        self.buttonAceptar.setGeometry(QtCore.QRect(610, 500, 75, 23))
        self.buttonAceptar.setObjectName(_fromUtf8("buttonAceptar"))
        self.frame_2 = QtGui.QFrame(self.frame)
        self.frame_2.setGeometry(QtCore.QRect(80, 60, 631, 421))
        self.frame_2.setStyleSheet(_fromUtf8("QFrame{background-color:#bbfa62;\n"
"border: 2px solid black;\n"
"}"))
        self.frame_2.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_2.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_2.setObjectName(_fromUtf8("frame_2"))
        self.scrollArea = QtGui.QScrollArea(self.frame_2)
        self.scrollArea.setGeometry(QtCore.QRect(20, 20, 591, 381))
        self.scrollArea.setWidgetResizable(True)
        self.scrollArea.setObjectName(_fromUtf8("scrollArea"))
        self.scrollAreaWidgetContents_2 = QtGui.QWidget()
        self.scrollAreaWidgetContents_2.setGeometry(QtCore.QRect(0, 0, 587, 377))
        self.scrollAreaWidgetContents_2.setStyleSheet(_fromUtf8("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
"<ui version=\"4.0\">\n"
" <widget name=\"__qt_fake_top_level\">\n"
"  <widget class=\"QScrollArea\" name=\"scrollArea\">\n"
"   <property name=\"geometry\">\n"
"    <rect>\n"
"     <x>110</x>\n"
"     <y>70</y>\n"
"     <width>581</width>\n"
"     <height>391</height>\n"
"    </rect>\n"
"   </property>\n"
"   <property name=\"widgetResizable\">\n"
"    <bool>true</bool>\n"
"   </property>\n"
"   <widget class=\"QWidget\" name=\"scrollAreaWidgetContents\">\n"
"    <property name=\"geometry\">\n"
"     <rect>\n"
"      <x>0</x>\n"
"      <y>0</y>\n"
"      <width>579</width>\n"
"      <height>389</height>\n"
"     </rect>\n"
"    </property>\n"
"    <widget class=\"QListWidget\" name=\"listClientes\">\n"
"     <property name=\"geometry\">\n"
"      <rect>\n"
"       <x>0</x>\n"
"       <y>0</y>\n"
"       <width>581</width>\n"
"       <height>391</height>\n"
"      </rect>\n"
"     </property>\n"
"    </widget>\n"
"   </widget>\n"
"  </widget>\n"
" </widget>\n"
" <resources/>\n"
"</ui>\n"
""))
        self.scrollAreaWidgetContents_2.setObjectName(_fromUtf8("scrollAreaWidgetContents_2"))
        self.listWidget = QtGui.QListWidget(self.scrollAreaWidgetContents_2)
        self.listWidget.setGeometry(QtCore.QRect(0, 0, 591, 381))
        self.listWidget.setStyleSheet(_fromUtf8("QListWidget{background-color:white;}"))
        self.listWidget.setObjectName(_fromUtf8("listWidget"))
        self.scrollArea.setWidget(self.scrollAreaWidgetContents_2)
        
        if(Variables.Variables.seleccionandoClientes==0):
            self.listWidget.addItem("Nuevo Cliente")
        
        listaClientes = Variables.Variables.getNombresClientes()

        for cliente in listaClientes:
            self.listWidget.addItem(cliente)

        self.retranslateUi(Form)
        
        if(Variables.Variables.seleccionandoClientes==0):
            
            QtCore.QObject.connect(self.buttonAceptar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarDetalleCliente)
            QtCore.QObject.connect(self.buttonAtras, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarPrincipal)
            QtCore.QObject.connect(self.listWidget,QtCore.SIGNAL(_fromUtf8("doubleClicked(QModelIndex)")),Form.lanzarDetalleCliente)
        else:
            QtCore.QObject.connect(self.buttonAceptar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarDetalleFactura)
            QtCore.QObject.connect(self.buttonAtras, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarPrincipal)
            QtCore.QObject.connect(self.listWidget,QtCore.SIGNAL(_fromUtf8("doubleClicked(QModelIndex)")),Form.lanzarDetalleFactura)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        Form.setWindowTitle(QtGui.QApplication.translate("Form", "Form", None, QtGui.QApplication.UnicodeUTF8))
        self.label.setText(QtGui.QApplication.translate("Form", "Listado de Clientes", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAtras.setText(QtGui.QApplication.translate("Form", "Atrás", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAceptar.setText(QtGui.QApplication.translate("Form", "Aceptar", None, QtGui.QApplication.UnicodeUTF8))

