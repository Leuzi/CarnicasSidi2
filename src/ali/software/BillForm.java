package ali.software;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import ali.clases.GlobalStatic;
import ali.clases.LineaFactura;
import ali.clases.Productos;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BillForm extends Activity {

	//Declaramos los Botones de la UI
	private Button buttonBack;
	private Button buttonEnd;
	private Button buttonSelect;
	private Button buttonPrint;
	private Button buttonData;
	
	//Declaramos los demas elementos
	private TextView textClient;
	private TableLayout tabla;
	private CalendarView calendar;
	private EditText obs;
	private TextView total;
	private ToggleButton estado;
	
	public void onCreate(Bundle savedInstanceState){
		//Super-constructor
		super.onCreate(savedInstanceState);
		//Lo asociamos con su layout correspondiente
		setContentView(R.layout.billform);
		
		//Asociamos los elementos
		buttonBack = (Button) findViewById(R.id.backButton);
		buttonEnd = (Button) findViewById(R.id.finalizeButton);
		buttonSelect = (Button) findViewById(R.id.selectButton);
		buttonPrint = (Button) findViewById(R.id.printButton);
		buttonData = (Button) findViewById(R.id.getData);
		//Nombre del cliente
		textClient = (TextView) findViewById(R.id.textClient);
		//Tabla con los elementos
		tabla = (TableLayout) findViewById(R.id.tableBill);
		//Calendario para el día de la factura, el primer dia es el lunes
		calendar = (CalendarView) findViewById(R.id.calendarView);
		calendar.setFirstDayOfWeek(2);
		//Las observaciones y el total
		obs = (EditText) findViewById(R.id.editObs);
		total = (TextView) findViewById(R.id.textTotal);
		estado = (ToggleButton) findViewById(R.id.toggleButton);
		//No mostrar el teclado por la pantalla
		this.getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		GlobalStatic data = (GlobalStatic) getApplication();
		
		//Si hemos elegido al cliente
		if(data.cliente!=null){
			//Hacer que el botón desaparezca
			//Hacer que el nombre aparezca
			buttonSelect.setVisibility(View.GONE);
			textClient.setText(data.cliente.getName());
			textClient.setVisibility(View.VISIBLE);
			buttonData.setVisibility(View.VISIBLE);
			
		}
		
		estado.setChecked(data.factura.isPaid());
		//Ponemos la fecha de la factura
		calendar.setDate(data.factura.getDate().getTime());
		
		//Ponemos los comentarios si existen
		if(data.factura.getView()!=null){
			
			obs.setText(data.factura.getView());
		}
		//Maximo de 35 caracteres
		obs.setFilters(new InputFilter[] { new InputFilter.LengthFilter(35) });
		obs.setMaxLines(1);
		obs.requestFocus(1);
		
		//Numeros que pueden usar en el teclado
		DigitsKeyListener teclado = DigitsKeyListener.getInstance("0123456789.\n");
		//Si se mide en unidades, no puede ponerse puntos
		DigitsKeyListener teclado2 = DigitsKeyListener.getInstance("0123456789\n");
		
		//Iteramos sobre las lineas de la factura
		Iterator<LineaFactura> it = data.factura.getLines().iterator();
		
		while(it.hasNext()){
			
			//Obtenemos la linea a facturar
			LineaFactura next = (LineaFactura) it.next();
			
			//Crear una fila nueva en la tabla
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			
			//Creamos una TextView con el id de la fila
			TextView id = new TextView(this);
			id.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			//Estará oculto, sirve para localizar la linea
			id.setVisibility(View.GONE);
			id.setText(String.valueOf(next.getId()));
			
			//Añadimos el nombre del producto
			TextView nombre = new TextView(this);
			nombre.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			//Modificamos el color y el tamaño del producto
			nombre.setTextColor(Color.BLACK);
			nombre.setTextSize(20);
			nombre.setText(next.getName());
			nombre.setWidth(415);
			
			//Añadimos el peso actual(modificable)
			final EditText peso = new EditText(this);
			peso.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			
			//Si es 0 ponemos una pista sobre el editText

			//Se introduce un número
			peso.setInputType(InputType.TYPE_CLASS_NUMBER);
			peso.setWidth(140);
			//El ancho del peso y que no extienda el teclado un máximo de 7 cifras
			peso.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			peso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(7) });
			//Ponemos el tipo de teclado correspondiente
			if(data.getProduct(next.getName()).getUnits()){
				peso.setKeyListener(teclado2);
			}
			else{
				peso.setKeyListener(teclado);
			}
			if(next.getQuantity()==0){
				peso.setHint("0.0");
			}
			//Si no ponemos el peso del producto, redondeándolo a tres cifras
			else{
				if(data.getProduct(next.getName()).getUnits()){
					peso.setText(String.valueOf((int) next.getQuantity()));
				}
				else{
					peso.setText(String.valueOf(data.roundThreeDigits(next.getQuantity())));
				}
			}
			
			System.out.println(next.getQuantity());
			System.out.flush();
			//Cuando cambie el peso	
			peso.addTextChangedListener(new TextWatcher(){

				@Override
				public void afterTextChanged(Editable s) {
					// Una vez que cambias el peso
					TableRow parent = (TableRow) peso.getParent();
					//Obtienes los elementos que necesitas para
					//calcular el total
					TextView cell = (TextView) parent.getChildAt(0);
					EditText peso = (EditText) parent.getChildAt(2);
					EditText precio = (EditText) parent.getChildAt(3);
					EditText descuento = (EditText) parent.getChildAt(4);
					EditText totalRow = (EditText) parent.getChildAt(5);
					GlobalStatic data = (GlobalStatic) getApplication();
					//Si la casilla no esta vacia
					if(!peso.getText().toString().equals("")){
						
						int id = Integer.parseInt(cell.getText().toString());
						double weight = 0;
						//Obtenemos peso,precio y descuento
						try{
							weight = Double.parseDouble(peso.getText().toString());
						}catch(Exception e){
							weight = 0;
							peso.setText("0");
						}
						
						//cambiamos el peso en la factura
						data.factura.getLineaId(id).setQuantity(weight);
						System.out.println(id+" "+weight);
						System.out.flush();
												
						double prize = Double.parseDouble(precio.getText().toString());
						double discount =0;
						try{
							discount = Double.parseDouble(descuento.getText().toString());
						}
						catch(Exception e){
							discount =0;
						}
						
						//Obtenemos el total de la fila
						double totalprize =  (double) (prize*weight)*(1-(discount/100));
						totalprize = data.roundTwoDigits(totalprize);
						totalRow.setText(String.valueOf(totalprize));
						
						
					}
					else{
						//El total de la fila es 0
						totalRow.setText("0");
					}
					//Calculamos el nuevo total
					data.recalculateTotal(tabla, total);
				}
				
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}
			});
			
			//Cuando dejemos de editar el peso
			//Tenemos que poner el punto si no está puesto
			peso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					
					GlobalStatic data = (GlobalStatic) getApplication();
					//Fila que se ha cambiado
					TableRow row = (TableRow) v.getParent();
					TextView productName = (TextView) row.getChildAt(1);
					
					//Obtenener el producto que se esta facturando
					Productos producto = data.getProduct(productName.getText().toString());
					
					//Si no se mide en unidades
					if(!producto.getUnits()){
						//Obtener el peso
						EditText weight = (EditText) v;												
						String sweight = weight.getText().toString();
						//Si no esta vacia
						if(!sweight.equals("")){
							//Y no contiene un punto
							if(!sweight.contains(".")){
								//Le ponemos una coma al final
								double peso = Double.parseDouble(sweight);
								peso /= 1000;
								peso = data.roundThreeDigits(peso);
								weight.setText(String.valueOf(peso));
								//RECALCULAR EL TOTAL
								data.recalculateTotal(tabla,total);
							}
						}
					}
				}				
			});
						
			//Añadimos el precio			
			EditText precio = new EditText(this);
			precio.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			//1)Es un numero 2)No pantalla completa para el teclado
			//3)Se permite un punto 4)75px y 5)6 caracteres como máximo
			precio.setInputType(InputType.TYPE_CLASS_NUMBER);
			precio.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			precio.setKeyListener(teclado);
			precio.setWidth(110);
			precio.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });

			//Vamos a ver si el cliente tiene un precio especial
			Object clientprize = null;
			//Si hay cliente
			if(data.cliente!=null){
				//El precio es el precio por cliente
				clientprize = data.cliente.getPrecios().get(next.getName());
			}
			double prize = 0;
			//Si ese precio no es nulo(Hay precio)
			if(clientprize!=null){
				//El precio es el del cliente
				prize = (Double) clientprize;
			}
			else{
				//Si no es el del cliente
				prize = next.getPrize();
			}
			//Redondear el precio y ponerlo
			prize = data.roundTwoDigits(prize);
			precio.setText(String.valueOf(prize));
			
			//Cuando cambie el foco del precio
			precio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
														
					// Obtenemos los elementos necesarios
						TableRow parent = (TableRow) v.getParent();
						TextView cell = (TextView) parent.getChildAt(0);
						EditText peso = (EditText) parent.getChildAt(2);
						EditText precio = (EditText) parent.getChildAt(3);
						EditText descuento = (EditText) parent.getChildAt(4);
						EditText totalRow = (EditText) parent.getChildAt(5);
						GlobalStatic data = (GlobalStatic) getApplication();
						
						//Si el precio no esta vacio
						if(!precio.getText().toString().equals("")){
							
							//Obtenemos los datos necesarios
							int id = Integer.parseInt(cell.getText().toString());
							double weight = 0;
							try{
								weight=Double.parseDouble(peso.getText().toString());
							}catch(Exception e){
								weight = 0;
							}
							
							double newprize = 0;
							try{
								newprize =Double.parseDouble(precio.getText().toString());
							}catch(Exception e){
								newprize = 0;
							}
							
							double prize = data.factura.getLineaId(id).getPrize();
							//Si el precio ha sido cambiado
							if(prize!=newprize){
								double discount = 0;
								//Vemos si el precio es menor que el anterior
								if(prize>newprize){
									//Calculamos un nuevo descuento
									discount = (1-(newprize/prize))*100;
									
								}
								//Ponemos el antiguo precio y el nuevo descuento
								prize=data.roundTwoDigits(prize);
								precio.setText(String.valueOf(prize));
								discount = data.roundTwoDigits(discount);
								descuento.setText(String.valueOf(discount));
								//Calculamos el total de la linea								
								data.factura.getLineaId(id).setDiscount(discount);
								double totalprize =  (double) (prize*weight)*(1-(discount/100));
								//Ponemos el total de la columna
								totalprize = data.roundTwoDigits(totalprize);
								totalRow.setText(String.valueOf(totalprize));
								data.recalculateTotal(tabla, total);
							}
						}					
				}
			});
						
			//Añadimos el descuento que se le realizó
			
			EditText descuento = new EditText(this);
			descuento.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			//Si es cero, ponemos una pista
			if(next.getDiscount()==0){
				descuento.setHint("0%");
			}
			//1)Numero 2)No pantalla completa 3)0123456789.\n
			//4)Ancho 5)Máximo 5 caracteres
			descuento.setInputType(InputType.TYPE_CLASS_NUMBER);
			descuento.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			descuento.setKeyListener(teclado);
			descuento.setWidth(120);
			descuento.setFilters(new InputFilter[] { new InputFilter.LengthFilter(5) });
			
			//Cuando cambie el foco(Has terminado la edicion)
			descuento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					TableRow parent = (TableRow) v.getParent(); 
					TextView cell = (TextView) parent.getChildAt(0);
					EditText peso = (EditText) parent.getChildAt(2);
					EditText precio = (EditText) parent.getChildAt(3);
					EditText descuento = (EditText) parent.getChildAt(4);
					EditText totalRow = (EditText) parent.getChildAt(5);
					
					if(!descuento.getText().toString().equals("")){
						
						int id = Integer.parseInt(cell.getText().toString());
						GlobalStatic data = (GlobalStatic) getApplication();
						double weight = 0;
						double prize = 0;
						double discount = 0;
						try{
							weight = Double.parseDouble(peso.getText().toString());
						}catch(Exception e){
							weight = 0;
						}
						
						try{
							prize = Double.parseDouble(precio.getText().toString());
						}catch(Exception e){
							prize = 0;
						}
						try{
							discount = Double.parseDouble(descuento.getText().toString());
						}catch(Exception e){
							discount = 0;
						}
						descuento.setText(String.valueOf(data.roundTwoDigits(discount)));
						data.factura.getLineaId(id).setDiscount(discount);
						double totalprize =  (double) (prize*weight)*(1-(discount/100));
														
						totalprize = data.roundTwoDigits(totalprize);
						totalRow.setText(String.valueOf(totalprize));
						data.recalculateTotal(tabla, total);
					}
				}
			});
			
			//Calculamos el total
			
			final EditText total = new EditText(this);
			total.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			
			double totalprize = (double) (next.getPrize()*next.getQuantity())*(1-(next.getDiscount()/100));
			totalprize = data.roundTwoDigits(totalprize);
			total.setText(String.valueOf(totalprize));
			total.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
			total.setKeyListener(teclado);
			total.setFilters(new InputFilter[] { new InputFilter.LengthFilter(7) });

			total.setWidth(155);
			
			//Añadimos el boton
			ImageButton boton = new ImageButton(this);
			boton.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.MATCH_PARENT));
			boton.setImageResource(R.drawable.remove);
			boton.setMinimumWidth(300);
			boton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TableRow fila = (TableRow) v.getParent();
					
					tabla.removeView(fila);
					GlobalStatic data = (GlobalStatic) getApplication();
					data.recalculateTotal(tabla, total);
					TextView id = (TextView) fila.getChildAt(0);
					
					LineaFactura linea = data.factura.getLineaId(Integer.parseInt(id.getText().toString()));
					data.factura.getLines().remove(linea);
				}
			});
		
			tr.addView(id,0);
			tr.addView(nombre,1);
			tr.addView(peso,2);
			tr.addView(precio,3);
			tr.addView(descuento,4);
			tr.addView(total,5);
			tr.addView(boton,6);
			
			tabla.addView(tr,new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
			
		}
		

		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		//Añadimos la linea nuevo producto
		TextView nueva = new TextView(this);
		nueva.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		Resources res = getResources();
		String text = String.format(res.getString(R.string.newProduct));
		nueva.setText(text);
		nueva.setTextColor(Color.BLACK);
		nueva.setTextSize(20);
		
		ImageButton boton = new ImageButton(this);
		boton.setImageResource(R.drawable.add);
		boton.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		boton.setFocusable(true);
		boton.setFocusableInTouchMode(true);
		data.recalculateTotal(tabla, total);
		boton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				GlobalStatic global = (GlobalStatic) getApplication();
				global.seleccionandoProductos=true;
				Intent intent = new Intent(BillForm.this,Products.class);
				startActivity(intent);
			}
		});

		
		tr.addView(nueva);
		tr.addView(boton);
		
		
		tabla.addView(tr,new TableLayout.LayoutParams(
	                    LayoutParams.FILL_PARENT,
	                    LayoutParams.WRAP_CONTENT));
		
		
		buttonBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubo
				GlobalStatic data = (GlobalStatic) getApplication();
				Intent intent=null;
				if(data.fromMenu){
					intent = new Intent(BillForm.this,AliActivity.class);
				}
				else{
					intent = new Intent(BillForm.this,DiarySelection.class);
					
				}
				startActivity(intent);
			}
		});
		
		buttonSelect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GlobalStatic data = (GlobalStatic) getApplication();
				data.seleccionandoClientesDiaria=true;
				data.seleccionandoClientesAnual=false;
				Intent intent = new Intent(BillForm.this,Clients.class);
				startActivity(intent);
				
			}
		});
		
		buttonEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				GlobalStatic data =(GlobalStatic) getApplication();
				
				if(data.cliente==null){
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.selectClient));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					
					if(data.factura.getIdFactura()==-1){
					
						data.db.saveBill(data);
						data.cliente.addFactura(data.factura);
						Resources res = getResources();
						String text = String.format(res.getString(R.string.billCreated));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
					}
					else{
						data.db.modifyBill(data);
						Resources res = getResources();
						String text = String.format(res.getString(R.string.billModified));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						
					}
					Intent intent = new Intent(BillForm.this,DiarySelection.class);
					startActivity(intent);
				}

			}
		});
		
		
		//Modificador para la fecha de la factura
		calendar.setOnDateChangeListener(new OnDateChangeListener() {
			
			//Comportamiento cuando se cambia una fecha
						
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				GlobalStatic data = (GlobalStatic) getApplication();
				
				Calendar cal = new GregorianCalendar();
				cal.set(year, month, dayOfMonth);
				
				data.factura.setDate(new java.sql.Date(cal.getTimeInMillis()));
			}
		});
		
		obs.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				GlobalStatic data = (GlobalStatic) getApplication();
				
				data.factura.setView(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stu
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		estado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        GlobalStatic data = (GlobalStatic) getApplication();
		    	
		    	if (isChecked) {
		        	data.factura.setView("Pagado");
		        	data.factura.setPaid(true);
		            obs.setText("Pagado");
		        } else {
		        	data.factura.setView("");
		        	data.factura.setPaid(false);
		        	obs.setText("");
		            // The toggle is disabled
		        }
		    }
		});
		
		buttonPrint.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				GlobalStatic data =(GlobalStatic) getApplication();
				
				obs.requestFocus(1);
				
				if(data.cliente==null){
					//Creamos un toast para avisar
					Resources res = getResources();
					String text = String.format(res.getString(R.string.selectClient));
					Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();
				}
				else{
					
					if(data.factura.getIdFactura()==-1){
					
						data.db.saveBill(data);
						data.cliente.addFactura(data.factura);
						Resources res = getResources();
						String text = String.format(res.getString(R.string.billCreated));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
					}
					else{
						data.db.modifyBill(data);
						Resources res = getResources();
						String text = String.format(res.getString(R.string.billModified));
						Toast msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
						msg.show();
						
					}				

					Resources res = getResources();
					String text = null;
					Toast msg = null;
					
					try {
						data.crearFacturaDiaria(data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Creamos un toast para avisar
					
					text = String.format(res.getString(R.string.printing));
					msg = Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG);
					msg.show();				
				}				
			}
		});
	
	}
	
}
