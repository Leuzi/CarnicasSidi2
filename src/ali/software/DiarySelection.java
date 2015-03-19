package ali.software;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import ali.clases.Clientes;
import ali.clases.Facturas;
import ali.clases.GlobalStatic;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DiarySelection extends Activity {
	
	//Elementos de la UI
	private Button buttonBack;
	private Button buttonModify;
	private Button buttonNewBill;
	private Button buttonPrint;
	private Button buttonErase;
	
	//Tabla de con los elementos de la factura
	private TableLayout table;
	
	int option = 0;
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.billlist);
		
		//Asociar elementos
		buttonBack = (Button) findViewById(R.id.backButton);
		buttonModify = (Button) findViewById(R.id.modifyButton);
		buttonNewBill = (Button) findViewById(R.id.newButton);
		buttonPrint = (Button) findViewById(R.id.printButton);
		buttonErase = (Button) findViewById(R.id.eraseButton);
		
		//Obtenemos los datos estáticos
		GlobalStatic data = (GlobalStatic) getApplicationContext();

		
		
		table = (TableLayout) findViewById(R.id.tableBill);
		

	
			
		//Obtenemos el cliente
		Clientes client = (Clientes) data.cliente;
		//Guardamos el nombre del cliente
		String name = client.getName();
		//Iteramos las facturas del cliente
		Iterator<Facturas> itBill =  client.getFactura().iterator();
		
		int row=0;
		
		while(itBill.hasNext()){
			//Para cada factura
			Facturas bill = (Facturas) itBill.next();
			//Obtenemos la fecha del cliente
			SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
			String date = df.format( bill.getDate());
			
			//Obtenemos la identificación de la factura
			String idBill = String.valueOf(bill.getIdFactura());
			//Obtenemos las observaciones de la factura
			String review = bill.getView();
			
			//Creamos una nueva Fila
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(
                     LayoutParams.FILL_PARENT,
                     LayoutParams.WRAP_CONTENT));
			
			//Creamos el TextView para el nombre
			TextView nombre = new TextView(this);
			nombre.setLayoutParams(new LayoutParams(
                     LayoutParams.FILL_PARENT,
                     LayoutParams.WRAP_CONTENT));
			nombre.setText(name);
			nombre.setWidth(120);
			nombre.setMaxLines(1);
			nombre.setTextSize(22);
			//Añadimos el nombre
			tr.addView(nombre,0);
			
			//Creamos el numero
			TextView numero = new TextView(this);
			numero.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			numero.setText(idBill);

			numero.setTextSize(22);
			//Añadimos el numero
			tr.addView(numero,1);
			
			//Creamos la fecha
			TextView fecha = new TextView(this);
			fecha.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			
			fecha.setText(date);

			fecha.setTextSize(22);
			//Añadimos la fecha
			tr.addView(fecha,2);
			
			//Creamos las observaciones
			TextView obs = new TextView(this);
			obs.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			obs.setText(review);

			obs.setTextSize(22);
			obs.setMaxLines(1);
			//Añadimos las Observaciones
			tr.addView(obs,3);
			tr.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TableLayout table = (TableLayout) v.getParent();
					
					for(int i=0;i<table.getChildCount();i++){
						TableRow row = (TableRow) table.getChildAt(i);
						row.setSelected(false);
					}

					TableRow row = (TableRow) v;
					row.setSelected(true);
				}
			});
			//Añadimos la fila a la tabla
			table.addView(tr,row,new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			
			
			//Esperamos que funcione
		}
			
			
		
		
		
		
		
		
		//Comportamiento al pulsar el boton back
		buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Volver al menú principal
				Intent intent = new Intent(DiarySelection.this,AliActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		//Comportamiento al pulsar nueva factura
				
		buttonNewBill.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Ponemos el identificador de la factura en -1
				//(Nueva factura)
				GlobalStatic data = (GlobalStatic) getApplication();
				data.cliente=null;
				Calendar cal = Calendar.getInstance();
				
				data.factura = new Facturas(-1, new java.sql.Date(cal.getTimeInMillis()), null, false);
				data.fromMenu=false;
				//Iniciamos el formulario de las facturas
				Intent intent = new Intent(DiarySelection.this,BillForm.class);
				startActivity(intent);
			}
		});
		
		//Comportamiento al boton imprimir
		buttonPrint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Obtenemos la tabla seleccionado
				TableRow selected=null;
				for(int i=0;i<table.getChildCount() && selected==null;i++){
					TableRow row = (TableRow)table.getChildAt(i);
					if(row.isSelected()){
						selected=row;
					}
				}
				
				//Si no se ha seleccionado ninguna columna
				if(selected==null){
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.selectBill));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}

				else{
					//Obtenemos los datos necesarios para
					//Localizar la factura a imprimir
					TextView idFactura = (TextView) selected.getChildAt(1);
					TextView nameClient = (TextView) selected.getChildAt(0);
					
					GlobalStatic data = (GlobalStatic) getApplicationContext();
					data.cliente = data.getCliente((String)nameClient.getText());
					data.factura = data.getFacturasId(data.cliente, Integer.parseInt((String)idFactura.getText()));
					//Creamos el pdf y lo imprimimos
					try {
						data.crearFacturaDiaria(data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.printing));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				
				}
			}
		});
		
		buttonModify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Obtenemos la fila seleccionada
				TableRow selected=null;
				for(int i=0;i<table.getChildCount() && selected==null;i++){
					TableRow row = (TableRow)table.getChildAt(i);
					if(row.isSelected()){
						selected=row;
					}
				}
				
				if(selected==null){
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.selectBill));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					TextView idFactura = (TextView) selected.getChildAt(1);
					TextView nameClient = (TextView) selected.getChildAt(0);
					
					GlobalStatic data = (GlobalStatic) getApplicationContext();
					
					data.cliente = data.getCliente((String)nameClient.getText());
					data.factura = data.getFacturasId(data.cliente, Integer.parseInt((String)idFactura.getText()));

					Intent intent = new Intent(DiarySelection.this,BillForm.class);
					startActivity(intent);
				}
			}
		});
		
		buttonErase.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TableRow selected=null;
				for(int i=0;i<table.getChildCount() && selected==null;i++){
					TableRow row = (TableRow)table.getChildAt(i);
					if(row.isSelected()){
						selected=row;
					}
				}
				
				if(selected==null){
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.selectBill));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					TextView idFactura = (TextView) selected.getChildAt(1);
					TextView nameClient = (TextView) selected.getChildAt(0);
					
					GlobalStatic data = (GlobalStatic) getApplicationContext();
					
					data.cliente = data.getCliente((String)nameClient.getText());
					data.factura = data.getFacturasId(data.cliente, Integer.parseInt((String)idFactura.getText()));
					
					data.db.removeBill(data);
					data.cliente.removeFactura(data.factura);
					
					
					table.removeView(selected);
				}
			
			}
		});
	}

}

