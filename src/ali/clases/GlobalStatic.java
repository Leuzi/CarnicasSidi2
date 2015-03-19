package ali.clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import android.util.Pair;
import ali.software.R;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.pdfjet.A4;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.Image;
import com.pdfjet.ImageType;
import com.pdfjet.Line;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.RGB;
import com.pdfjet.TextLine;



//Extiende Applicación(serán globales todos estos datos)
public class GlobalStatic extends Application {
	
	public ArrayList<Clientes> clientes;	//Lista de clientes de la base de datos
	public ArrayList<Productos> productos;		//Lista de productos en nuestra base de datos
	public ArrayList<LineaFactura> lista;
	public Clientes cliente;				//Último cliente seleccionado
	public Productos producto;					//Último producto seleccionado
	public Facturas factura;				//Última factura seleccionada
	public boolean seleccionandoClientesDiaria;		//Flag para modificar layouts(vamos a facturas diarias)
	public boolean seleccionandoClientesAnual;		//"" "" "" ""(vamos a facturas anuales)
	public boolean seleccionandoProductos;				//IDEM
	public DBHandler db;							//Nuestra base de datos
	public boolean fromMenu = false;
	public boolean seleccionandoClientesDiaria2;
	public BluetoothAdapter mBluetoothAdapter;

	
	//Será lo primero que haga la aplicación (supuestamente)
	public void onCreate(){
		clientes = new ArrayList<Clientes>();	//Lista de clientes(vacia al principio)
		productos = new ArrayList<Productos>();		//Lista de productos(vacía al principio)
		lista = new ArrayList<LineaFactura>();
		cliente = null;//Cliente por defecto
		producto = null;
		factura = null;	//Factura por defecto
		seleccionandoClientesDiaria=false;			//No estamos seleccionando Clientes para la factura
		seleccionandoClientesAnual=false;		//No estamos seleccionando para las facturas anuales
		seleccionandoProductos=false;				//Estamos seleccionando productos para la factura
		seleccionandoClientesDiaria2=false;
		db= new DBHandler(this);				//Creación  de la base de datos
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
		}
		//Carga de los datos necesarios para el programa(Información de la tablas)
		loadData();
		
		Collections.sort(clientes);
		Collections.sort(productos);
		
