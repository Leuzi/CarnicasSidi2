package ali.clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import ali.clases.GlobalStatic;

public class DBHandler extends SQLiteOpenHelper {

	 //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/ali.software/databases/";
 
    private static String DB_NAME = "DBAli4";
 
    private SQLiteDatabase myDataBase; 
    private Context contexto;

	public DBHandler(Context context) {
		super(context, "DBAli4", null, 9);			//Abre/crea la base de dato
		// TODO Auto-generated constructor stub
		contexto = context;
	}
	
		
	static final String ForeignKeysON="PRAGMA foreign_keys=ON;";  //Activar claves externas
	
    static final String TableClients=		//Tabla de clientes
    	  "CREATE TABLE clientes ("+
			  "idClientes INTEGER PRIMARY KEY,"+		//AutoIncremento
			  "Nombre TEXT DEFAULT NULL,"+
			  "Detalle TEXT DEFAULT NULL,"+
			  "Direccion TEXT DEFAULT NULL,"+
			  "CIF TEXT DEFAULT NULL,"+
			  "Ciudad TEXT DEFAULT Melilla,"+		//Por defecto Melilla
			  "Pais TEXT DEFAULT España,"+				//Por defecto España
			  "CP INTEGER DEFAULT NULL,"+
			  "factura INTEGER DEFAULT 0,"+
			  "UNIQUE(Nombre)"+						//Nombre del cliente es único
		  ");";
	
    static final String TableProducts=			//Tabla de productos
    	   "CREATE TABLE productos ("+
    			 "idProductos INTEGER PRIMARY KEY,"+ //AutoIncremento
    			  "Nombre TEXT DEFAULT NULL,"+
    			  "Detalle TEXT DEFAULT NULL,"+
    			  "Precio REAL DEFAULT NULL,"+
    			  "Unidades INTEGER DEFAULT NULL,"+
    			  "UNIQUE(Nombre)"+				//Nombre del producto es único
    		");";
    
    static final String TableBills=			//Tabla de facturas
    		"CREATE TABLE facturas ("+
    		"idFacturas INTEGER PRIMARY KEY,"+		//Auto Incremento
			"Fecha TEXT NOT NULL,"+
			"Observaciones TEXT DEFAULT NULL,"+
			"Estado INTEGER DEFAULT NULL"+					//Pagado 1/ No Pagado 0
			");";
    
    static final String TableOwn=				//Tabla posee
    		"CREATE TABLE posee("+
    		"idCliente INTEGER NOT NULL,"+		//Asocia clientes con
    		"idFactura INTEGER NOT NULL,"+			//Facturas
    		"PRIMARY KEY (idCliente,idFactura),"+
    		"FOREIGN KEY (idCliente) REFERENCES clientes(idCliente),"+	//Restricciones de las columnas
    		"FOREIGN KEY (idFactura) REFERENCES factura(idFactura)"+
    		");";
    		
    static final String IsMadeWith=
    		"CREATE TABLE contiene ("+			//Tabla contiene
    		"idFacturas INTEGER NOT NULL,"+			//Asocia facturas
    		"idProductos INTEGER NOT NULL,"+	//con Productos
    		"Cantidad REAL NOT NULL,"+	
    		"Precio REAL NOT NULL,"+
    		"Descuento REAL NOT NULL,"+
    		"PRIMARY KEY (idFacturas,idProductos),"+	//Evita productos duplicados en el mismo albaran
    		"FOREIGN KEY (idFacturas) REFERENCES facturas(idFacturas),"+
    		"FOREIGN KEY (idProductos) REFERENCES productos (idProductos)"+
    		");";
    
    static final String OwnPrize=
    		"CREATE TABLE propio ("+		//Tabla contiene
    		"idClientes INTEGER,"+				//Clientes asociados con
    		"idProductos INTEGER,"+			//Productos
    		"precio REAL,"+						//Precio individual
    		"PRIMARY KEY (idClientes,idProductos),"+
    		"FOREIGN KEY(idClientes) REFERENCES clientes(idClientes),"+
    		"FOREIGN KEY(idProductos) REFERENCES productos(idProductos));";
    

	 
	 
