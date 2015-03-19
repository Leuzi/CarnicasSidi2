package ali.software;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import ali.clases.DBHandler;
import ali.clases.GlobalStatic;
import ali.clases.LineaFactura;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class Clients  extends Activity {
	
	//Declaración del botón Atras
	//Declaración del ListView list
	private Button back;
	private ListView list;
	private Button todosButton;
	private Button lastButton;
	private Button bestButton;
	private Spinner selection;
	private ArrayList<Pair<String,String>> last;
	private ArrayList<Pair<String,String>> names;
	private ArrayList<Pair<String,String>> best;
	
	//Cuando se crea
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clientlist);	//Asociar al layout
		
		//Asociar elementos con su correspondiente elemento en la IU
		back = (Button) findViewById(R.id.backButton);
		todosButton = (Button) findViewById(R.id.TodosButton);
		lastButton = (Button) findViewById(R.id.UltimosButton);
		bestButton = (Button) findViewById(R.id.Mejores);
		list = (ListView) findViewById(R.id.listClient);
		list.setDivider(null);
		
		//Recolectar datos globales
		final GlobalStatic data = (GlobalStatic) getApplication();
		
		//ArrayList con los nombres de los clientes
		names = data.getNameClients();
		DBHandler BD = new DBHandler(this);
		last = BD.getLastClients();
		best = BD.getBestClients(null,null);
		
		selection = (Spinner) findViewById(R.id.selectDate);		
		//Creamos el adaptador
		ArrayAdapter<CharSequence> adaptadorfechas = ArrayAdapter.createFromResource(this,R.array.optionsDate,android.R.layout.simple_spinner_item);
		//Añadimos el layout para el menú
		adaptadorfechas.setDropDownViewResource(android.R.layout.simple_spinner_item);
		//Le indicamos al spinner el adaptador a usar
		selection.setAdapter(adaptadorfechas);
		
		selection.setSelection(0);
		
		//Si estamos creando/editando clientes
		if(data.seleccionandoClientesAnual == false && data.seleccionandoClientesDiaria==false&& data.seleccionandoClientesDiaria2==false){
			//Añadir un elemento "Nuevo cliente"
			Resources res = getResources();
			String text = String.format(res.getString(R.string.newClient));
			names.add(0,new Pair<String,String>(text,""));//
			last.add(0,new Pair<String,String>(text,""));
			best.add(0,new Pair<String,String>(text,""));
		}
		
				
		ItemAdapter adapter = new ItemAdapter(this, names);
		selection.setVisibility(View.INVISIBLE);
		//Establecer el adaptador
		list.setAdapter(adapter);
		
		todosButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ItemAdapter adapter =  new ItemAdapter(getApplicationContext(), names);
				
				//Establecer el adaptador
				list.setAdapter(adapter);
				// TODO Auto-generated method stub
				selection.setVisibility(View.INVISIBLE);
			}
		});
		lastButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ItemAdapter adapter =  new ItemAdapter(getApplicationContext(), last);
				//Establecer el adaptador
				list.setAdapter(adapter);
				// TODO Auto-generated method stub
				selection.setVisibility(View.INVISIBLE);
			}
		});
		
		bestButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ItemAdapter adapter =  new ItemAdapter(getApplicationContext(), best);
				
				//Establecer el adaptador
				list.setAdapter(adapter);
				// TODO Auto-generated method stub
				selection.setVisibility(View.VISIBLE);
			}
		});
		
		
		//Al pulsar back
		back.setOnClickListener(new View.OnClickListener() {
			//Ir a el menu principal
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				GlobalStatic data = (GlobalStatic) getApplicationContext();

                data.seleccionandoClientesDiaria2=false;
				Intent intent = null;
				if(data.seleccionandoClientesDiaria){
					intent = new Intent(Clients.this,BillForm.class);
				}
				else{
					intent = new Intent(Clients.this,AliActivity.class);
				}
				 
				startActivity(intent);
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
				best = BD.getBestClients(inicio,fin);
				ItemAdapter adaptador = new ItemAdapter(getApplicationContext(),best);
				list.setAdapter(adaptador);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		list.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		      //Obtener el nombre clickado
		      Pair<String,String> clientes = (Pair<String,String>) list.getItemAtPosition(position);
		      String clientName =clientes.first;
		      
		      Resources res = getResources();
			  String text = String.format(res.getString(R.string.newClient));
		      
			  //Si no se ha pulsado el boton "Nuevo Cliente"
		      if(!clientName.equals(text)){
		    	  //Seleccionar ese cliente
			      data.cliente = data.getCliente(clientName);
		      }
		      else{
		    	  data.cliente=null;
		      }
		      
		      
		      Intent intent = null;
		      //Si estamos seleccionando un cliente para una
		      //Factura anual/mensual
		      if(data.seleccionandoClientesAnual){
		    	  intent = new Intent(Clients.this,AnualDateSelection.class);
		      }
		      //Si estamos seleccionando un cliente para una facura diaria
		      //Vamos a las facturas diarias
		      else if(data.seleccionandoClientesDiaria){
		    	  intent = new Intent(Clients.this,BillForm.class);
		    	  
		    	  Iterator<LineaFactura> it =data.factura.getLines().iterator();
		    	  
		    	  while(it.hasNext()){
		    		  LineaFactura linea = it.next();
		    		  if(data.cliente.getPrecios().containsKey(linea.getName())){
		    			  Object obj = data.cliente.getPrecios().get(linea.getName());
		    			  double prize =  (Double) obj;
		    			  linea.setPrize(prize);
		    		  }
		    		  
		    	  }
		      }
		      else if(data.seleccionandoClientesDiaria2){
		    	  data.seleccionandoClientesDiaria2=false;
		    	  intent = new Intent(Clients.this,DiarySelection.class);
		      }
		      else{//Ir al formulario de clientes
		    	  intent = new Intent(Clients.this,ClientsForm.class);
		      }
		      //Damos la entrada a la correspondiente actividad
		      startActivity(intent);
		    }
		});
		
		
	}
}
