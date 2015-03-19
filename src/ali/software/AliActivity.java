package ali.software;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import ali.clases.Facturas;
import ali.clases.GlobalStatic;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AliActivity extends Activity {

	/** Called when the activity is first created. */
	
	//Declaramos los cinco botones que componen la
	private Button clients;
	private Button products;
	private Button diaryBill;
	private Button anualBill;
	private Button backUp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);						//Asociamos con el Layout al inicio
        clients = (Button) findViewById(R.id.clientsButton);	//Asociamos los botones a el elemento correspondiente
        products = (Button) findViewById(R.id.productsButton);
        diaryBill = (Button) findViewById(R.id.diaryButton);
        anualBill = (Button) findViewById(R.id.anualButton);
        backUp = (Button) findViewById(R.id.backupButton);
        
        GlobalStatic data = (GlobalStatic) getApplication();
		if (!data.mBluetoothAdapter.isEnabled()) {
			int REQUEST_ENABLE_BT=1;
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
        
        clients.setOnClickListener(new View.OnClickListener() {		//Al hacer click en Clientes			
			@Override
			public void onClick(View v) {
				GlobalStatic data = (GlobalStatic) getApplication();
				data.seleccionandoClientesAnual=false;
				data.seleccionandoClientesDiaria=false;
				data.seleccionandoClientesDiaria2=false;

				Intent intent = new Intent(AliActivity.this,Clients.class);	//Cambiamos de layout
				startActivity(intent);												//Iniciando una actividad
			}
		});
        
        
        products.setOnClickListener(new View.OnClickListener() {	//Al clickar Productos
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GlobalStatic data = (GlobalStatic) getApplication();
				data.seleccionandoProductos=false;
				Intent intent = new Intent(AliActivity.this,Products.class);//Cambiamos de layout
				startActivity(intent);												//Iniciando una actividad
			}
		});
        
        diaryBill.setOnLongClickListener(new View.OnLongClickListener() {
        	
			
			@Override
			public boolean onLongClick(View v) {

				GlobalStatic data = (GlobalStatic) getApplication();
				data.factura = null;
				data.seleccionandoClientesAnual=false;
				
				data.seleccionandoClientesDiaria=false;
				data.seleccionandoProductos=false;
					
				
				data.seleccionandoClientesDiaria2=true;
				Intent intent = new Intent(AliActivity.this,Clients.class);//Cambiamos de layout
				startActivity(intent);													//Iniciando una actividad
				return false;
			}
		});
       
        diaryBill.setOnClickListener(new View.OnClickListener() {			//Al clickar en Factura diaria
			
        		@Override
				public void onClick(View v) {
        
				// TODO Auto-generated method stub
				//Ponemos el identificador de la factura en -1
				//(Nueva factura)
				GlobalStatic data = (GlobalStatic) getApplication();
				data.cliente=null;
				Calendar cal = Calendar.getInstance();
				data.fromMenu=true;
				data.seleccionandoClientesAnual=false;
				data.seleccionandoClientesDiaria=false;
				data.seleccionandoClientesDiaria2=false;
				data.factura = new Facturas(-1, new java.sql.Date(cal.getTimeInMillis()), null, false);
				
				//Iniciamos el formulario de las facturas
				Intent intent = new Intent(AliActivity.this,BillForm.class);
				startActivity(intent);
			}
		});
        anualBill.setOnClickListener(new View.OnClickListener() {				//Al clickar en factura anual
        	
			@Override
			public void onClick(View v) {
				//Tenemos que seleccionar a un cliente para una factura anual
				GlobalStatic data = (GlobalStatic) getApplication();
				data.seleccionandoClientesAnual=true;
				data.seleccionandoClientesDiaria=false;
				data.seleccionandoClientesDiaria2=false;
				Intent intent = new Intent(AliActivity.this,Clients.class);//Cambiamos de layout
				startActivity(intent);													//Iniciando una actividad
			}
		});
        
        backUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {					
				// TODO Auto-generated method stub
				//Crear Dump de la base de datos y almacenarlo/////////////////////////////////////////
				GlobalStatic data = (GlobalStatic) getApplication();
				try {
//					DBHandler db = new DBHandler(getApplicationContext());
//					db.createDataBase();
					data.createBackup();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String text ="Base de datos guardada";
				Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
				msg.show();

				
		}});
    }
}