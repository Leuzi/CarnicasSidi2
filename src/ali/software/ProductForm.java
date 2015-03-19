package ali.software;

import ali.clases.GlobalStatic;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ProductForm extends Activity{
	
	//Declaramos los tres botones de la UI
	private Button buttonBack;
	private Button buttonErase;
	private Button buttonAccept;
	
	//Declaramos los 6 elementos del formulario
	
	private EditText nombre;
	private EditText detalles;
	private EditText precio;
	private CheckBox unidades;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productform);//Asociamos con la vista
		
		//Asociamos variables a elementos de la UI
		nombre = (EditText) findViewById(R.id.editName);
		detalles = (EditText) findViewById(R.id.editDetail);
		precio = (EditText) findViewById(R.id.editPrize);
		unidades = (CheckBox) findViewById(R.id.checkUnitsProduct);
		buttonBack = (Button) findViewById(R.id.backButton);
		buttonErase = (Button) findViewById(R.id.eraseButton);
		buttonAccept = (Button) findViewById(R.id.acceptButton);
		
		
		
		//Datos estáticos		
		GlobalStatic data = (GlobalStatic) getApplicationContext();
		
		//Si es un producto nuevo
		if(data.producto==null){
			//Valores por defecto
			nombre.setText("");
			detalles.setText("");
			precio.setHint("0.0");
			unidades.setChecked(false);
			//Ocultamos el botón borrar
			this.buttonErase.setVisibility(View.GONE);
		}
		else{
			//Valores del producto
			nombre.setText(data.producto.getName());
			detalles.setText(data.producto.getDetails());
			//Distinción entre producto enteros/discretos
			String value = String.valueOf(data.producto.getPrize());
			precio.setText(value);
			unidades.setChecked(data.producto.getUnits());
			
			
		}
		
		this.buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Volvemos a la vista del menú principal
				Intent intent = new Intent(ProductForm.this,Products.class);
				startActivity(intent);
			}
		});
		
		this.buttonAccept.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Obtenemos los datos estáticos
				GlobalStatic data = (GlobalStatic) getApplicationContext();
				//Los elementos escritos en el menú
				String name = nombre.getText().toString();
				String details = detalles.getText().toString();
				double prize = 0;
				try{
					prize=Double.parseDouble(precio.getText().toString());
				}catch(Exception e){
					prize=0;
				}
				
				boolean units = unidades.isChecked();

				
				if(data.producto==null){
					int result=data.newProduct(name,details,prize,units);
					Resources res = getResources();
					
					if(result==-1){
						String text = String.format(res.getString(R.string.notCreatedProduct));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						
					}
					else{
						String text = String.format(res.getString(R.string.createdProduct));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						Intent intent = new Intent(ProductForm.this,Products.class);
						startActivity(intent);
						
					}
					
				}
				else{
					//Comparamos con los valores anteriores
					boolean equal=true;
					
					if(!data.producto.getName().equals(name)){
						equal=false;
						data.producto.setName(name);
					}
					/*
						System.out.println("Nombre diferente");
						System.out.flush();
					}
					else{
						System.out.println("Nombre iguales");
						System.out.flush();
					}
					*/
					if(data.producto.getPrize() != prize){
						equal=false;
						data.producto.setPrize(prize);
					}
					/*
						System.out.println("Precio diferente");
						System.out.flush();
					}
					else{
						System.out.println("Precio iguales");
						System.out.flush();
					}
					*/
					if(!data.producto.getDetails().equals(details)){
						equal=false;
						data.producto.setDetails(details);
					}
					/*
						System.out.println("Detalles diferente");
						System.out.flush();
					}
					else{
						System.out.println("Detalles iguales");
						System.out.flush();
					}
					System.out.print(data.producto.getUnits());
					System.out.print(units);
					*/
					if(data.producto.getUnits()!= units){
						equal=false;
						data.producto.setUnits(units);
					}
					/*
						System.out.println("Unidades diferente");
						System.out.flush();
					}
					else{
						System.out.println("Unidades iguales");
						System.out.flush();
					}
					*/
					//Si todo permanece igual
					if(equal){
						//Creamos un toast para avisar
						Resources res = getResources();
						String text = String.format(res.getString(R.string.notModifiedProduct));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						
					}
					else{
						//Debemos cambiar los datos del producto
						data.modifyProduct();
						
						//Creamos un toast para avisar
						Resources res = getResources();
						String text = String.format(res.getString(R.string.modifiedProduct));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
					}
					Intent intent = new Intent(ProductForm.this,Products.class);
					startActivity(intent);
				}
				
			}	
		});
		
		this.buttonErase.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v){
				
				//Sólo puede ser de un producto que existiera previamente
				//El botón esta oculto 
				
				//Obtener los datos estáticos(Usaremos el producto luego)
				GlobalStatic data = ((GlobalStatic)getApplicationContext());
				//Añadir mensaje warning//////////////////////////////////////////////////////
				int producto=data.eraseProduct(data);
				
				if(producto==1){
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.erasedProduct));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					Resources res = getResources();
					String text = String.format(res.getString(R.string.error1Product));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}
				
				Intent intent = new Intent(ProductForm.this,Products.class);
				startActivity(intent);
			}
		});
		
	}
}
