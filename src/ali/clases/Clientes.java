package ali.clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Clientes implements Comparable<Clientes> {
	
	private int idCliente;		//Id del cliente
	private String nombre;			//Nombre del cliente
	private String detalles;	//Detalles (subCompañia, por ejemplo)
	private String direccion;		//Dirección fiscal
	private String cif;			//Cif del cliente
	private int CP;					//Código postal de la ciudad
	private String ciudad;			//Ciudad de la dirección
	private String pais;		//País donde factura

	private int nextFactura; //Entero con el numero de la factura siguiente
	
	private Map<String, Object> precios; //Precios especiales para un cliente
	private ArrayList<Facturas> facturas;//Lista de las facturas que posee un cliente

	
	//Constructor con parámetros
	public Clientes(int idCliente,String nombre,String detalles,String direccion,String cif,String ciudad,String pais,int CP,int ultimaFactura){
		this.idCliente=idCliente;
		this.nombre=nombre;
		this.detalles=detalles;
		this.direccion=direccion;
		this.cif=cif;
		this.ciudad=ciudad;
		this.pais=pais;
		this.CP=CP;
		facturas = new ArrayList<Facturas>();
		precios = new HashMap<String, Object>();
		this.setNextFactura(ultimaFactura);
		
	}
	//Devuelve el id de un cliente
	public int getIdClient() {
		return idCliente;
	}
	//Retorna el nombre del cliente
	public String getName(){
		return this.nombre;
	}
	//Cambia el nombre del cliente
	public void setName(String name){
		this.nombre=name;
	}
	//Devuelve los detalles del cliente
	public String getDetails() {
		// TODO Auto-generated method stub
		return this.detalles;
	}
	//Cambia los detalles del cliente
	public void setDetails(String detalles){
		this.detalles=detalles;
	}
	//Devuelve la direccion del cliente
	public String getAddress(){
		
		return this.direccion;
	}
	//Cambia la direccion del cliente
	public void setAddress(String address){
		this.direccion=address;
	}
	//Devuelve un STRING con el código postal del cliente
	public String getZIPCode(){
		
		return String.valueOf(this.CP);
	}
	//Cambia el codigo postal
	public void setZipCode(String ZIPCode){
		int CP = Integer.parseInt(ZIPCode);
		this.CP = CP;
	}
	//Devuelve la ciudad
	public String getCity(){
		
		return this.ciudad;
	}
	//Cambia la ciudad del cliente
	public void setCity(String city){
		
		this.ciudad=city;
	}
	//Devuelve el CIF del cliente
	public String getCIF(){
		return this.cif;
	}
	//Cambiar el CIF del cliente
	public void setCIF(String CIF){
		this.cif=CIF;
	}
	//Devuelve el país del cliente
	public String getCountry(){
		return this.pais;
	}
	//Cambia el país
	public void setCountry(String pais){
		
		this.pais=pais;
	}
	//Añade una factura a la lista
	public void addFactura(Facturas nuevaFactura){
		
		//Añadimos una factura
		this.facturas.add(nuevaFactura);
	}
	//Elimina una factura
	public void removeFactura(Facturas factura){
		//Eliminamos una factura
		this.facturas.remove(factura);
	}
	//Modifica una factura
	public void modifyFactura(Facturas antigua,Facturas nueva){
		//Modificar Factura
		//Borrar Factura
		this.facturas.remove(antigua);
		//Crear factura
		this.facturas.remove(nueva);
	}
	//Devuelve la lista de facturas de un cliente
	public ArrayList<Facturas> getFactura(){
		//Devolver factura
		return this.facturas;
	}
	//Cambia la lista de facturas
	public void setFacturas(ArrayList<Facturas> facturas){
		this.facturas=facturas;
	}
	//Devuelve el maps con los precios del cliente
	public Map<String, Object> getPrecios() {
		return precios;
	}
	//Cambia los precios del cliente por los nuevos
	public void setPrecios(Map<String, Object> precios) {
		this.precios = precios;
	}
	//Nos da el numero de la siguiente factura
	public int getNextFactura() {
		return nextFactura;
	}
	//Cambiamos el numero de la factura
	public void setNextFactura(int ultimaFactura) {
		this.nextFactura = ultimaFactura+1;
	}

	@Override
	public int compareTo(Clientes another) {
		// TODO Auto-generated method stub
		return this.nombre.compareTo(another.nombre);
	}
	
}
