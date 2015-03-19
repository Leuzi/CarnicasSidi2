package ali.clases;

public class Precios {

	private String nombre;
	private double precio;
	
	public Precios(String nombre,double precio){
		
		setNombre(nombre);
		setPrecio(precio);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
