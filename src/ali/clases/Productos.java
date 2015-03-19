package ali.clases;

import android.widget.ArrayAdapter;

public class Productos implements Comparable<Productos>{

	private int idProducto;		//Id del producto
	private String nombre;			//Nombre del producto
	private String detalle;		//Detalle
	private double precio;			//Precio por unidad/kg 
	private boolean discreto;	//Dependendiendo de esto
	
	//Constructor con parámetros
	public Productos(int idProducto,String nombre,String detalle,double precio,boolean discreto){
		this.idProducto=idProducto;
		this.nombre=nombre;
		this.detalle=detalle;
		this.precio=precio;
		this.discreto=discreto;
	}
	
	//Devuelve el id del producto
	public int getIdProducto(){
		return this.idProducto;
	}
	
	//Devuelve el nombre del producto
	public String getName() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	
	//Devuelve los detalles de un producto
	public String getDetails() {
		// TODO Auto-generated method stub
		return this.detalle;
	}
	
	//Devuelve el precio del producto
	public double getPrize(){
		return this.precio;
	}
	
	//Devuelve las unidades del producto
	public boolean getUnits(){
		return this.discreto;
	}
	
	//Cambia el nombre del producto
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.nombre=name;
	}
	
	//Cambia el precio del producto
	public void setPrize(double prize) {
		// TODO Auto-generated method stub
		this.precio=prize;
	}

	//Cambia los detalles
	public void setDetails(String details) {
		// TODO Auto-generated method stub
		this.detalle=details;
	}
	//Cambia las unidades
	public void setUnits(boolean units){
		this.discreto=units;
	}

	@Override
	public int compareTo(Productos another) {
		// TODO Auto-generated method stub
		return this.nombre.compareTo(another.nombre);
	}
}
