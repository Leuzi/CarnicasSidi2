package ali.clases;

public class LineaFactura {
	
	private int id;					//Id de la linea factura
	private String nombre;			//Nombre del producto
	private double cantidad;			//Cantidad de producto
	private double precio;			//Precio en aquel momento del producto
	private double descuento;			//Descuento realizado %
	
	//Constructor con parametros
	public LineaFactura(int id,String nombre,double cantidad,double precio,double descuento){
		this.id=id;
		this.nombre=nombre;
		this.setPrize(precio);
		this.setDiscount(descuento);
		this.setQuantity(cantidad);
	}
	
	public int getId(){
		return this.id;
		
	}
	
	//Devuelve el nombre del producto
	public String getName() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	
	//Cambiamos el nombre del producto
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.nombre=name;
	}

	public double getQuantity() {
		return cantidad;
	}

	public void setQuantity(double cantidad) {
		if(cantidad>=0)
			this.cantidad = cantidad;
	}

	public double getPrize() {
		return precio;
	}

	public void setPrize(double precio) {
		if(precio>=0)
			this.precio = precio;
	}

	public double getDiscount() {
		return descuento;
	}

	public void setDiscount(double descuento) {
		if(descuento>=0 && descuento <=100)
			this.descuento = descuento;
	}

}