		super.onCreate();
	}

	private void loadData() {
		// TODO Auto-generated method stub
		//A nivel de aplicación, todo puede ser accedido desde los productos
		//Y desde los clientes, los cuales tienen facturas y lineas de facturas
		loadProducts();		//Cargar Productos
		loadClients();		//Cargar Clientes
	}


	private void loadProducts() {
		// TODO Auto-generated method stub
		
		this.productos = db.getProducts();//Lista con la información necesaria

	}

	private void loadClients() {
		// TODO Auto-generated method stus
		
		//Cursor con la información de cada cliente
		//menos las facturas
		this.clientes = db.getClients();
		
	}



	//Devuelve la los nombres de los clientes en una lista
	public ArrayList<Pair<String,String>> getNameClients() {
		// TODO Auto-generated method stub
		
		ArrayList<Pair<String,String>> list = new ArrayList<Pair<String,String>>();
		
		Iterator<Clientes> it = clientes.listIterator();
		
		while(it.hasNext()){
			Clientes next=it.next();
			list.add(new Pair<String,String>(next.getName(),""));
		}
		
		return list;
	}

	//Devuelve el cliente identificandolo por el nombre
	//return nulo si no lo encuentra
	public Clientes getCliente(String clientName) {
		// TODO Auto-generated method stub
		
		Clientes found = null;
		
		Iterator<Clientes> it = clientes.listIterator();
		
		while(it.hasNext() && found==null){
			
			Clientes next = (Clientes) it.next();
			
			if(next.getName().equals(clientName)){
				found = next;
			}
		}
		
		return found;
		
	}
	
	public Clientes getClienteId(int idCliente){
		Clientes found = null;
		
		Iterator<Clientes> it = clientes.listIterator();
		
		while(it.hasNext() && found==null){
			
			Clientes next = (Clientes) it.next();
			
			if(next.getIdClient() == idCliente){
				found = next;
			}
		}
		
		return found;
		
	}
	

	//Devuelve la los nombres de los productos en una lista
	public ArrayList<String> getNameProducts() {
		
		ArrayList<String> names = new ArrayList<String>();
		// TODO Auto-generated method stub
		
		Iterator<Productos> it = this.productos.iterator();
		
		while(it.hasNext()){
			Productos next = it.next();
			
			names.add(next.getName());
		}
		
		return names;
	}
	
	


	//Devuelve el producto identificandolo por el nombre
	//return nulo si no lo encuentra
	public Productos getProduct(String selectedProduct) {
		// TODO Auto-generated method stub
		
		Productos found = null;
		
		Iterator<Productos> it = this.productos.iterator();
		
		while(it.hasNext() && found==null){
			Productos next = (Productos) it.next();
			
			if(next.getName().equals(selectedProduct)){
				found = next;
			}
		}
		
		return found;
	}

	//Cambia el cliente en la ArrayList por el cliente en estatico
	//Lo modifica en la base de datos
	public void modifyClient() {
		// TODO Auto-generated method stub
		//Obtener el cliente guardado en la base de datos
		//A través de su nombre
		Clientes client = this.getClienteId(this.cliente.getIdClient());
		
		//Lo elimino de la lista
		this.clientes.remove(client);
		
		//Copio sus facturas al modificado
		this.cliente.setFacturas(cliente.getFactura());
		
		//Lo añado a la lista
		this.clientes.add(this.cliente);		
		
		//Modificar la base de datos
		db.modifyClient(this.cliente);
		
	}

	public int eraseClient(GlobalStatic data) {
		// TODO Auto-generated method stub
		
		int value = 0;
		
		if(data.cliente.getFactura().size()==0){
			value = 1;
			//Eliminamos al cliente de la base de datos
			//Junto con las conexiones que tenga
			db.removeClient(this.cliente.getIdClient());
			
			//Lo eliminamos del sistema
			clientes.remove(cliente);
		}
		else{
			value = -1;
		}
		
		return value;
		
		
		
	}

	//Modifica un producto seleccionado
	public void modifyProduct() {
		
		
		//Obtenemos el producto a cambiar
		Productos product = this.getProductId(this.producto.getIdProducto());
		
		//Obtenemos todos los clientes
		Iterator<Clientes> it = this.clientes.iterator();
		
		//Para cada cliente
		while(it.hasNext()){
			
			Clientes nextClient = (Clientes) it.next();
			//Obtenemos las facturas
			Iterator<Facturas> it2 = nextClient.getFactura().iterator();
			
			while(it2.hasNext()){
				//Para cada factura del cliente
				Facturas nextFactura = (Facturas) it2.next();
				
				//Obtenemos las lineas de la facturas
				Iterator<LineaFactura> it3 = nextFactura.getLines().iterator();
				
				while(it3.hasNext()){
					//Obtenemos la linea
					LineaFactura nextLinea = (LineaFactura) it3.next();
					
					if(nextLinea.getName().equals(product.getName())){
						nextLinea.setName(product.getName());
					}
				}
				
			}
		}
		
		//Lo eliminamos de la lista actual
		this.productos.remove(product);
		
		//Añadimos el que queremos
		this.productos.add(this.producto);				
		
		db.modifyProducto(product);
		
	}

	//Devuelve el producto identificandolo por el nombre
	//return nulo si no lo encuentra
	public Productos getProductId(int idProducto) {
		// TODO Auto-generated method stub
		
		Productos found = null;
		
		Iterator<Productos> it = this.productos.iterator();
		
		while(it.hasNext() && found==null){
			Productos next = (Productos) it.next();
			
			if(next.getIdProducto() == idProducto){
				found = next;
			}
		}
		
		return found;
	}

	//Eliminamos un producto
	public int eraseProduct(GlobalStatic data) {
		// TODO Auto-generated method stu
		//Obtenemos el producto
		Productos product = this.getProduct(this.producto.getName());
		
		//debemos ver si el producto está en una factura
		//si es así no se puede borrar
		
		Iterator<Clientes> itClientes = data.clientes.iterator();
		
		int value=0;
		
		boolean encontrado = false;
		
		while(itClientes.hasNext() && encontrado==false){
			
			Clientes nextClient = (Clientes) itClientes.next();
			
			Iterator<Facturas> itFacturas = nextClient.getFactura().iterator();
			
			while(itFacturas.hasNext() && encontrado == false){
				
				Facturas facturas = (Facturas) itFacturas.next();
				
				Iterator<LineaFactura> itLineas = facturas.getLines().iterator();
				
				while(itLineas.hasNext() && encontrado ==false){
					LineaFactura nextLinea = (LineaFactura) itLineas.next();
					
					if(nextLinea.getName().equals(product.getName())){
						encontrado=true;
					}
				}
			}
		}
		
		if(encontrado==false){	
			//Lo eliminamos de la lista de productos
			productos.remove(product);
			
			//Para cada cliente
			itClientes = this.clientes.iterator();
			
			while(itClientes.hasNext()){
				
				Clientes next = (Clientes) itClientes.next();
				
				//Obtenemos sus facturas
				Iterator<Facturas> itFacturas = next.getFactura().iterator();
				
				while(itFacturas.hasNext()){
					Facturas next2 = (Facturas) itFacturas.next();
					//Obtenemos las lineas de esa factura
					Iterator<LineaFactura> lineas = next2.getLines().iterator();
					
					while(lineas.hasNext()){
						
						LineaFactura linea = (LineaFactura) lineas.next();
						//Si el producto estaba, hay que borrarlo
						if(linea.getName().equals(producto.getName())){
							next2.removeLinea(linea);
						}
					}
				}				
			}
			value = 1;
			//Lo eliminamos de la base de datos
			//Eliminando las relaciones con las facturas
			this.db.removeProducto(producto.getIdProducto());
		}
		else{
			value= -1;
		}
		
		return value;
	}

	//Devuelve la factura deseada para un cliente dado
	public Facturas getFacturasId(Clientes client,int idFactura){
		
		Facturas bill = null;
		
		Iterator<Facturas> it = client.getFactura().iterator();
		
		while(it.hasNext()&&bill==null){
			Facturas next = (Facturas) it.next();
			
			if(next.getIdFactura()==idFactura){
				bill = next;
			}
		}
		
		return bill;
	} 
	
	public static boolean createDirIfNotExists(String path) {
	    boolean ret = true;

	    File file = new File(Environment.getExternalStorageDirectory(), path);
	    if (!file.exists()) {
	        if (!file.mkdirs()) {
	            Log.e("TravellerLog :: ", "Problem creating Image folder");
	            ret = false;
	        }
	    }
	    return ret;
	}
	
	//Crea una factura anual//////////////////////////////////////////////////////////////////////////
	public void crearFacturaAnual(GlobalStatic data,java.util.Date since, java.util.Date until,java.util.Date daybill,int optionPayment,int optionIndividuales) throws Exception {
		
		if(optionIndividuales==1){
			crearFacturaAnualMeses(data,since,until,daybill, optionPayment,optionIndividuales);
		}
		else{
			SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy");
			
			String nombre = "Facturas/"+data.cliente.getName()+"/"+df2.format(since)+"/"+df.format(since)+df.format(until);
			
			int billNumber = data.cliente.getNextFactura();
			
			if((new File(Environment.getExternalStorageDirectory(),nombre)).exists()){
				File index = new File(Environment.getExternalStorageDirectory(),nombre);
				FileReader inputFil = new FileReader(index);
				BufferedReader in = new BufferedReader(inputFil);
				String s =in.readLine();
				billNumber = Integer.parseInt(s);
				in.close();
				inputFil.close();
			}
			else{
				GlobalStatic.createDirIfNotExists("Facturas/"+data.cliente.getName());
				GlobalStatic.createDirIfNotExists("Facturas/"+data.cliente.getName()+"/"+df2.format(since));
				File file = new File(Environment.getExternalStorageDirectory(),nombre);
			    file.setWritable(true);
			    
			    FileWriter out = new FileWriter(file);
			    BufferedWriter out2 = new BufferedWriter(out);
			    
			    out2.write(String.valueOf(data.cliente.getNextFactura()));
			    
			    out2.close();
			    out.close();
			    data.getNextFactura();
			}
			
			
			//Creamos el flujo de salida
			
			
			File file = new File(Environment.getExternalStorageDirectory(),nombre+".pdf");
									
			FileOutputStream fos = new FileOutputStream(file);
			int numClient = data.cliente.getIdClient();
			if(numClient>100){
				numClient*=10000;
				
			}
			else if(numClient>10){
				numClient*=100000;
			}
			else{
				numClient*=1000000;
			}
			numClient+=billNumber;
			
			//Creamos el pdf que tenga esa salida
			PDF pdf = new PDF(fos);
			
			ArrayList<Facturas> lista = data.getFacturasFecha(data, since, until,optionPayment);
			//Creamos el flujo de salida
					
			Iterator<Facturas> it = lista.iterator();
			
			//El numero de lineas que se van a mostrar
	        int number = 0;
	
	        	
			while(it.hasNext()){
				number += ((Facturas)it.next()).getLines().size();
			}
	        int global=0; //numero de líneas mostradas hasta el momento        
	        int sheet = 0;//Numero de pagina
	        
	        int maxLines=48; //Numero máximo de líneas que se van a mostrar por página
			        
	        //Numero de páginas necesarias
	        int totalPages = (number/maxLines) +1;
	        
	        //Total de la factura
	        double totalBill=0;
			        
			//Cantidad de puntos que es un centímetro
			double cm = 30.9;
	     
				        
			int borde1Image = R.drawable.borde1;
	    	int borde4Image = R.drawable.borde4;
	    	int borde5Image = R.drawable.borde5;
	    	int logoImage   = R.drawable.logofactura;
	    	int borde3Image = R.drawable.borde3;
	    	Image logo = new Image(pdf,getResources().openRawResource(logoImage),ImageType.PNG);
	    	Image borde1 = new Image(pdf,getResources().openRawResource(borde1Image),ImageType.PNG);
	    	Image borde3 = new Image(pdf,getResources().openRawResource(borde3Image),ImageType.PNG);
	    	
	    	Image borde5 = new Image(pdf,getResources().openRawResource(borde5Image),ImageType.PNG);
	    	Font fuente1 = new Font(pdf,CoreFont.HELVETICA);
	    	TextLine line = new TextLine(fuente1);
	    	fuente1.setSize(10);
	        //Las páginas que se van a mostrar
	        Page page = new Page(pdf, A4.PORTRAIT);
	    	sheet++;
	    	double counter=9*cm;
	    	//Crear los elementos que no cambias (cliente, numero factura etc)
	    	staticDataBill(pdf,logo,borde1,borde3,borde4Image,borde5,data,cm, page, fuente1,1,since,until,daybill,numClient,optionPayment);
		        	     	
		    int total = 0;//Numero de lineas que llevamos
		    //Rellenar de una en una
		    //los albaranes
	    	it = lista.iterator();
	    	
		    while(it.hasNext()){
		    	fuente1.setSize(9);		    	
		    	Facturas factura = (Facturas) it.next();			    
		    	boolean primero=true;		    	
		    	Iterator<LineaFactura> itFacturas = factura.getLines().iterator();
		    	String albaran = String.valueOf(factura.getIdFactura());
		    	
		    	
		    	while(itFacturas.hasNext()){
		    		fuente1.setSize(9);	
		    		LineaFactura linea = (LineaFactura) itFacturas.next();
		    		global++;
		    		total++;
		    		
		    		line.setFont(fuente1);
		    		line.setText(albaran);
		    		line.setPosition(1.6*cm,counter);
		    		if(primero){
		    			primero=false;
		    			albaran="-";
		    		}
		    		
		    		line.drawOn(page);
		    		
		    		Productos producto = data.getProduct(linea.getName());
		    		
		    		line.setText(data.getThreeDecimals(data.roundThreeDigits(linea.getQuantity())));
		    		line.setPosition(2.8*cm+getWidth(linea.getQuantity(),cm)*0.65,counter);
		    		line.drawOn(page);
	            	
		    		if(producto.getUnits()){
		    			line.setText("und");
		    		}
		    		else{
		    			line.setText("kg");
		    		}
		    		line.setPosition(4.9*cm, counter);
		    		line.drawOn(page);
	        		
		    		String nombre1 = linea.getName();
		    		line.setText(nombre1);
		    		line.setPosition(5.8*cm,counter);
		    		line.drawOn(page);
	        		line.setText(data.getTwoDecimals(linea.getPrize()));
	        		line.setPosition(12.9*cm+getWidth(linea.getPrize(),cm)*0.65,counter);
	        		line.drawOn(page);
	            	
	        		line.setText("€");
	        		line.setPosition(14.9*cm, counter);
	        		line.drawOn(page);
	            	
	        		String descuento="";
	        		if(linea.getDiscount()!=0){
	        			descuento = String.valueOf(linea.getDiscount()) +"%";
	        			line.setText(descuento);
	        			line.setPosition(15*cm,counter);
	        			line.drawOn(page);
	        		}
	
	        		double totalRow = (double) (linea.getPrize()*linea.getQuantity())*(1-(linea.getDiscount()/100));
	        		totalRow = data.roundTwoDigits(totalRow);
	        		
	        		line.setText(data.getTwoDecimals(totalRow));
	        		line.setPosition(15.9*cm+getWidth(totalRow,cm)*0.65,counter);
	            	line.drawOn(page);
	            	
	            	line.setText("€");
	            	line.setPosition(17.9*cm,counter);
	            	line.drawOn(page);
	            	
	        		counter+=0.285*cm;
	            	
	        		totalBill+=totalRow;   
	        		//Si hemos llegado al final
	        		if(global==number){
	        			fuente1.setSize(10);
	        			//Rellenar el total y las observaciones
	    		    	SimpleDateFormat df1 = new SimpleDateFormat("dd MMMM yyyy");
	    		    	
	    		    	line.setText("Albaranes desde el dia " +df1.format(since));
	            		line.setPosition(1.2*cm, 23.7*cm);
	                	line.drawOn(page);
	                	
	                	line.setText("hasta el dia " + df1.format(until));
	                	line.setPosition(1.2*cm, 24.3*cm);
	                	line.drawOn(page);
	                	
	
	                	totalBill = data.roundTwoDigits(totalBill);
	                	line.setText(data.getTwoDecimals(totalBill) +" €");
	                	line.setPosition(16.0*cm, 25.8*cm);
	                	line.drawOn(page);
	                	
	                	if(optionPayment!=0){
	                		if(optionPayment==1){
	                    		line.setText("Solo No Pagadas");
	                    	}
	                    	else if(optionPayment==2){
	                    		line.setText("Solo Pagadas");
	                    	}
	                		line.setPosition(1.0*cm, 24.9*cm);
	                    	line.drawOn(page);
	                	}
	        		}
	        		else{
	        			if(total==maxLines){
	        				fuente1.setSize(10);
	        				//No hemos terminado
			        		//Necesitamos otra página
			        		line.setText("Página  " +sheet+ "  de  " +totalPages);
			        		line.setPosition(1.2*cm, 23.9*cm);
			            	line.drawOn(page);
			            	
	        				page = new Page(pdf, A4.PORTRAIT);
	        		    	sheet++;
	        		    	counter=9*cm;
	        		    	//Crear los elementos que no cambias (cliente, numero factura etc)
	        		    	staticDataBill(pdf,logo,borde1,borde3,borde4Image,borde5,data,cm, page, fuente1,1,since,until,daybill,numClient,optionPayment);
	        			        	     	
	        			    total = 0;//Numero de lineas que llevamos
	        			    //Rellenar de una en una
	        			    //los albaranes
	        			}
	        		}
	        				        		
		    	}
			    	
		    }  	
			    
	        
	        pdf.flush();
	        
	        fos.close();
	        if(file.exists()){
	        	        	
		        Uri path = Uri.fromFile(file); 
		        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
		        pdfIntent.setDataAndType(path, "application/pdf");
		        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        try
		        {
		            startActivity(pdfIntent);
		        }
		        catch(ActivityNotFoundException e)
		        {
		            Toast.makeText(GlobalStatic.this, "No Application available to view pdf", Toast.LENGTH_LONG).show(); 
		        }
	        }
			
			}
			
		
	}
	
	
	
	private void crearFacturaAnualMeses(GlobalStatic data, Date since,
		Date until,Date daybill, int optionPayment, int optionIndividuales) throws Exception {
		
		File file = new File(Environment.getExternalStorageDirectory(), "factura.pdf");
		
		FileOutputStream fos = new FileOutputStream(file);
		int numClient = 0;
		
		//Creamos el pdf que tenga esa salida
		PDF pdf = new PDF(fos);
		
		ArrayList<Object> lista = (ArrayList<Object>) data.getBills(since, until, optionPayment);
		//Creamos el flujo de salida
				
		//El numero de lineas que se van a mostrar
        int number = lista.size()/2;

		int global=0; //numero de líneas mostradas hasta el momento        
        int sheet = 0;//Numero de pagina
        
        int maxLines=24; //Numero máximo de líneas que se van a mostrar por página
		        
        //Numero de páginas necesarias
        int totalPages = (number/maxLines) +1;
        
        //Total de la factura
        double totalBill=0;
		        
		//Cantidad de puntos que es un centímetro
		double cm = 30.9;
     
			        
		int borde1Image = R.drawable.borde1;
    	int borde4Image = R.drawable.borde4;
    	int borde5Image = R.drawable.borde5;
    	int logoImage   = R.drawable.logofactura;
    	int borde3Image = R.drawable.borde3;
    	Image logo = new Image(pdf,getResources().openRawResource(logoImage),ImageType.PNG);
    	Image borde1 = new Image(pdf,getResources().openRawResource(borde1Image),ImageType.PNG);
    	Image borde3 = new Image(pdf,getResources().openRawResource(borde3Image),ImageType.PNG);
    	
    	Image borde5 = new Image(pdf,getResources().openRawResource(borde5Image),ImageType.PNG);
    	Font fuente1 = new Font(pdf,CoreFont.HELVETICA);
    	TextLine line = new TextLine(fuente1);
    	
        //Las páginas que se van a mostrar
        Page page = new Page(pdf, A4.PORTRAIT);
    	sheet++;
    	double counter=8.9*cm;
    	//Crear los elementos que no cambias (cliente, numero factura etc)
    	staticDataBill(pdf,logo,borde1,borde3,borde4Image,borde5,data,cm, page, fuente1,1,since,until,daybill,numClient,optionPayment);
	        	     	
	    int total = 0;//Numero de lineas que llevamos
	    //Rellenar de una en una
	    //los albaranes
	    
	    Iterator<Object> it = lista.iterator();
	    
	    while(it.hasNext()){
	    		   
	    	global++;
    		total++;
	    	String nombre = (String) it.next();
	    	
	    	Object obj = it.next();
	    	String sobj = obj.toString();
	    	double totalRow = Double.parseDouble(sobj);
	    	totalRow = data.roundTwoDigits(totalRow);
	    
	    	line.setText(nombre);
	    	line.setPosition(6*cm, counter);
	    	line.drawOn(page);
	    	
	    	line.setText(data.getTwoDecimals(totalRow));
	    	line.setPosition(14*cm, counter);
	    	line.drawOn(page);
	    	
	    	line.setText("€");
	    	line.setPosition(15.7*cm, counter);
	    	line.drawOn(page);
	    	
	    	counter+=0.55*cm;
            	
        	totalBill+=totalRow;   
    		//Si hemos llegado al final
    		if(global!=number){

    			if(total==maxLines){
    				
    				//No hemos terminado
	        		//Necesitamos otra página
	        		line.setText("Página  " +sheet+ "  de  " +totalPages);
	        		line.setPosition(1.4*cm, 23.6*cm);
	            	line.drawOn(page);
	            	
    				page = new Page(pdf, A4.PORTRAIT);
    		    	sheet++;
    		    	counter=8.9*cm;
    		    	//Crear los elementos que no cambias (cliente, numero factura etc)
    		    	staticDataBill(pdf,logo,borde1,borde3,borde4Image,borde5,data,cm, page, fuente1,1,since,until,daybill,numClient,optionPayment);
    			        	     	
    			    total = 0;//Numero de lineas que llevamos
    			    //Rellenar de una en una
    			    //los albaranes
    			}
    		}  
    		//Si hemos llegado al final
    		if(global==number){
    			//Rellenar el total y las observaciones
		    	SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		    	
		    	line.setText("Factras desde el dia " +df.format(since));
        		line.setPosition(1.4*cm, 23.4*cm);
            	line.drawOn(page);
            	
            	line.setText("hasta el dia " + df.format(until));
            	line.setPosition(1.4*cm, 24*cm);
            	line.drawOn(page);
            	
            	if(optionPayment!=0){
            		if(optionPayment==1){
                		line.setText("Solo No Pagadas");
                	}
            		else if(optionPayment==2){
                		line.setText("Solo Pagadas");
                	}
            		line.setPosition(1.4*cm, 24.6*cm);
                	line.drawOn(page);
                	
                	totalBill = data.roundTwoDigits(totalBill);
                	line.setText(totalBill +" €");
                	line.setPosition(16.2*cm, 25.5*cm);
                	line.drawOn(page);
            	}
            	
            	

            	totalBill = data.roundTwoDigits(totalBill);
            	line.setText(data.getTwoDecimals(totalBill) +" €");
            	line.setPosition(16.2*cm, 25.5*cm);
            	line.drawOn(page);
    		}
    		else{
    			if(total==maxLines){
    				
    				//No hemos terminado
	        		//Necesitamos otra página
	        		line.setText("Página  " +sheet+ "  de  " +totalPages);
	        		line.setPosition(1.4*cm, 23.6*cm);
	            	line.drawOn(page);
	            	
    				page = new Page(pdf, A4.PORTRAIT);
    		    	sheet++;
    		    	counter=8.9*cm;
    		    	//Crear los elementos que no cambias (cliente, numero factura etc)
    		    	staticDataBill(pdf,logo,borde1,borde3,borde4Image,borde5,data,cm, page, fuente1,1,since,until,daybill,numClient,optionPayment);
    			        	     	
    			    total = 0;//Numero de lineas que llevamos
    			    //Rellenar de una en una
    			    //los albaranes
    			}
    		}
	    }
        pdf.flush();
        
        fos.close();
        if(file.exists()){
        	        	
	        Uri path = Uri.fromFile(file); 
	        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
	        pdfIntent.setDataAndType(path, "application/pdf");
	        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        try
	        {
	            startActivity(pdfIntent);
	        }
	        catch(ActivityNotFoundException e)
	        {
	            Toast.makeText(GlobalStatic.this, "No Application available to view pdf", Toast.LENGTH_LONG).show(); 
	        }
        }
		    	
	}  	
		
	private ArrayList<Object> getBills(Date since, Date until,int option) {
		// TODO Auto-generated method stub
		//Lista que devolveremos con la estructura
		//{{"Total <Mes>",Cantidad},{{"Total <Mes>",Cantidad}}
		ArrayList<Object> lista =new ArrayList<Object>();
		
		//Meses del año
		String meses[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		
		//Inicialización de fechas 
		Calendar calSince = Calendar.getInstance();
		Calendar calUntil = Calendar.getInstance();		
		calSince.setTimeInMillis(since.getTime());
		calUntil.setTimeInMillis(until.getTime());
		
		//Calendarios auxiliares
		Calendar calAux1=Calendar.getInstance();
		Calendar calAux2=Calendar.getInstance();
		calAux1.setTimeInMillis(since.getTime());
		calAux2.setTimeInMillis(since.getTime());		
		
		//Le añadimos un mes al calendario auxiliar
		calAux2.set(Calendar.DAY_OF_MONTH, 1);
		calAux2.add(Calendar.MONTH, 1);
		calAux2.add(Calendar.DAY_OF_MONTH,-1);		
				
		//Mientras que el calendario no se pase
		while(calUntil.compareTo(calAux2)>=0){
			//Cogemos todas la facturas en ese periodo
			Iterator<Facturas> it = this.getFacturasFecha(this, calAux1.getTime(), calAux2.getTime(), option).iterator();
			
			double total=0;
			//Les sumamos el total
			while(it.hasNext()){
				Facturas factura = it.next();
				total += factura.getTotal();				
			}
			
			//Las añadimos a la lista
			lista.add("Total " + meses[calAux1.get(Calendar.MONTH)]+" "+calAux1.get(Calendar.YEAR));
			
			lista.add(total);
			//Actualizamos calendarios
			calAux2.add(Calendar.DATE,1);
			calAux1.setTimeInMillis(calAux2.getTimeInMillis());
			calAux2.add(Calendar.MONTH, 1);
			calAux2.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
		calAux2.setTimeInMillis(until.getTime());
		
		
		System.out.println(df.format(calAux1.getTime()));
		System.out.println(df.format(calAux2.getTime()));
		System.out.flush();
		
		if(calAux1.compareTo(calAux2)<=0){
			Iterator<Facturas> it = this.getFacturasFecha(this, calAux1.getTime(), calAux2.getTime(), option).iterator();
		
			double total=0;
		
			while(it.hasNext()){
				Facturas factura = it.next();
				total += factura.getTotal();				
			}
				
			lista.add("Total " + meses[calAux1.get(Calendar.MONTH)]+" "+calAux1.get(Calendar.YEAR));
		
			lista.add(total);
			
		}
	
		
		return lista;
	}

	private ArrayList<Facturas> getFacturasFecha(GlobalStatic data,Date since, Date until,int optionPayment) {
		// TODO Auto-generated method stub
		ArrayList<Facturas> lista = new ArrayList<Facturas>();
		
		Iterator<Facturas> it = data.cliente.getFactura().iterator();
		
		long longSince = since.getTime();
		long longUntil = until.getTime();
		
		Calendar calSince = Calendar.getInstance();
		Calendar calUntil = Calendar.getInstance();
		calSince.setTimeInMillis(longSince);
		calUntil.setTimeInMillis(longUntil);
		
		while(it.hasNext()){
			Facturas factura = (Facturas) it.next();
			long dateBill = factura.getDate().getTime();
			Calendar calBill = Calendar.getInstance();
			calBill.setTimeInMillis(dateBill);
			
			int comp1= calSince.compareTo(calBill);
			int comp2= calUntil.compareTo(calBill);
					
			if(comp1<=0 && comp2>=0){
				
				if(optionPayment==0){
					lista.add(factura);
				}
				else if(optionPayment==1){
					if(!factura.isPaid()){
						lista.add(factura);
					}
				}
				else{
					if(factura.isPaid()){
						lista.add(factura);
					}
				}
				
			}
			
		}
		
		return lista;
	}

	//Metodo para crear factura///////////////////////////////////
	public void crearFacturaDiaria(GlobalStatic data) throws Exception {
		// TODO Auto-generated method stub
		
		//Creamos el flujo de salida
		File file = new File(Environment.getExternalStorageDirectory(), "Example_13.pdf");
		
        FileOutputStream fos = new FileOutputStream(file);

		//Creamos el pdf que tenga esa salida
        PDF pdf = new PDF(fos);
        
        //El numero de lineas que se van a mostrar
        int number = this.factura.getLines().size();
        int global=0; //numero de líneas mostradas hasta el momento        
        int sheet = 0;//Numero de pagina
        
        int maxLines=24; //Numero máximo de líneas que se van a mostrar por página
        
        //Numero de páginas necesarias
        int totalPages = (data.factura.getLines().size()/maxLines) +1;
        
        //Total de la factura
        double totalBill=0;
        
        //Cantidad de puntos que es un centímetro
        double cm = 30.9;
        //Iterador para las lineas de la factura
        Iterator<?> it = this.factura.getLines().iterator();
        
        //Booleano para ver cuando paramos
        boolean end=false;
        
        
        int borde1Image = R.drawable.borde1;
    	int borde4Image = R.drawable.borde4;
    	int borde5Image = R.drawable.borde5;
    	int logoImage   = R.drawable.logofactura;
    	int borde3Image = R.drawable.borde3;
    	Image logo = new Image(pdf,getResources().openRawResource(logoImage),ImageType.PNG);
    	Image borde1 = new Image(pdf,getResources().openRawResource(borde1Image),ImageType.PNG);
    	Image borde3 = new Image(pdf,getResources().openRawResource(borde3Image),ImageType.PNG);
    	
    	Image borde5 = new Image(pdf,getResources().openRawResource(borde5Image),ImageType.PNG);
    	Font fuente1 = new Font(pdf,CoreFont.HELVETICA);
    	TextLine line = new TextLine(fuente1);

    	
    	//Para cada página, tenemos que
        //Crear los elementos que no cambias (cliente, numero albaran etc)
        //Rellenar la tabla
        //Poner las observaciones
        while(end==false){
            //Las páginas que se van a mostrar
            Page page = new Page(pdf, A4.PORTRAIT);
        	sheet++;
        	double counter=9.2*cm;
        	//Crear los elementos que no cambias (cliente, numero factura etc)
        	staticDataBill(pdf,logo,borde1,borde3,borde4Image,borde5,data,cm, page, fuente1,0, null, null,new Date(),0,0);
        	     	
        	int total = 0;//Numero de lineas que llevamos
        	//Rellenar la tabla
        	while(it.hasNext()&& total<maxLines){
        		total++;
        		global++;
        		LineaFactura linea = (LineaFactura) it.next();
        		
        		Productos producto = data.getProduct(linea.getName());
        		
        		line.setText(data.getThreeDecimals(data.roundThreeDigits(linea.getQuantity())));
        		
        		line.setPosition(0.0*cm+getWidth(linea.getQuantity(),cm),counter);
        		line.drawOn(page);
        		if(producto.getUnits()){
        			line.setText("und");
        		}
        		else{
        			line.setText("kg");
        		}
        		line.setPosition(3.2*cm, counter);
        		line.drawOn(page);
            	
            	String nombre = linea.getName();
        		line.setText(nombre);
        		line.setPosition(4.4*cm,counter);
        		line.drawOn(page);
            	
        		line.setText(data.getTwoDecimals(linea.getPrize()));
        		line.setPosition(10*cm+getWidth(linea.getPrize(),cm),counter);
            	line.drawOn(page);
            	line.setText("€");
            	line.setPosition(13*cm,counter);
            	line.drawOn(page);
            	
        		String descuento="";
        		if(linea.getDiscount()!=0){
        			descuento = String.valueOf(linea.getDiscount()) +"%";
        			line.setText(descuento);
            		line.setPosition(13.8*cm,counter);
                	line.drawOn(page);
        		}
        		double totalRow = (double) (linea.getPrize()*linea.getQuantity())*(1-(linea.getDiscount()/100));
        		totalRow = data.roundTwoDigits(totalRow);
        		
        		line.setText(data.getTwoDecimals(totalRow));
        		line.setPosition(15.0*cm+getWidth(totalRow,cm),counter);
            	line.drawOn(page);
            	
            	line.setText("€");
            	line.setPosition(17.9*cm, counter);
            	line.drawOn(page);
            	
            	counter+=0.55*cm;
            	
        		totalBill+=totalRow;             		
        	}
        	 //Poner las observaciones
        	if(global<number){
        		//No hemos terminado
        		//Necesitamos otra página
        		line.setText("Página  " +sheet+ "  de  " +totalPages);
        		line.setPosition(1.2*cm, 23.9*cm);
            	line.drawOn(page);
        	}
        	else{
        		end=true;
        		line.setText(data.factura.getView());
        		line.setPosition(1.2*cm, 23.9*cm);
            	line.drawOn(page);
            	totalBill = data.roundTwoDigits(totalBill);
            	line.setText(data.getTwoDecimals(totalBill) +"€");
            	line.setPosition(16.2*cm, 25.8*cm);
            	line.drawOn(page);
        	}
        	
        }


        
        pdf.flush();
        
        fos.close();
        if(file.exists()){
        	        	
	        Uri path = Uri.fromFile(file); 
	        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
	        pdfIntent.setDataAndType(path, "application/pdf");
	        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        try
	        {
	            startActivity(pdfIntent);
	        }
	        catch(ActivityNotFoundException e)
	        {
	            Toast.makeText(GlobalStatic.this, "No Application available to view pdf", Toast.LENGTH_LONG).show(); 
	        }
        }	
	}
	
	//Metodo para crear Lista de precios///////////////////////////////////
		public void crearListaPrecios(GlobalStatic data) throws Exception {
			// TODO Auto-generated method stub
			
			//Creamos el flujo de salida
			File file = new File(Environment.getExternalStorageDirectory(), "Example_13.pdf");
			
	        FileOutputStream fos = new FileOutputStream(file);

			//Creamos el pdf que tenga esa salida
	        PDF pdf = new PDF(fos);
	        
	        //El numero de lineas que se van a mostrar
	        int number = this.productos.size();
	        int global=0; //numero de líneas mostradas hasta el momento        
	        int sheet = 0;//Numero de pagina
	        
	        int maxLines=24; //Numero máximo de líneas que se van a mostrar por página
	        
	        //Numero de páginas necesarias
	        int totalPages = (this.productos.size()/maxLines) +1;

	        
	        //Cantidad de puntos que es un centímetro
	        double cm = 30.9;
	        //Iterador para las lineas de la factura
	        Iterator<?> it = this.productos.iterator();
	        
	        //Booleano para ver cuando paramos
	        boolean end=false;
	        
	        
	        int borde1Image = R.drawable.borde1;
	    	int borde4Image = R.drawable.borde4;
	    	int borde5Image = R.drawable.borde5;
	    	int logoImage   = R.drawable.logofactura;
	    	int borde3Image = R.drawable.borde3;
	    	Image logo = new Image(pdf,getResources().openRawResource(logoImage),ImageType.PNG);
	    	Image borde1 = new Image(pdf,getResources().openRawResource(borde1Image),ImageType.PNG);
	    	Image borde3 = new Image(pdf,getResources().openRawResource(borde3Image),ImageType.PNG);
	    	
	    	Image borde5 = new Image(pdf,getResources().openRawResource(borde5Image),ImageType.PNG);
	    	Font fuente1 = new Font(pdf,CoreFont.HELVETICA);
	    	TextLine line = new TextLine(fuente1);

	    	
	    	//Para cada página, tenemos que
	        //Crear los elementos que no cambias (cliente, numero albaran etc)
	        //Rellenar la tabla
	        //Poner las observaciones
	        while(end==false){
	            //Las páginas que se van a mostrar
	            Page page = new Page(pdf, A4.PORTRAIT);
	        	sheet++;
	        	double counter=9.2*cm;
	        	staticDataBill(pdf,logo,borde1,borde3,borde4Image,borde5,data,cm, page, fuente1,2, null, null,null,0,0);

	        	    	
	        	int total = 0;//Numero de lineas que llevamos
	        	//Rellenar la tabla
	        	while(it.hasNext()&& total<maxLines){
	        		total++;
	        		global++;
	        		Productos producto = (Productos) it.next();
	        		
	        		line.setText("");
	        		
	        		line.setPosition(1.0*cm,counter);
	        		line.drawOn(page);
	        		if(producto.getUnits()){
	        			line.setText("und");
	        		}
	        		else{
	        			line.setText("kg");
	        		}
	        		line.setPosition(3.2*cm, counter);
	        		line.drawOn(page);
	            	
	            	String nombre = producto.getName();
	        		line.setText(nombre);
	        		line.setPosition(4.4*cm,counter);
	        		line.drawOn(page);
	            	
	        		double prize = producto.getPrize();
	        		
	        		if(data.cliente.getPrecios().containsKey(producto.getName())){
	        			Object obj = data.cliente.getPrecios().get(producto.getName());
	        			prize = (Double) obj;
	        		}
	        		
	        		line.setText(data.getTwoDecimals(prize));
	        		line.setPosition(10.3*cm+getWidth(prize,cm),counter);
	            	line.drawOn(page);
	            	line.setText("€");
	            	line.setPosition(13.3*cm,counter);
	            	line.drawOn(page);
	            	

	        		            	
	            	counter+=0.55*cm;
           		
	        	}
	        	 //Poner las observaciones
	        	if(global<number){
	        		//No hemos terminado
	        		//Necesitamos otra página
	        		line.setText("Página  " +sheet+ "  de  " +totalPages);
	        		line.setPosition(1.2*cm, 23.9*cm);
	            	line.drawOn(page);
	        	}
	        	else{
	        		end=true;
	        	}
	        	
	        }


	        
	        pdf.flush();
	        
	        fos.close();
	        if(file.exists()){
	        	        	
		        Uri path = Uri.fromFile(file); 
		        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
		        pdfIntent.setDataAndType(path, "application/pdf");
		        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        try
		        {
		            startActivity(pdfIntent);
		        }
		        catch(ActivityNotFoundException e)
		        {
		            Toast.makeText(GlobalStatic.this, "No Application available to view pdf", Toast.LENGTH_LONG).show(); 
		        }
	        }	
		}
	
	private double getWidth(double prize,double cm) {
		// TODO Auto-generated method stub
			
			double dif=0.27*cm;
			int num_cifras=(int)Math.log10(prize);
			double value=0;
			if(num_cifras<=0){
				num_cifras=0;
			}
			value =(7-(num_cifras))*dif;
				
			return value;
		
		
	}

	private void staticDataBill(PDF pdf, Image logo, Image borde1, Image borde3,
			int borde4Image, Image borde5, GlobalStatic data, double cm,Page page,Font fuente1, int option, Date since, Date until,Date daybill, int numClient,int payment) throws Exception {
		// TODO Auto-generated method stub
		TextLine line = new TextLine(fuente1);
    	Font fuente2 = new Font(pdf,CoreFont.HELVETICA_BOLD);
    	fuente2.setSize(20);
    	
    	TextLine line2 = new TextLine(fuente2);
     	fuente1.setSize(15);
		
		logo.setPosition(0.8*cm, 0.9*cm);
    	logo.drawOn(page);    	
    	   	
    	borde1.setPosition(9.7*cm, 0.9*cm);
    	borde1.drawOn(page);
    	
    	
    	borde3.setPosition(0.8*cm, 7.5*cm);
    	borde3.drawOn(page);
    	
    	Image borde4 = new Image(pdf,getResources().openRawResource(borde4Image),ImageType.PNG);
    	borde4.setPosition(0.8*cm, 23*cm);
    	borde4.drawOn(page);
    	
    	
    	borde5.setPosition(11.5*cm, 23*cm);
    	borde5.drawOn(page);
    	
    	Image borde2 = borde4;
    	borde2.scaleBy(0.4);
    	borde2.setPosition(9.85*cm, 5.7*cm);
    	borde2.drawOn(page);
    	
    	borde2.setPosition(14.3*cm,5.7*cm);
    	borde2.drawOn(page);
    	
    	
    	fuente1.setSize(15);
    	    	    
    	line2.setText("Datos Cliente");
    	line2.setPosition(11.05*cm, 1.2*cm);
    	line2.drawOn(page);
    	
    	line2.setText("Cárnicas SIDI C.B.");
    	line2.setPosition(1*cm, 4*cm);
    	line2.drawOn(page);
    	
    	fuente1.setSize(12);
    	
    	line.setText("N.I.F. E-52001989");
    	line.setPosition(1*cm, 4.5*cm);
    	line.drawOn(page);    	
    	
    	line.setText("C/. Carlos Ramírez de Arellano,26");
    	line.setPosition(1*cm, 5.1*cm);
    	line.drawOn(page);
    	
    	
    	line.setText("TLF  630  40  60  69                        ESP");
    	line.setPosition(1*cm, 5.7*cm);
    	line.drawOn(page);
    	
    	line.setText("         952  68  54  08                  10-16008-ML");
    	line.setPosition(0.95*cm, 6.2*cm);
    	line.drawOn(page);
    	
    	line.setText("C.E.E.");
    	line.setPosition(7.05*cm, 6.7*cm);
    	line.drawOn(page);
    	
    	line.setText("52003-MELILLA");
    	line.setPosition(1*cm, 6.9*cm);
    	line.drawOn(page);
    	
    	line.setText("");
    	
    	fuente1.setSize(15);
    	double counter= 2.0*cm;
    	String id = "Nº "+ String.valueOf(data.cliente.getIdClient());
    	String nombre = data.cliente.getName();
    	String detalle = data.cliente.getDetails();
    	String calle = data.cliente.getAddress();
    	String CP = data.cliente.getZIPCode();
    	String ciudad = data.cliente.getCity();
    	String pais = data.cliente.getCountry();
    	String CIF = data.cliente.getCIF();
    	
    	
    	line.setText(id);
    	line.setPosition(16.0*cm, 1.6*cm);
    	line.drawOn(page);
    	
    	double dif=0.7*cm;
    	double posLetras1=10.22*cm;
    	if(!nombre.equals("")){
    		line.setText(nombre);
        	line.setPosition(posLetras1, counter);
        	line.drawOn(page);
        	counter+=dif;
    	}
    	if(!detalle.equals("")){
    		line.setText(detalle);
        	line.setPosition(posLetras1, counter);
        	line.drawOn(page);
        	counter+=dif;
    	}
    	if(!CIF.equals("")){
    		line.setText(CIF);
        	line.setPosition(posLetras1, counter);
        	line.drawOn(page);
        	counter+=dif;
    	} 
    	if(!calle.equals("")){
    		line.setText(calle);
        	line.setPosition(posLetras1, counter);
        	line.drawOn(page);
        	counter+=dif;
    	}
    	if(!CP.equals("")){
    		line.setText(CP);
        	line.setPosition(posLetras1, counter);
        	line.drawOn(page);
        	
    	}
    	if(!ciudad.equals("")){
    		line.setText(ciudad);
        	line.setPosition(posLetras1+2*cm, counter);
        	line.drawOn(page);
    	}
    	if(!pais.equals("")){
    		line.setText(pais);
        	line.setPosition(posLetras1+6.4*cm, counter);
        	line.drawOn(page);
        	counter+=dif;
    	}
   		
    	line.setText("Fecha");
		
    	line.setPosition(11.0*cm,5.9*cm);
    	line.drawOn(page);

    	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    	if(option>0){
    		if (daybill == null){
    			line.setText(df.format(new Date()));
    		}
    		else{
    			line.setText(df.format(daybill));
    		}
    	}
    
    	else{
			line.setText(df.format(data.factura.getDate()));//Obtenemos la fecha del cliente
    	}
    	line.setPosition(10.05*cm, 6.5*cm);
		line.drawOn(page);
		if(option==0){
			line.setText("Albarán");
		}
		else{
			line.setText("Factura");
		}
    	line.setPosition(15.34*cm,5.9*cm);
    	line.drawOn(page);        	
    	
    	if(option==0){
    		line.setText("D-"+data.factura.getIdFactura());//Obtenemos la fecha del cliente
    	}
    	else{
    		if(payment==1){
    			line.setText("A-"+String.valueOf(numClient)+"NP");
    		}
    		else if(payment==2){
    			line.setText("A-"+String.valueOf(numClient)+"P");
    		}
    		else{
    			line.setText("A-"+String.valueOf(numClient));
    		}
    		
    	}
    	
		line.setPosition(14.6*cm, 6.5*cm);
		line.drawOn(page);
					
    	line2.setText("Conceptos y facturación");
    	line2.setPosition(4.1*cm,7.7*cm);
    	line2.drawOn(page);
    	
    	line2.setText("Observaciones");
    	line2.setPosition(3.1*cm, 23.2*cm);
    	line2.drawOn(page);
    	
    	line2.setText("Total");
    	line2.setPosition(14.2*cm, 23.3*cm);
    	line2.drawOn(page);
    	
    	line.setText("SUBTOTAL");
    	line.setPosition(11.8*cm, 24*cm);
    	line.drawOn(page);
    	line.setText("Descuento");
    	line.setPosition(11.8*cm, 24.9*cm);
    	line.drawOn(page);

    	
    	Line stripe = new Line(0.80*cm,8.3*cm,18.4*cm,8.3*cm);
    	stripe.setWidth(23);
        stripe.setColor(RGB.BLACK);
        stripe.drawOn(page);
        fuente2.setSize(15);
        line2.setColor(RGB.WHITE);
        if(option==0 || option ==2){
        	line2.setText(" CANTIDAD  	CONCEPTO                               PRECIO   DESC         TOTAL");
        }
        else{
        	line2.setText("ALB.    CANTIDAD  CONCEPTO                                    PRECIO        TOTAL");
        }
        line2.setPosition(1.3*cm,8.5*cm);
        line2.drawOn(page);
        
        line2.setColor(RGB.BLACK);
    	line2.setText("IMPORTE TOTAL");
    	line2.setPosition(11.8*cm, 25.8*cm);
    	line2.drawOn(page);
        
    }

	private int getNextFactura() {
		// TODO Auto-generated method stub
		
		int nextFactura= cliente.getNextFactura();
		cliente.setNextFactura(nextFactura);
		db.updateBill(cliente.getIdClient(),nextFactura+1);
		return nextFactura-1;
	}

	public int newClient(String name, String detail,String address,String city,	String country, String CIFid, String ZIPCode, int ultima) {
		// TODO Auto-generated method stub
		
		int result=1;
		Iterator<Clientes> itClientes = this.clientes.iterator();
		
		while(itClientes.hasNext()&&result==1){
			Clientes cliente = itClientes.next();
			if(cliente.getName().equals(name)){
				result=-1;
			}
		}
		if(result==1)
			this.clientes.add(db.newClient(name,detail,address,city,country,CIFid,ZIPCode,ultima));
		
		return result;
	}

	public int newProduct(String name, String details, double prize,boolean units) {
		// TODO Auto-generated method stub
		Iterator<Productos> itProductos = this.productos.iterator();
		int result =1;
		while(itProductos.hasNext()&&result==1){
			Productos producto = (Productos) itProductos.next();
			if(name.equals(producto.getName())){
				result=-1;
			}
		}
		if(result!=-1)
			this.productos.add(db.newProduct(name,details,prize,units));
		
		return result;
	}

	public double roundTwoDigits(double number) {
		// TODO Auto-generated method stub
		return Math.rint(number*100)/100;

	}

	public double roundThreeDigits(double number) {
		// TODO Auto-generated method stub
		return Math.rint(number*1000)/1000;
	}

	public void recalculateTotal(TableLayout table, TextView total) {
		// TODO Auto-generated method stub
		
		double totalBill = 0;
		for(int i=0;i<table.getChildCount()-1;i++){
			TableRow fila = (TableRow) table.getChildAt(i);
			EditText totalEdit = (EditText) fila.getChildAt(5);
			totalBill+=Double.parseDouble(totalEdit.getText().toString());
		}
		
		total.setText(String.valueOf(this.roundTwoDigits(totalBill))+"€");
		
	}
	
	private String getTwoDecimals(double prize){
		

		String sprize = String.valueOf(prize);
		
		String integer = sprize.substring(0, sprize.lastIndexOf("."));
		
		String decimal = sprize.substring(sprize.lastIndexOf("."),sprize.length());
		
		
		while (decimal.length()!=3){
			decimal = decimal+"0";
		}
		
		return integer+decimal;
		
	}
	private String getThreeDecimals(double prize){
	
		String sprize = String.valueOf(prize);
		
		String integer = sprize.substring(0, sprize.lastIndexOf("."));
		
		String decimal = sprize.substring(sprize.lastIndexOf("."),sprize.length());
		
		
		while (decimal.length()!=4){
			decimal = decimal+"0";
		}
		
		return integer+decimal;
		
	}

	public void createBackup() throws FileNotFoundException, IOException, InterruptedException {

		
	    File Db = new File("/data/data/ali.software/databases/DBAli2");

	    Date d = new Date();	    
	    SimpleDateFormat formatea = new SimpleDateFormat("yyyyMMddHHmm");
	    File file = new File("/storage/sdcard0/backup/"+formatea.format(d)+".db");	    
	    if (Db.exists()) {
	       GlobalStatic.copyFile(new FileInputStream(Db),new FileOutputStream(file));
	    }

//	    File file = new File("/storage/sdcard0/backup/201502201345.db");
//	    if (file.exists() && Db.exists()){
//	       GlobalStatic.copyFile(new FileInputStream(file),new FileOutputStream(Db));
//	    }	       
	}
	
	
	public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            long size = fromChannel.size();
            fromChannel.transferTo(0, size, toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }
}