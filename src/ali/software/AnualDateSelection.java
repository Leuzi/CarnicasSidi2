package ali.software;

import java.util.Calendar;
import java.util.Date;

import ali.clases.GlobalStatic;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class AnualDateSelection extends Activity {

	//Declaración de los elementos de la UI
	
	//Primero los botones
	private Button buttonBack;
	private Button buttonPrint;
	
	//Los elementos del calendario
	private CalendarView sinceDate;
	private CalendarView untilDate;
	private CalendarView dayBill;
	
	//Por último el spinner
	private Spinner selection;
	private Spinner selection2;
	private int option=0;//Opción seleccionada para realizar las facturas
	private int option2=0;
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		//Seleccionamos el layout que queremos usar
		setContentView(R.layout.anualform);
		
		//Los asociamos
		buttonBack = (Button) findViewById(R.id.backButton);
		buttonPrint = (Button) findViewById(R.id.printButton);
		
		sinceDate = (CalendarView) findViewById(R.id.sinceDay);
		untilDate = (CalendarView) findViewById(R.id.untilDay);
		dayBill = (CalendarView) findViewById(R.id.billDay);
		
		sinceDate.setFirstDayOfWeek(2);
		untilDate.setFirstDayOfWeek(2);
		dayBill.setFirstDayOfWeek(2);
		
		selection = (Spinner) findViewById(R.id.selectBillsAnual);
		selection2 = (Spinner) findViewById(R.id.selectModeAnual);
		
		//Creamos el adaptador
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.optionsBillSelection,android.R.layout.simple_spinner_item);
		//Añadimos el layout para el menú
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		//Le indicamos al spinner el adaptador a usar
		selection.setAdapter(adapter);
		
		selection.setSelection(0);
		
		//Hacemos lo mismo para el segundo adaptador
		adapter = ArrayAdapter.createFromResource(this,R.array.optionsModeSelection,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		selection2.setAdapter(adapter);
		
		selection2.setSelection(0);
		//La fecha por defecto es hoy
		long timeNow = System.currentTimeMillis();
		sinceDate.setDate(timeNow);
		untilDate.setDate(timeNow);
		
		//Creamos el dia
		Date day = new Date();
		//Lo ponemos en un calendario
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeNow);
		
		c.add(Calendar.DATE, -6);
		
		day = c.getTime();
		
		sinceDate.setDate(day.getTime());
		
		//Comportamiento cuando se pulsa back
		buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Volvemos al menú seleccionar clientes
				Intent intent = new Intent(AnualDateSelection.this,Clients.class);
				startActivity(intent);
			}
		});
		
		sinceDate.setOnDateChangeListener(new OnDateChangeListener() {
			
			//Comportamiento cuando se cambia una fecha
						
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				
				long since = sinceDate.getDate();
				long until = untilDate.getDate();
				
				
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(since);
				System.out.println(cal.get(Calendar.DAY_OF_MONTH));
				System.out.println(cal.get(Calendar.MONTH));
				System.out.println(cal.get(Calendar.YEAR));
				System.out.flush();
				
				if(cal.get(Calendar.DAY_OF_MONTH)==1){
					cal.add(Calendar.MONTH, 1);
					cal.add(Calendar.DATE, -1);
					until = cal.getTimeInMillis();
					untilDate.setDate(until);
					dayBill.setDate(until);
				}
				
			}
		});
		
		
		
		selection.setOnItemSelectedListener(new OnItemSelectedListener(){


			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// TODO Auto-generated method stub
				option=position;
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
		
		selection2.setOnItemSelectedListener(new OnItemSelectedListener(){


			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				// TODO Auto-generated method stub
				option2=position;
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
		
		buttonPrint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Obtenemos los días seleccionados
				long datesince = sinceDate.getDate();
				long dateuntil = untilDate.getDate();
				long dateBill = dayBill.getDate();
				Date since = new Date(datesince);
				Date until = new Date(dateuntil);
				Date billday = new Date(dateBill);
				
				//Cargamos los datos estáticos(donde está el cliente)
				GlobalStatic data = (GlobalStatic) getApplicationContext();
				
				//Creamos la factura deseada
				try {
					data.crearFacturaAnual(data, since,until,billday,option, option2);
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
		});
		

		
		
	}
	
}
