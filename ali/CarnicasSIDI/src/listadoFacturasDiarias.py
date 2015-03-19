# -*- coding: iso-8859-15 -*-

# Form implementation generated from reading ui file 'c:\Users\kaos\workspace\ali\Ali1\listadoFacturasDiarias.ui'
#
# Created: Sat Jan 07 01:23:23 2012
#      by: PyQt4 UI code generator 4.9
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui
import Variables

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_ListadoFacturasDiarias(object):
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
        self.label.setGeometry(QtCore.QRect(350, 40, 101, 16))
        self.label.setObjectName(_fromUtf8("label"))
        self.buttonAtras = QtGui.QPushButton(self.frame)
        self.buttonAtras.setGeometry(QtCore.QRect(110, 500, 75, 23))
        self.buttonAtras.setObjectName(_fromUtf8("buttonAtras"))
        self.buttonAceptar = QtGui.QPushButton(self.frame)
        self.buttonAceptar.setGeometry(QtCore.QRect(610, 500, 75, 23))
        self.buttonAceptar.setObjectName(_fromUtf8("buttonAceptar"))
        self.buttonNueva = QtGui.QPushButton(self.frame)
        self.buttonNueva.setGeometry(QtCore.QRect(610, 5, 80, 40))
        self.buttonNueva.setObjectName(_fromUtf8("buttonNueva"))
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
        self.tableWidget = QtGui.QTableWidget(self.scrollAreaWidgetContents_2)
        self.tableWidget.setGeometry(QtCore.QRect(0, 0, 591, 381))
        self.tableWidget.setStyleSheet(_fromUtf8("QTableWidget{background-color:white;}"))
        self.tableWidget.setEditTriggers(QtGui.QAbstractItemView.NoEditTriggers)
        self.tableWidget.setAlternatingRowColors(True)
        self.tableWidget.setSelectionMode(QtGui.QAbstractItemView.SingleSelection)
        self.tableWidget.setSelectionBehavior(QtGui.QAbstractItemView.SelectRows)
        self.tableWidget.setTextElideMode(QtCore.Qt.ElideLeft)
        self.tableWidget.setShowGrid(False)
        self.tableWidget.setGridStyle(QtCore.Qt.NoPen)
        self.tableWidget.setObjectName(_fromUtf8("tableWidget"))
        self.tableWidget.setColumnCount(4)
        self.tableWidget.setRowCount(0)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(0, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(1, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(2, item)
        item = QtGui.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(3, item)
        self.scrollArea.setWidget(self.scrollAreaWidgetContents_2)
        self.buttonImprimir = QtGui.QPushButton(self.frame)
        self.buttonImprimir.setGeometry(QtCore.QRect(360, 500, 75, 23))
        self.buttonImprimir.setObjectName(_fromUtf8("buttonImprimir"))

        self.tableWidget.setColumnWidth(0,100)
        self.tableWidget.setColumnWidth(1,65)
        self.tableWidget.setColumnWidth(2,100)
        self.tableWidget.setColumnWidth(3,307)
        
        i=0
        for cliente in Variables.Variables.clientes:
            nombre=cliente.nombre
            for factura in cliente.facturas:
                numero=factura.idFactura
                fecha=factura.fecha
                observaciones=factura.observaciones
                
                self.tableWidget.insertRow(i)
                self.tableWidget.setItem(i,0,QtGui.QTableWidgetItem(nombre))
                self.tableWidget.setItem(i,1,QtGui.QTableWidgetItem(str(numero)))
                fechas=fecha.strftime("%d/%m/%y")
                self.tableWidget.setItem(i,2,QtGui.QTableWidgetItem(fechas))
                self.tableWidget.setItem(i,3,QtGui.QTableWidgetItem(observaciones))
            
                i=i+1
        self.retranslateUi(Form)
        QtCore.QObject.connect(self.buttonAtras, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarPrincipal)
        QtCore.QObject.connect(self.buttonImprimir, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarImprimir)
        QtCore.QObject.connect(self.buttonAceptar, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarDetallesFactura)
        QtCore.QObject.connect(self.buttonNueva, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarNuevaFactura)
        QtCore.QObject.connect(self.tableWidget, QtCore.SIGNAL(_fromUtf8("doubleClicked(QModelIndex)")), Form.lanzarDetallesFactura)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        Form.setWindowTitle(QtGui.QApplication.translate("Form", "Form", None, QtGui.QApplication.UnicodeUTF8))
        self.label.setText(QtGui.QApplication.translate("Form", "Listado de Facturas", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAtras.setText(QtGui.QApplication.translate("Form", "Atrás", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAceptar.setText(QtGui.QApplication.translate("Form", "Modificar", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonNueva.setText(QtGui.QApplication.translate("Form", "Nueva Factura", None, QtGui.QApplication.UnicodeUTF8))
        self.tableWidget.setSortingEnabled(True)
        item = self.tableWidget.horizontalHeaderItem(0)
        item.setText(QtGui.QApplication.translate("Form", "Cliente", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(1)
        item.setText(QtGui.QApplication.translate("Form", "NºFactura", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(2)
        item.setText(QtGui.QApplication.translate("Form", "Fecha", None, QtGui.QApplication.UnicodeUTF8))
        item = self.tableWidget.horizontalHeaderItem(3)
        item.setText(QtGui.QApplication.translate("Form", "Observaciones", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonImprimir.setText(QtGui.QApplication.translate("Form", "Imprimir", None, QtGui.QApplication.UnicodeUTF8))