	  /**
	     * Creates a empty database on the system and rewrites it with your own database.
	     * 
	    */
	    public void createDataBase() throws IOException{
	 
//	    	boolean dbExist = checkDataBase();
//	 
//	    	if(dbExist){
//	    		//do nothing - database already exist
//	    	}else{
	 
	    		//By calling this method and empty database will be created into the default system path
	               //of your application so we are gonna be able to overwrite that database with our database.
	        	this.getReadableDatabase();
	 
	        	try {
	 
	    			copyDataBase();
	 
	    		} catch (IOException e) {
	 
	        		throw new Error("Error copying database");
	 
	        	}
//	    	}
	 
	    }
	 
	    /**
	     * Copies your database from your local assets-folder to the just created empty database in the
	     * system folder, from where it can be accessed and handled.
	     * This is done by transfering bytestream.
	     * */
	    private void copyDataBase() throws IOException{
	 
	    	//Open your local db as the input stream
	    	InputStream myInput = contexto.getAssets().open(DB_NAME);
	 
	    	// Path to the just created empty db
	    	String outFileName = DB_PATH + DB_NAME;
	 
	    	//Open the empty db as the output stream
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	 
 
	    @Override
		public synchronized void close() {
	 
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
	    }
	    
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		  db.execSQL("DROP TABLE IF EXISTS clientes ");
		  db.execSQL("DROP TABLE IF EXISTS productos");		  
		  db.execSQL("DROP TABLE IF EXISTS facturas");
		  db.execSQL("DROP TABLE IF EXISTS posee");
		  db.execSQL("DROP TABLE IF EXISTS contiene");
		  db.execSQL("DROP TABLE IF EXISTS propio");

		  onCreate(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		//Ejecuta las setencias		 
		 db.execSQL(ForeignKeysON);
		 db.execSQL(TableClients);
		 db.execSQL(TableProducts);
		 db.execSQL(TableBills);
		 db.execSQL(TableOwn);
		 db.execSQL(IsMadeWith);
		 db.execSQL(OwnPrize);
	}

	public ArrayList<Productos> getProducts() {				//Devuelve todos los productos disponibles
		SQLiteDatabase db = this.getWritableDatabase();		
		String sql = "SELECT * FROM productos";		
		String str[] =null;
		Cursor cursor = db.rawQuery(sql,str);		
		ArrayList<Productos> list = new ArrayList<Productos>();
		
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Para cada registro
			do{
				//Obtenemos los datos del cursor
				int idProductos = cursor.getInt(0);
	   			String nombre = cursor.getString(1);
	   			String detalle = cursor.getString(2);
	       		double precio = cursor.getDouble(3);
	       		boolean unidades = false;
	       		if(cursor.getInt(4)==1){
	       			unidades = true;
	       		}
	       		//Creamos el producto
	       		Productos producto = new Productos(idProductos,nombre,detalle,precio,unidades);
	       		//Lo añadimos a la lista global con los productos
	       		list.add(producto);
	       		
			}while(cursor.moveToNext());//Mientras que queden registros
		}		
		
		db.close();
		return list;
	}
	public ArrayList<Pair<String,String>> getLastProducts() {				//Devuelve los últimos productos vendidos
		// TODO Auto-generated method stub
		
		ArrayList<Pair<String,String>> names = new ArrayList<Pair<String,String>>();
		SQLiteDatabase db = this.getReadableDatabase();		
		String sql = "SELECT P.Nombre,F.Fecha FROM Productos P "+
					 " LEFT JOIN Contiene C ON C.idProductos = P.idProductos "+
					 " LEFT JOIN Facturas F ON C.idFacturas = F.idFacturas "+
					 " GROUP BY C.idProductos"+
					 " ORDER BY C.idFacturas DESC";		
		String str[] =null;
		Cursor cursor = db.rawQuery(sql,str);
	
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Para cada registro
			do{
				//Obtenemos los datos del cursor
	   			String nombre = cursor.getString(0);
	   			String fecha = cursor.getString(1);
	   			if(fecha != null){
	   				fecha = "Ultima venta el día "+ cursor.getString(1);
	   			}
	   			else{
	   				fecha = "Nunca ha sido vendido";
	   			}
	       		//Lo añadimos a la lista global con los productos
	   			names.add(new Pair<String, String>(nombre, fecha));
       		  
			}while(cursor.moveToNext());//Mientras que queden registros
		}		
		db.close();
		return names;
	}
	public ArrayList<Pair<String,String>> getBestProducts(java.util.Date inicio,java.util.Date fin) {//Devuelve los productos mas vendidos
		SimpleDateFormat iso8601Format = new SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss");
		ArrayList<Pair<String,String>> names = new ArrayList<Pair<String,String>>();
		SQLiteDatabase db = this.getReadableDatabase();		
		String sql = "SELECT P.Nombre,SUM((C.Cantidad*C.Precio)*((100-C.Descuento)/100)) as Suma " +
				" FROM Productos P LEFT JOIN Contiene C ON C.idProductos = P.idProductos ";
		
		if(inicio != null && fin != null){
			sql = sql +" LEFT JOIN Facturas F ON F.idFacturas = C.idFacturas"
					+ " WHERE F.Fecha BETWEEN '"+iso8601Format.format(new java.sql.Date(inicio.getTime())) +"' AND '" + iso8601Format.format(new java.sql.Date(fin.getTime()))+"'";
		}
		
		sql = sql +	" GROUP BY C.idProductos ORDER BY suma DESC";		
		String str[] =null;
		Cursor cursor = db.rawQuery(sql,str);
	
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Para cada registro
			do{
				//Obtenemos los datos del cursor
	   			String nombre = cursor.getString(0);
	   			Double Scantidad = Math.rint(cursor.getDouble(1)*100)/100;
	   			String cantidad = String.valueOf(Scantidad);
	   			cantidad ="Vendidos un total de "+cantidad+ " €";
	       		//Lo añadimos a la lista global con los productos
	   			names.add(new Pair<String, String>(nombre, cantidad));
       		  
			}while(cursor.moveToNext());//Mientras que queden registros
		}		
		db.close();
		return names;
	}
	
	public ArrayList<Pair<String,String>> getLastClients() {	//Devuelve los últimos clientes asociados a productos
		
		ArrayList<Pair<String,String>> names = new ArrayList<Pair<String,String>>();
		SQLiteDatabase db = this.getReadableDatabase();		
		String sql = "SELECT C.Nombre,F.Fecha  FROM Clientes C " +
					"LEFT JOIN Posee P ON C.idClientes = P.idCliente " +
					"LEFT JOIN Facturas F ON F.idFacturas=P.idFactura"+
					" GROUP BY C.idClientes"+
					" ORDER BY P.idFactura DESC";		
		String str[] =null;
		Cursor cursor = db.rawQuery(sql,str);
	
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Para cada registro
			do{
				//Obtenemos los datos del cursor
	   			String nombre = cursor.getString(0);
	   			String fecha = cursor.getString(1);
	       		//Lo añadimos a la lista global con los productos
	   			if(fecha != null){
	   				names.add(new Pair<String, String>(nombre,"Última venta el día "+fecha));
	   			}
	   			else{
	   				names.add(new Pair<String, String>(nombre,"Nunca se ha realizado una venta"));
	   			}
	   			
       		  
			}while(cursor.moveToNext());//Mientras que queden registros
		}		
		db.close();
		return names;
	}
	
	public ArrayList<Pair<String,String>> getBestClients(java.util.Date inicio, java.util.Date fin) {	//Devuelve los mejores clientes asociados a productos
		// TODO Auto-generated method stub
		
		ArrayList<Pair<String,String>> names = new ArrayList<Pair<String,String>>();
		SQLiteDatabase db = this.getReadableDatabase();
		SimpleDateFormat iso8601Format = new SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss");		
		String sql = "SELECT C.Nombre,SUM((T.Cantidad*T.Precio)*((100-T.Descuento)/100)) as Suma " +
				" FROM Clientes C LEFT JOIN Posee P ON c.idClientes = P.idCLiente" +
				" LEFT JOIN Contiene T ON T.idFacturas=P.idFactura";
		if(inicio != null && fin != null){
			sql = sql +" LEFT JOIN Facturas F ON F.idFacturas = T.idFacturas"
					+ " WHERE F.Fecha BETWEEN '"+iso8601Format.format(new java.sql.Date(inicio.getTime())) +"' AND '" + iso8601Format.format(new java.sql.Date(fin.getTime()))+"'";
		}
		sql = sql +	" GROUP BY C.idClientes ORDER BY Suma DESC";		
		String str[] =null;
		Cursor cursor = db.rawQuery(sql,str);
	
		//Nos aseguramos de que existe al menos un registro
		if(cursor.moveToFirst()){
			//Para cada registro
			do{
				//Obtenemos los datos del cursor
	   			String nombre = cursor.getString(0);
	   			Double Scantidad = Math.rint(cursor.getDouble(1)*100)/100;
	   			
	   			String cantidad ="Vendidos un total de "+ String.valueOf(Scantidad)+" €";
	       		//Lo añadimos a la lista global con los productos
	   			names.add(new Pair<String, String>(nombre, cantidad));
       		  
			}while(cursor.moveToNext());//Mientras que queden registros
		}		
		db.close();
		return names;
	}	

	public ArrayList<Clientes> getClients() {				//Devuelve todos los clientes
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM clientes";
		ArrayList<Clientes> list = new ArrayList<Clientes>();
		
		Cursor cursor = db.rawQuery(sql,null);
		
		//Si hay algún resultado
		if(cursor.moveToFirst()){
			do{
				//Recolectamos información
				int idCliente = cursor.getInt(0);
				String nombre = cursor.getString(1);	
				String detalles = cursor.getString(2);
				String direccion = cursor.getString(3);	
				String cif = cursor.getString(4);
				String ciudad = cursor.getString(5);	
				String pais = cursor.getString(6);
				int CP = cursor.getInt(7);
				int ultima = cursor.getInt(8);
				
				//Creamos el cliente
				Clientes cliente = new Clientes(idCliente,nombre,detalles,direccion,cif,ciudad,pais,CP, ultima);
				
				//Cargamos los precios
				cliente.setPrecios(getPrizes(idCliente));
				
				//Cargamos las facturas
				//correspondientes a ese cliente
				ArrayList<Facturas> facturas= getFacturas(idCliente);
				cliente.setFacturas(facturas);
				
				//Añadimos un cliente
				list.add(cliente);
				
			}while(cursor.moveToNext());//Mientras que haya clientes
		}
		
		db.close();
		return list;
	}

	private Map<String, Object> getPrizes(int idCliente) {
		// TODO Auto-generated method stub
		
		//Obtenemos la base de datos
		SQLiteDatabase db = this.getReadableDatabase();
		//El idCliente es nuestro dato
		String[] args={String.valueOf(idCliente)};
		//Sentencia a ejecutar
		String sql = "SELECT A.idProductos,A.precio,B.nombre FROM propio A"+
		" LEFT JOIN productos B ON A.idProductos=B.idProductos WHERE A.idClientes=?";
//		String sql = "SELECT idProductos,precio FROM propio WHERE idClientes=?";
		Cursor cursor = db.rawQuery(sql, args);
		
		Map<String, Object> precios = new HashMap<String, Object>();
		
		if(cursor.moveToFirst()){
			double precio = cursor.getDouble(1);			
			String nombre = cursor.getString(2);			
			precios.put(nombre, precio);			
		}
		cursor.close();
		return precios;
	}

	//Devuelve las facturas asociadas a un cliente
	public ArrayList<Facturas> getFacturas(int idCliente) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = this.getReadableDatabase();
		String[] args={String.valueOf(idCliente)};
		String sql = "SELECT idFactura,Fecha,Observaciones,Estado FROM facturas AS F "+ //Selecciona los datos necesarios
					"INNER JOIN("+				//Uniendo
							"SELECT idFactura FROM posee WHERE "+	//La lista con idFacturas
								"idCliente=?)AS  P "+		//que posee un cliente
							"ON F.idFacturas=P.idFactura";					//Usando el idFactura
						
		Cursor cursor = db.rawQuery(sql, args);
		ArrayList<Facturas> list = new ArrayList<Facturas>();
		//Si hay algún registro
		if(cursor.moveToFirst()){
		
			do{
				//Cargamos la información
				int idFactura = cursor.getInt(0);
				String rawFecha = cursor.getString(1);
				Date fecha = Date.valueOf(rawFecha);
				String observaciones = cursor.getString(2);
				
				//Pagado 1 /No Pagado 0
				boolean pagado=false;
				if(cursor.getInt(3)==1){
	       			pagado = true;
	       		}
				
				//Creamos la factura
				Facturas factura = new Facturas(idFactura,fecha,observaciones,pagado);
				//Cargamos las líneas correspondientes a esa factura
				factura.setList(getLines(idFactura));
				
				//Añadimos la factura
				list.add(factura);
						
			}while(cursor.moveToNext());//Mientras que haya facturas pendientes
		
		}
		
		db.close();
		return list;

	}
	
	//Devuelve todas las lineas de una factura dada
	//luego tendremos que cambiar idProducto por el nombre(simplificación)
	public ArrayList<LineaFactura> getLines(int idFactura) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql = "SELECT A.idProductos,A.cantidad,A.precio,A.descuento,B.nombre"+
					 " FROM contiene A LEFT JOIN productos B ON A.idProductos=B.idProductos WHERE A.idFacturas=?";
		String[] args={String.valueOf(idFactura)};
		Cursor cursor = db.rawQuery(sql, args);
		ArrayList<LineaFactura> list = new ArrayList<LineaFactura>();
		int i=0;
		//Si hay alguna linea
		if(cursor.moveToFirst()){
			do{
				//Obtenemos la información
				String nombreProducto = cursor.getString(4);
				double cantidad = cursor.getDouble(1);
				double precio = cursor.getDouble(2);
				double descuento = cursor.getDouble(3);	
	
				//Creamos la linea
				LineaFactura linea = new LineaFactura(i,nombreProducto,cantidad,precio,descuento);
				i++;
				//Añadimos la linea
				list.add(linea);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}

	//Inserta un cliente en la base de datos
	//Devuelve el cliente insertado
	public Clientes newClient(String name, String detail, String address, String city,String country, String CIFid, String ZIPCode, int ultima) {

		//Obtenemos la base de datos con la que podamos escribir
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Creamos el contenedor de valores y los almacenamos ahí		
		ContentValues c = new ContentValues();
		c.put("nombre", name);
		c.put("detalle",detail);
		c.put("direccion",address);
		c.put("cif", CIFid);
		c.put("ciudad", city);
		c.put("pais",country);
		c.put("CP",ZIPCode);
		c.put("factura", ultima);
		
		//Insertamos al cliente en la base de datos
		db.insert("clientes", null, c);
		
		//Obtenemos su id(el máximo porque se ha incrementado)
		Cursor cursor = db.rawQuery("SELECT MAX(idClientes) FROM clientes",null);
		
		int idCliente = -1;
		
		//Si la consulta a sido satisfactoria
		if(cursor.moveToFirst()){
			idCliente=cursor.getInt(0);
		}
		//Creamos el cliente
		int zip=0;
		try{
			zip = Integer.parseInt(ZIPCode);
		}catch(Exception e){
			zip=0;
		}
		
		Clientes newClient = new Clientes(idCliente,name,detail,address,CIFid,city,country,zip,ultima);
		//Devolvemos el cliente
		return newClient;
	}
	
	//Modifica los datos del cliente que esta en la base de datos
	public void modifyClient(Clientes cliente) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		//Creamos un contenedor de valores para los datos que vamos a cambiar
		ContentValues values = new ContentValues();
		//Insertamos los datos
		values.put("Nombre",cliente.getName());
		values.put("Detalle", cliente.getDetails());
		values.put("Direccion", cliente.getAddress());
		values.put("CIF", cliente.getCIF());
		values.put("Ciudad", cliente.getCity());
		values.put("Pais",cliente.getCountry());
		values.put("CP",cliente.getZIPCode());
		values.put("factura", cliente.getNextFactura());
		
		//Datos de la clausula where
		String[] args = {String.valueOf(cliente.getIdClient())};
		//Ejecutamos el update		
		db.update("clientes", values, "idClientes=?", args);
		db.close();
	}

	//Elimina un cliente, sus facturas y las relaciones con las líneas(tocamos 4 tablas)
	public void removeClient(int idCliente) {
		// TODO Auto-generated method stub
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		//Elimina el cliente de la BD
		String args[]={String.valueOf(idCliente)};
		
		db.delete("propio", "idClientes=?", args);
		db.delete("clientes", "idClientes=?", args);
		db.close();
		
	}

	public void removeFacturasCliente(int idCliente) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Obtenemos las facturas de los clientes
		ArrayList<Facturas> cursor= getFacturas(idCliente);
		String args2[]={String.valueOf(idCliente)};
		
		Iterator<Facturas> it = cursor.iterator();
		//Para cada factura
		while(it.hasNext()){
			Facturas next = (Facturas) it.next();
			//Obtenemos el Id de la factura
			int idFactura = next.getIdFactura();
			//Borramos todos lo productos que contiene
			String args[]={String.valueOf(idFactura)};
			db.delete("contiene", "idProducto=?", args);
			
			//Borramos la conexión posee
			db.delete("posee","idFactura=?",args2);
			
			//Borramos el elemento de la tabla Facturas
			db.delete("facturas", "idFactura", args);
		}
		
		db.close();

	}
	
	public Productos newProduct(String name, String details, double prize,			boolean units) {
		// TODO Auto-generated method stub
		//Obtenemos la base de datos con la que podamos escribir
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Creamos el contenedor de valores y los almacenamos ahí
		
		ContentValues c = new ContentValues();
		c.put("nombre", name);
		c.put("detalle",details);
		c.put("precio",prize);
		if(units){
			c.put("unidades", 1);
		}
		else{
			c.put("unidades",0);
		}

		
		//Insertamos el producto en la base de datos
		db.insert("productos", null, c);
		
		//Obtenemos su id(el máximo porque se ha incrementado)
		Cursor cursor = db.rawQuery("SELECT MAX(idProductos) FROM productos",null);
		
		int idProducto = -1;
		
		//Si la consulta a sido satisfactoria
		if(cursor.moveToFirst()){
			idProducto=cursor.getInt(0);
		}
		//Creamos el cliente
		
		Productos newProducto = new Productos(idProducto,name,details,prize,units);
		//Devolvemos el cliente
		return newProducto;
	}
	
	//Eliminamos el producto seleccionado
	//Debemos eliminar todas las relaciones de ese producto en las facturas
	public void removeProducto(int idProducto) {
		// TODO Auto-generated method stub

		SQLiteDatabase db = this.getWritableDatabase();
		
		//Lo eliminamos de las facturas
		//No tiene sentido facturar algo que no hemos vendido
		String args[]={String.valueOf(idProducto)};
		db.delete("contiene", "idProductos=?", args);
		
		//Lo eliminamos de los productos
		db.delete("productos", "idProductos=?", args);
		
		db.close();
	}

	//Modificamos un producto de la base de datos
	//Fácil, no hay que cambiar las facturas
	public void modifyProducto(Productos product) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Creamos un contenedor de valores para los datos que vamos a cambiar
		ContentValues values = new ContentValues();
		//Insertamos los datos
		values.put("Nombre",product.getName());
		values.put("Detalle", product.getDetails());
		if(product.getUnits()==true){
			values.put("Unidades", 1);
			values.put("Precio", product.getPrize());
		}
		else{
			values.put("Unidades",0);
			values.put("Precio", (int) product.getPrize());
		}
		
		//Datos de la clausula where
		String[] args = {String.valueOf(product.getIdProducto())};
		//Ejecutamos el update		
		db.update("Productos", values, "idProductos=?", args);
		
		db.close();
	}

	//Elimina los precios especiales por cliente
	//Eliminamos todas las columnas
	public void removePrizes(int idClient) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		//Datos de la clausula where
		String[] args = {String.valueOf(idClient)};
		db.delete("propio", "idClientes=?", args);
		db.close();
		
	}

	public void modifyPrize(int idClient, HashMap<String, Object> create,
			ArrayList<String> delete, HashMap<String, Object> modify) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Borramos los que ya existen
		Iterator<String> itdelete = delete.iterator();
		
		while(itdelete.hasNext()){
			String id = itdelete.next();
			String args[]={String.valueOf(idClient),id};			
			db.delete("propio", "idCliente=? AND idProductos=?", args);
		}
		
		//Modificamos los modificados
		
		Iterator<Entry<String, Object>> itmodify=modify.entrySet().iterator();

		while(itmodify.hasNext()){
			
			Entry<String, Object> next = itmodify.next();
			
			String id = next.getKey();
			String args[]={String.valueOf(idClient),id};
			ContentValues values = new ContentValues();
			values.put("precio",(Double) next.getValue());
			
			db.update("propio", values, "idCliente=? AND idProductos=?", args);
		}
		
		//Creamos los que tengamos que crear
		
		Iterator<Entry<String, Object>> itcreate=create.entrySet().iterator();
		
		while(itcreate.hasNext()){
			Entry<String, Object> next = itcreate.next();
			String id = next.getKey();
			
			ContentValues content = new ContentValues();
			content.put("idClientes",idClient);
			content.put("idProductos",id);
			content.put("precio",(Double) next.getValue());
			db.insert("propio", null, content);
		}
		db.close();
	}

	public String getNextBill() {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String sql="SELECT MAX(idFacturas) FROM facturas";
		
		Cursor cur = db.rawQuery(sql, null);
		
		int number = 1;
		if(cur.moveToFirst()){
			number = cur.getInt(0);
		}
		
		
		
		return String.valueOf(number);
	}

	public void saveBill(GlobalStatic data) {
		// TODO Auto-generated method stub
		//Obtenemos la base de datos
		SQLiteDatabase db = this.getReadableDatabase();
				
		//Metemos los valores
		ContentValues values = new ContentValues();
		values.put("Fecha", data.factura.getDate().toString());
		values.put("Observaciones", data.factura.getView());
		values.put("Estado", "0");
		
		//Insertamos los valores en la factura
		db.insert("facturas", null, values);
		
		
		//Por último, añadimos la correspondencia entre cliente y factura
		values = new ContentValues();
		
		values.put("idCliente",data.cliente.getIdClient());
		int billnumber = Integer.parseInt(data.db.getNextBill());
		data.factura.setIdBill(billnumber);
		values.put("idFactura",data.factura.getIdFactura());
		db.insert("posee",null, values);
		
		data.factura.groupLines();

		Iterator<LineaFactura> it = data.factura.getLines().iterator();
		//Para cada linea de la factura
		
		while(it.hasNext()){
			LineaFactura linea = it.next();
			//Obtenemos los valores necesarios
			values = new ContentValues();
			values.put("idFacturas", data.factura.getIdFactura());
			values.put("idProductos", String.valueOf(data.getProduct(linea.getName()).getIdProducto()));
			values.put("Cantidad", linea.getQuantity());
			values.put("Precio", linea.getPrize());
			values.put("Descuento", linea.getDiscount());
			//Lo insertamos en la BD
			db.insert("contiene",null, values);
		}
		
	}
	
	

	public void removeBill(GlobalStatic data) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Primero borraremos las lineas de la factura
		Iterator<LineaFactura> it = data.factura.getLines().iterator();
		
		//Iteramos sobre la lista
		String clientId = String.valueOf(data.cliente.getIdClient());
		while(it.hasNext()){
			LineaFactura linea = (LineaFactura) it.next();
		
			String args[]={clientId,String.valueOf(linea.getId())};
			
			db.delete("contiene", "idFacturas = ? AND idProductos=?", args);
		}
		
		//Una vez borradas todas las lineas, vamos a borrar la conexion entre la factura
		//Y el cliente
		String args[]={clientId,String.valueOf(data.factura.getIdFactura())};
		
		db.delete("posee", "idCliente=? AND idFactura=?", args);
		
		
		//Para finalizar borramos la factura
		String args2[]={String.valueOf(data.factura.getIdFactura())};
		db.delete("facturas", "idFacturas=?", args2);
		
	}

	public void modifyBill(GlobalStatic data) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		
		String args[]={String.valueOf(data.factura.getIdFactura())};
		//Primero eliminamos todas las lineas anteriores
		db.delete("contiene", "idFacturas=?", args);
		
		data.factura.groupLines();
		
		//Después añadimos las lineas nuevas
		Iterator<LineaFactura> it = data.factura.getLines().iterator();
	
		//Para cada linea
		while(it.hasNext()){
			//Obtenemos la línea de la nueva facturas
			LineaFactura linea = (LineaFactura) it.next();
			ContentValues values = new ContentValues();
			
			//Insertamos los productos
			values.put("idFacturas", data.factura.getIdFactura());
			values.put("idProductos", data.getProduct(linea.getName()).getIdProducto());
			values.put("Cantidad", linea.getQuantity());
			values.put("Precio", linea.getPrize());
			values.put("Descuento", linea.getDiscount());		
						
			db.insert("contiene", null, values);
		}
		
		//Por último modificamos los datos de la factura
		
		ContentValues values = new ContentValues();
		
		values.put("Fecha", data.factura.getDate().toString());
		values.put("Observaciones", data.factura.getView());
		values.put("Estado", data.factura.getView());
		
		String args2[]={String.valueOf(data.factura.getIdFactura())};
		db.update("facturas", values, "idFacturas=?", args2);
		
	}

	public void updateBill(int idClient, int i) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("factura", i);
		String args[]={String.valueOf(idClient)};
		db.update("clientes", values, "idClientes=?", args);
	}
}
