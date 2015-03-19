package ali.software;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import ali.clases.DBHandler;
import ali.clases.GlobalStatic;
import ali.clases.LineaFactura;
import android.app.Activity;
import android.util.Pair;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import ali.software.ItemAdapter;

public class Products extends Activity {
	
	//Declaración de los elementos de la interfaz gráfica
	public Button backProductList;
	public Button todosButton;
	public Button ultimosButton;
	public Button masVendidosButton;
	public GridView productList;
	public Spinner selection;
	private ArrayList<String> productos;
	private ArrayList<Pair<String,String>> products;
	private ArrayList<Pair<String,String>> ultimos;
	private ArrayList<Pair<String,String>> masVendidos;
	

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productlist);//Lo asignamos con su layout
		//Asignación de los elementos
		backProductList = (Button) findViewById(R.id.backButton);
		todosButton = (Button) findViewById(R.id.TodosButton);
		ultimosButton = (Button) findViewById(R.id.UltimosButton);
		masVendidosButton = (Button) findViewById(R.id.MasVendidosButton);
		productList = (GridView) findViewById(R.id.listProducts);
		
		selection = (Spinner) findViewById(R.id.selectDate);		
		//Creamos el adaptador
		ArrayAdapter<CharSequence> adaptadorfechas = ArrayAdapter.createFromResource(this,R.array.optionsDate,android.R.layout.simple_spinner_item);
		//Añadimos el layout para el menú
		adaptadorfechas.setDropDownViewResource(android.R.layout.simple_spinner_item);
		//Le indicamos al spinner el adaptador a usar
		selection.setAdapter(adaptadorfechas);
		
		selection.setSelection(0);
		
		//Recuperar los datos globales
		final GlobalStatic data =  ((GlobalStatic)getApplicationContext());
		
		//Obtener el nombre de los productos
		productos = data.getNameProducts();
		Iterator<String> it =productos.iterator();
		
		products = new ArrayList<Pair<String,String>>();
		while(it.hasNext()){
			products.add(new Pair<String,String>(it.next(),""));
		}
		
		DBHandler BD = new DBHandler(this);
		ultimos = BD.getLastProducts();
		masVendidos = BD.getBestProducts(null,null);
		
		//Si podemos crear un producto
		if(data.seleccionandoProductos==false){
			Resources res = getResources();
			String text = String.format(res.getString(R.string.newProduct));
			products.add(0,new Pair<String, String>(text,""));
			ultimos.add(0,new Pair<String, String>(text,""));
			masVendidos.add(0,new Pair<String, String>(text,""));
		}
		//Crear un adaptador usando el ArrayList
		//Creación del adaptador y asignación
		ItemAdapter adapter = new ItemAdapter(this, products);
		productList.setAdapter(adapter);
		selection.setVisibility(View.INVISIBLE);
		
		todosButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub			
				//Crear un adaptador usando el ArrayList
				//Creación del adaptador y asignación
				ItemAdapter adapter = new ItemAdapter(getApplicationContext(), products);
				productList.setAdapter(adapter);
				selection.setVisibility(View.INVISIBLE);
			}
		});
		
		masVendidosButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ItemAdapter adaptador = new ItemAdapter(getApplicationContext(),masVendidos);
//				ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.text, android.R.id.text1,masVendidos);
				productList.setAdapter(adaptador);
				selection.setVisibility(View.VISIBLE);
			}
		});
		
		ultimosButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selection.setVisibility(View.INVISIBLE);
				ItemAdapter adaptador = new ItemAdapter(getApplicationContext(),ultimos);
//				ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.text, android.R.id.text1,ultimos);
				productList.setAdapter(adaptador);
			}
		});
		
		selection.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// TODO Auto-generated method stub
				java.util.Date inicio;
				java.util.Date fin;
				fin = Calendar.getInstance().getTime();
				inicio = Calendar.getInstance().getTime();
				Calendar cal = Calendar.getInstance();
				switch (position) {
				case 0://Todas
					 inicio = null;
					 fin = null;
					break;
				case 1: //Hoy
					 inicio.setHours(0);
					 inicio.setMinutes(0);
					 inicio.setSeconds(1);
					 break;
				case 2: //Ayer
					cal.add(Calendar.DATE, -1);
					inicio = cal.getTime();
					fin = cal.getTime();
					inicio.setHours(0);
					inicio.setMinutes(0);
					inicio.setSeconds(1);
					fin.setHours(23);
					fin.setMinutes(59);
					fin.setSeconds(59);
					break;
				
				case 3: //Ultima semana
					cal.add(Calendar.DATE, -7);
					inicio = cal.getTime();
					inicio.setHours(0);
					inicio.setMinutes(0);
					inicio.setSeconds(1);
					break;
				case 4: //Ultimo mes
					cal.add(Calendar.MONTH, -1);
					inicio = cal.getTime();
					inicio.setHours(0);
					inicio.setMinutes(0);
					inicio.setSeconds(1);
					break;
				case 5:
						//Tres meses
					cal.add(Calendar.MONTH, -3);
					inicio = cal.getTime();
					inicio.setHours(0);
					inicio.setMinutes(0);
					inicio.setSeconds(1);
					break;
				case 6:
						//Ultimo año
					cal.add(Calendar.YEAR, -1);
					inicio = cal.getTime();
					inicio.setHours(0);
					inicio.setMinutes(0);
					inicio.setSeconds(1);
					break;
				default:
					break;
				}
				DBHandler BD = new DBHandler(getApplicationContext());
				masVendidos = BD.getBestProducts(inicio,fin);
				ItemAdapter adaptador = new ItemAdapter(getApplicationContext(),masVendidos);
				productList.setAdapter(adaptador);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

				//Creamos un toast para avisar
				Resources res = getResources();
				String text = String.format(res.getString(R.string.chooseType));
				Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
				msg.show();
			}
		});
		
		backProductList.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Al pulsar el botón atrás, si estamos
				
				Intent intent = null;
				
				if(data.seleccionandoProductos){
					intent = new Intent(Products.this,BillForm.class);
				}
				//en facturas(selecciononado producto), volvemos al menú principal
				else{
					intent = new Intent(Products.this,AliActivity.class);
				}
						
				startActivity(intent);
			}
		});
		
		
		productList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				Pair<String,String> par = (Pair<String,String>) productList.getItemAtPosition(position);
				String selectedProduct = par.first;
				
				data.producto = data.getProduct(selectedProduct);
				

				Intent intent=null;
				
				if(data.seleccionandoProductos){
					int count = data.factura.getLines().hashCode();

					LineaFactura linea=null;
					
					double prize = 0;
					
					Object object =null;
					
					if(data.cliente!=null){
					
						object = data.cliente.getPrecios().get(data.producto.getName());
					}
					
					//Si no tiene precio, le añadimos el precio general
					if(object == null)
						prize = data.producto.getPrize();
					//Si tiene precio, lo usamos
					else
						prize = (Double) object;	
					
					linea = new LineaFactura(count,data.producto.getName(),0,prize,0);
					data.factura.getLines().add(data.factura.getLines().size(), linea);
					intent = new Intent(Products.this,BillForm.class);
					
				}
				else{
					intent = new Intent(Products.this,ProductForm.class);
				}
				startActivity(intent);
			}
			
		});
		
	}
	
}
