package ali.software;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ali.clases.Clientes;
import ali.clases.GlobalStatic;
import ali.clases.Productos;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ModifyPrizes extends Activity {
	
	//Declarar los elementos de la UI
	private Button backButton;
	private Button restoreButton;
	private TableLayout table;
	
	
	public void onCreate(Bundle savedInstanceState){
		//Llamar al super constructor
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifylist);
		
		//Asociar botones
		backButton = (Button) findViewById(R.id.backModifyLists);
		restoreButton = (Button) findViewById(R.id.restoreButton);
		table = (TableLayout) findViewById(R.id.tablePrizes);
		DigitsKeyListener teclado = DigitsKeyListener.getInstance("0123456789.\n");
		restoreButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				GlobalStatic data = (GlobalStatic) getApplication();
				
				//Borramos todos los precios anteriores
				Map<String, Object> precios = new HashMap<String, Object>();
				data.cliente.setPrecios(precios);
				//MODIFICAR BD
				data.db.removePrizes(data.cliente.getIdClient());
				
				for(int i=0;i<table.getChildCount();i++){
					
					TableRow row = (TableRow) table.getChildAt(i);
					
					TextView nombre = (TextView) row.getChildAt(0);
					
					EditText precio = (EditText) row.getChildAt(1);
					
					double prize = data.getProduct((String)nombre.getText()).getPrize();
					
					precio.setText(String.valueOf(prize));
					
					row.removeViewAt(1);
					row.addView(precio, 1);
					//ED
					
					
				}
			}
		});
		
		//Obtener los datos globales
		GlobalStatic data = (GlobalStatic) getApplication();
		
		//Obtener el cliente seleccionado
		Clientes client = data.cliente;
		
		//Listaremos los productos, así que necesitamos los datos
		Iterator<Productos> it = data.productos.iterator();
		
		//Para cada producto
		while(it.hasNext()){
			//Obtenemos el nombre
			Productos next = it.next();
			String nombre = next.getName();
			//Con su nombre obtenemos el precio del cliente
			Object object = client.getPrecios().get(nombre);
			double prize = 0;
			//Si no tiene precio, le añadimos el precio general
			if(object == null)
				prize = next.getPrize();
			//Si tiene precio, lo usamos
			else
				prize = (Double) object;
			
			//Creamos una fila con el nombre y el precio(Modificable)
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			//Añadimos el nombre del producto
			TextView name = new TextView(this);
			name.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			name.setTextColor(Color.BLACK);
			name.setText(nombre);
			
			EditText precio = new EditText(this);
			precio.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			precio.setMaxLines(1);
			precio.setInputType(0x00002002);
			precio.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			precio.setText(String.valueOf(prize));
			precio.setKeyListener(teclado);
			precio.setWidth(75);
			precio.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
			
			tr.addView(name,0);
			tr.addView(precio,1);
			table.addView(tr,new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			
		}
		
		backButton.setFocusable(true);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Modificar la lista de productos del cliente
				GlobalStatic data = (GlobalStatic) getApplication();
				//Necesitaremos la lista de los productos antiguos
				HashMap<String, Object> antiguo = (HashMap<String, Object>) data.cliente.getPrecios();

				//Para cada fila de la tabla
				for(int i=0;i<table.getChildCount();i++){
					//Cojemos la tabla
					TableRow row = (TableRow) table.getChildAt(i);
					//Obtenemos el nombre y el precio
					TextView name = (TextView) row.getChildAt(0);
					EditText prize = (EditText) row.getChildAt(1);
					
					String nombre = name.getText().toString();
					
					Productos producto = data.getProduct(nombre);
					
					double precio = producto.getPrize();
					try{
						precio = Double.parseDouble(prize.getText().toString());
					}catch(Exception e){};
							
					precio = data.roundTwoDigits(precio);
					System.out.println(nombre);
					System.out.println(precio);
					System.out.flush();
					
					//Cambiamos el precio del producto
					if(precio!=producto.getPrize()){
						data.cliente.getPrecios().put(nombre, precio);
					}
				}		
				
				
				//Map nuevo
				HashMap<String,Object> nuevo = (HashMap<String, Object>) data.cliente.getPrecios();

				//Creamos las tres listas que necesitaremos 
				HashMap<String,Object> modify = new HashMap<String,Object>();
				HashMap<String,Object> create = new HashMap<String,Object>();
				ArrayList<String> delete = new ArrayList<String>();
				
				//Obtenemos todos los elementos diferentes
				Set<Entry<String, Object>> todos = new HashSet<Entry<String, Object>>(antiguo.entrySet());
				todos.addAll(nuevo.entrySet());
				
				Iterator<Entry<String, Object>> it = todos.iterator();

				//Para cada elemento del mapa
				while(it.hasNext()){
					//Obtenemos el elemento
					Map.Entry<String,Object> element = it.next();
					System.out.println(element.getKey());
					
					if(antiguo.containsKey(element)){//Si pertenece al antiguo
						//Puede ser(modificado,eliminado o no modificado)
						
						if(nuevo.containsKey(element)){
							//El elemento estaba antes
								//Y continúa estando
							//Falta comprobar si se ha modificado
							double a = (Double) nuevo.get(element.getKey());
							double b = (Double) antiguo.get(element.getKey());
							
							if(a!=b){ //El elemento fue modificado
								Productos producto = data.getProduct(element.getKey());
								modify.put(String.valueOf(producto.getIdProducto()), a);
							}
							else{
								//El elemento continua como está
							}
						}
						else{
							//El elemento estaba antes
								//Ya no esta, se debe eliminar
							Productos producto = data.getProduct(element.getKey());
							delete.add(String.valueOf(producto.getIdProducto()));
						}
						
					}
					else{//Si no pertenece al antiguo
							//Significa que es nuevo
						 //Debemos crearlo
						Productos producto = data.getProduct(element.getKey());
						
						create.put(String.valueOf(producto.getIdProducto()),element.getValue());
					}
					
				}
				
				data.db.modifyPrize(data.cliente.getIdClient(),create,delete,modify);
				
				//Volver al estado anterior
				Intent intent = new Intent(ModifyPrizes.this,ClientsForm.class);
				startActivity(intent);
			}
		});
	}
}