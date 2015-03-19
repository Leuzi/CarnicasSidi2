# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'c:\Users\kaos\workspace\ali\Ali1\formularioSeleccionFecha.ui'
#
# Created: Sun Jan 22 00:29:10 2012
#      by: PyQt4 UI code generator 4.9
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    _fromUtf8 = lambda s: s

class Ui_FormularioSeleccionFechas(object):
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
        self.label.setGeometry(QtCore.QRect(350, 40, 111, 16))
        self.label.setObjectName(_fromUtf8("label"))
        self.buttonAtras = QtGui.QPushButton(self.frame)
        self.buttonAtras.setGeometry(QtCore.QRect(110, 500, 75, 23))
        self.buttonAtras.setObjectName(_fromUtf8("buttonAtras"))
        self.buttonImprimir = QtGui.QPushButton(self.frame)
        self.buttonImprimir.setGeometry(QtCore.QRect(610, 500, 75, 23))
        self.buttonImprimir.setObjectName(_fromUtf8("buttonImprimir"))
        self.frame_2 = QtGui.QFrame(self.frame)
        self.frame_2.setGeometry(QtCore.QRect(80, 70, 631, 421))
        self.frame_2.setStyleSheet(_fromUtf8("QFrame{background-color:#bbfa62;\n"
"border: 2px solid black;\n"
"}"))
        self.frame_2.setFrameShape(QtGui.QFrame.StyledPanel)
        self.frame_2.setFrameShadow(QtGui.QFrame.Raised)
        self.frame_2.setObjectName(_fromUtf8("frame_2"))
        self.Calendario1 = QtGui.QCalendarWidget(self.frame_2)
        self.Calendario1.setGeometry(QtCore.QRect(30, 80, 271, 181))
        self.Calendario1.setStyleSheet(_fromUtf8(""))
        self.Calendario1.setSelectedDate(QtCore.QDate.currentDate())
        self.Calendario1.setMinimumDate(QtCore.QDate(2005, 1, 1))
        self.Calendario1.setFirstDayOfWeek(QtCore.Qt.Monday)
        self.Calendario1.setGridVisible(True)
        self.Calendario1.setVerticalHeaderFormat(QtGui.QCalendarWidget.ISOWeekNumbers)
        self.Calendario1.setDateEditAcceptDelay(1500)
        self.Calendario1.setObjectName(_fromUtf8("Calendario1"))
        self.Calendario2 = QtGui.QCalendarWidget(self.frame_2)
        self.Calendario2.setGeometry(QtCore.QRect(320, 80, 271, 181))
        self.Calendario2.setStyleSheet(_fromUtf8(""))
        self.Calendario2.setSelectedDate(QtCore.QDate.currentDate())
        self.Calendario2.setMinimumDate(QtCore.QDate(2005, 1, 1))
        self.Calendario2.setFirstDayOfWeek(QtCore.Qt.Monday)
        self.Calendario2.setGridVisible(True)
        self.Calendario2.setVerticalHeaderFormat(QtGui.QCalendarWidget.ISOWeekNumbers)
        self.Calendario2.setDateEditAcceptDelay(1500)
        self.Calendario2.setObjectName(_fromUtf8("Calendario2"))
        self.label_2 = QtGui.QLabel(self.frame_2)
        self.label_2.setGeometry(QtCore.QRect(30, 50, 51, 21))
        self.label_2.setAutoFillBackground(False)
        self.label_2.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.label_3 = QtGui.QLabel(self.frame_2)
        self.label_3.setGeometry(QtCore.QRect(320, 50, 51, 21))
        self.label_3.setAutoFillBackground(False)
        self.label_3.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.label_3.setObjectName(_fromUtf8("label_3"))
        self.Button2 = QtGui.QRadioButton(self.frame_2)
        self.Button2.setGeometry(QtCore.QRect(30, 320, 101, 17))
        self.Button2.setObjectName(_fromUtf8("Button2"))
        self.label_5 = QtGui.QLabel(self.frame_2)
        self.label_5.setGeometry(QtCore.QRect(230, 10, 191, 21))
        self.label_5.setAutoFillBackground(False)
        self.label_5.setStyleSheet(_fromUtf8("QLabel{border:0px;}"))
        self.label_5.setObjectName(_fromUtf8("label_5"))
        self.Button3 = QtGui.QRadioButton(self.frame_2)
        self.Button3.setGeometry(QtCore.QRect(30, 350, 101, 17))
        self.Button3.setObjectName(_fromUtf8("Button3"))

        self.retranslateUi(Form)
        QtCore.QObject.connect(self.buttonAtras, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarPrincipal)
        QtCore.QObject.connect(self.buttonImprimir, QtCore.SIGNAL(_fromUtf8("clicked()")), Form.lanzarImprimir)
        QtCore.QMetaObject.connectSlotsByName(Form)

    def retranslateUi(self, Form):
        Form.setWindowTitle(QtGui.QApplication.translate("Form", "Form", None, QtGui.QApplication.UnicodeUTF8))
        self.label.setText(QtGui.QApplication.translate("Form", "Seleccion de facturas", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonAtras.setText(QtGui.QApplication.translate("Form", "Atrás", None, QtGui.QApplication.UnicodeUTF8))
        self.buttonImprimir.setText(QtGui.QApplication.translate("Form", "Imprimir", None, QtGui.QApplication.UnicodeUTF8))
        self.label_2.setText(QtGui.QApplication.translate("Form", "Desde", None, QtGui.QApplication.UnicodeUTF8))
        self.label_3.setText(QtGui.QApplication.translate("Form", "Hasta", None, QtGui.QApplication.UnicodeUTF8))
        self.Button2.setText(QtGui.QApplication.translate("Form", "Sólo No Pagadas", None, QtGui.QApplication.UnicodeUTF8))
        self.label_5.setText(QtGui.QApplication.translate("Form", "Las facturas se agruparan por meses", None, QtGui.QApplication.UnicodeUTF8))
        self.Button3.setText(QtGui.QApplication.translate("Form", "Todas", None, QtGui.QApplication.UnicodeUTF8))

