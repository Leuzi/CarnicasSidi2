package ali.software;

import ali.clases.Clientes;
import ali.clases.GlobalStatic;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClientsForm extends Activity{

	//Declaramos los tres botones
	private Button buttonBack;
	private Button buttonErase;
	private Button buttonAccept;
	private Button buttonModifyPrizes;
	private Button buttonList; 
	
	//Y los múltiples EditText
	private EditText nombre;
	private EditText detalles;
	private EditText direccion;
	private EditText ciudad;
	private EditText pais;
	private EditText CP;
	private EditText CIF;
	private EditText next;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clientform);//Lo asignamos con su layout
		
		//Y los EditText con sus correspondientes
		nombre = (EditText) findViewById(R.id.editName);
		detalles = (EditText) findViewById(R.id.editDetail);
		direccion = (EditText) findViewById(R.id.editAddress);
		ciudad = (EditText) findViewById(R.id.editCity);
		pais = (EditText) findViewById(R.id.editCountry);
		CP = (EditText) findViewById(R.id.editCP);
		CIF = (EditText) findViewById(R.id.editCIF);
		next = (EditText) findViewById(R.id.editNext);
		
		buttonBack = (Button) findViewById(R.id.backButton);
		buttonErase = (Button) findViewById(R.id.eraseButton);
		buttonAccept= (Button) findViewById(R.id.acceptButton);
		buttonModifyPrizes = (Button) findViewById(R.id.modifyButton);
		buttonList = (Button) findViewById(R.id.listButton);
				
		//Obtener los datos estaticos
		GlobalStatic data = ((GlobalStatic)getApplicationContext());
		
		//El cliente es el cliente estático
		Clientes client = data.cliente;
		
		//Si el cliente es nulo (Significa que venimos de crear nuevo)
		if(client ==null ){
			//Poner valores por defecto
			nombre.setText("");
			detalles.setText("");
			direccion.setText("");
			ciudad.setText("Melilla");
			pais.setText("España");
			CP.setText("");
			CIF.setText("");
			//Ocultamos el boton puesto que no lo necesitamos
			buttonErase.setVisibility(View.GONE);
			buttonModifyPrizes.setVisibility(View.GONE);
			next.setVisibility(View.GONE);
		}
		//Si no, ya tenemos datos para empezar a rellenar
		else{			
			nombre.setText(client.getName());
			detalles.setText(client.getDetails());
			direccion.setText(client.getAddress());
			ciudad.setText(client.getCity());
			pais.setText(client.getCountry());
			CP.setText(client.getZIPCode());
			CIF.setText(client.getCIF());
			next.setText(String.valueOf(client.getNextFactura()));
		}
		
		//Asociar el botón back con el layout anterior
		buttonBack.setOnClickListener(new View.OnClickListener(){
			
			
			public void onClick(View v){
				
				//Empezar la actividad
				Intent intent = new Intent(ClientsForm.this,Clients.class);
				startActivity(intent);				
			}
		});
		
		
		buttonAccept.setOnClickListener(new View.OnClickListener() {
						
			public void onClick(View v) {
				
				//Obtener los datos estáticos(Usaremos el cliente luego)
				GlobalStatic data = ((GlobalStatic)getApplicationContext());
				
				//Obtener los elementos actuales del la pantalla
				String name = nombre.getText().toString();
				String detail = detalles.getText().toString();
				String address = direccion.getText().toString();
				String city = ciudad.getText().toString();
				String country = pais.getText().toString();
				String CIFid = CIF.getText().toString();
				String ZIPCode = CP.getText().toString();
				int ultima = 0;
				if(!next.getText().toString().equals("")){
					ultima = Integer.parseInt(next.getText().toString());
				}
				
				//Centinela para ver si realmente se cambió el usuario
				boolean equal=true;
				
				if(data.cliente==null){
					
					int result=data.newClient(name,detail,address,city,country,CIFid,ZIPCode,ultima);
					Resources res = getResources();
					if(result==-1){
						String text = String.format(res.getString(R.string.notCreatedClient));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
					}
					else{
						String text = String.format(res.getString(R.string.createdClient));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						Intent intent = new Intent(ClientsForm.this,Clients.class);
						startActivity(intent);
					}			
					
					
				}
				
				else{
				
					//Para cualquier valor modificado
					//lo cambiaremos
					if(!data.cliente.getName().equals(name)){
						equal=false;
						data.cliente.setName(name);
					}
					/*
						System.out.print("Nombre diferente");
						System.out.flush();
					}
					else{
						System.out.print("Nombre igual");
						System.out.flush();
					}
					*/
					if(!data.cliente.getAddress().equals(address)){
						equal=false;
						data.cliente.setAddress(address);
					}
					/*
						System.out.print("Direccion diferente");
						System.out.flush();
					}
					else{
						System.out.print("Direccion igual");
						System.out.flush();
					}
					*/
					if(!data.cliente.getDetails().equals(detail)){
						equal=false;
						data.cliente.setDetails(detail);
					}
					/*
						System.out.print("Detalle diferente");
						System.out.flush();
					}
					else{
						System.out.print("Detalle igual");
						System.out.flush();
					}
					*/
					if(!data.cliente.getCity().equals(city)){
						equal=false;
						data.cliente.setCity(city);
					}
					/*
						System.out.print("Ciudad diferente");
						System.out.flush();
					}
					else{
						System.out.print("Ciudad igual");
						System.out.flush();
					}
					*/
					if(!data.cliente.getCIF().equals(CIFid)){
						equal=false;
						data.cliente.setCIF(CIFid);
					}
					/*
						System.out.print("CIF diferente");
						System.out.flush();
					}
					else{
						System.out.print("CIF igual");
						System.out.flush();
					}
					*/
					if(!data.cliente.getCountry().equals(country)){
						equal=false;
						data.cliente.setCountry(country);
					}
					/*
						System.out.print("Pais diferente");
						System.out.flush();
					}
					else{
						System.out.print("Pais igual");
						System.out.flush();
					}
					*/
					if(!data.cliente.getZIPCode().equals(ZIPCode)){
						equal=false;
						data.cliente.setZipCode(ZIPCode);
					}
					/*
						System.out.print("ZIP diferente");
						System.out.flush();
					}
					else{
						System.out.print("ZIP igual");
						System.out.flush();
					}
					*/
					
					if(data.cliente.getNextFactura()!=ultima){
						equal=false;
						data.cliente.setNextFactura(ultima-1);
					}
					
					//Si todo permanece igual
					if(equal){
						//Creamos un toast para avisar
						Resources res = getResources();
						String text = String.format(res.getString(R.string.notModifiedClient));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						
					}
					//Algo se modificó
					else{
						//Llamamos a la función para modificar el cliente
						data.modifyClient();
						//Creamos un toast para avisar
						Resources res = getResources();
						String text = String.format(res.getString(R.string.modifiedClient));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						
					}
					Intent intent = new Intent(ClientsForm.this,Clients.class);
					startActivity(intent);
				}
				
			}
		});
		
		buttonErase.setOnClickListener(new View.OnClickListener(){
			
			//Sólo puede ser de un cliente que existiera previamente
			//El botón esta oculto 
			
			//Obtener los datos estáticos(Usaremos el cliente luego)
			GlobalStatic data = ((GlobalStatic)getApplicationContext());
			
			public void onClick(View v){
				//Añadir mensaje warning/////////////////////////////////////////////////////
				int result = data.eraseClient(data);
				
				if(result == 1){
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.erasedClient));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.error1Client));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();	
				}
				
				
				Intent intent = new Intent(ClientsForm.this,Clients.class);
				startActivity(intent);
			}
		});
		
		buttonModifyPrizes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(ClientsForm.this,ModifyPrizes.class);
				startActivity(intent);
			}
		});
		
		buttonList.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GlobalStatic data =(GlobalStatic) getApplication();
				Resources res = getResources();
				String text = null;
				Toast msg = null;
				
				try {
					data.crearListaPrecios(data);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Creamos un toast para avisar
				
				text = String.format(res.getString(R.string.printing));
				msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
				msg.show();				
			}
		});
		
	}
}
