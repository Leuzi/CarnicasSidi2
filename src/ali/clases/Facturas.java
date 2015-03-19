package ali.clases;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class Facturas implements Cloneable{

	private int idFactura;				//Id de la factura
	private Date fecha;						//Fecha en la fue creada
	private String observaciones;		//Observaciones que se hacen constar
	private boolean pagado;					//Estado de la factura
	private ArrayList<LineaFactura> lista;		//Lista con los elementos que componen la factura
	
	//Constructor por defecto
	public Facturas(int idFactura,Date fecha,String observaciones,boolean pagado){
		this.idFactura=idFactura;
		this.fecha=fecha;
		this.observaciones=observaciones;
		this.pagado=pagado;
		this.lista = new ArrayList<LineaFactura>();	//Inicialmente vacia
		
		
	}
	
	//Añadir una linea a la factura
	void addLinea(LineaFactura nueva){
		this.lista.add(nueva);
	}
	
	//Quitar una linea a la factura	
	void removeLinea(LineaFactura antigua){
		this.lista.remove(antigua);
	}
	public Object clone() throws CloneNotSupportedException
	{
	    Object clone = null;
	    clone = super.clone();
	    return clone;
	}
	//Modificar una linea a la factura	
	void modifyLinea(LineaFactura nueva,LineaFactura antigua){
		this.lista.remove(antigua);
		this.lista.add(nueva);
	}
	
	//Devuelve las lineas de la factura
	public ArrayList<LineaFactura> getLines() {
		// TODO Auto-generated method stub
		return this.lista;
	}
	
	//Cambia la lista de las lineas
	public void setList(ArrayList<LineaFactura> loadLineas) {
		// TODO Auto-generated method stub
		this.lista=loadLineas;
	}
	
	//Devuelve el id de la factura
	public int getIdFactura() {
		return idFactura;
	}
	
	//Devuelve el día en el que fue creada la factura
	public Date getDate() {
		return fecha;
	}

	//Cambia el dia de la factura
	public void setDate(Date fecha) {
		this.fecha = fecha;
	}
	
	//Obtiene las observaciones de la factura
	public String getView() {
		return observaciones;
	}
	
	//Cambia las observaciones
	public void setView(String observaciones) {
		this.observaciones = observaciones;
	}
	
	//Devuelve el estado de la factura (Pagado/NoPagado)
	public boolean isPaid() {
		return pagado;
	}
	//Cambia el estado de la facturas
	public void setPaid(boolean pagado) {
		this.pagado = pagado;
	}
	
	//Cambia el id de la Factura(-1 es factura nueva)
	public void setIdBill(int i) {
		
		if(this.idFactura==-1){
			this.idFactura=i;
		}
	}
	
	public LineaFactura getLineaId(int id){
		
		Iterator<LineaFactura> it = this.lista.iterator();
		
		boolean encontrado=false;
		
		LineaFactura next= null;
		
		while(it.hasNext()&&encontrado==false){
			
			next = (LineaFactura) it.next();
			if(next.getId()==id){
				encontrado=true;
			}
		}
		
		return next;
	}

	public void groupLines() {
		// TODO Auto-generated method stub
		
		for(int i=0;i<lista.size();i++){
			LineaFactura mainLine = (LineaFactura) lista.get(i);
			for(int j=i+1;j<lista.size();j++){
				LineaFactura maybeCopy= (LineaFactura) lista.get(j);
				System.out.println("Compruebo "+i+" "+j);
				System.out.flush();
				if(mainLine.getName().equals(maybeCopy.getName())){
					mainLine.setQuantity(mainLine.getQuantity()+maybeCopy.getQuantity());
					lista.remove(j);
					j--;
					System.out.println("IGUALES"+i+" "+j);
				}
			}
		}
		
	}

	public double getTotal() {
		// TODO Auto-generated method stub
		
		Iterator<LineaFactura> it = this.lista.iterator();
		double total = 0;
		while(it.hasNext()){
			LineaFactura linea = (LineaFactura) it.next();
			total += (linea.getPrize()*linea.getQuantity() * (1-(linea.getDiscount()/100)));			
		}
		return total;
	}

}
