package logica;

import java.util.ArrayList;


public class Deposito {
	
	private double capacidad;
	private boolean frigorifico;
	private boolean propio;
	private double costoPorTonelada;
	private ArrayList<Paquetes> almacenamiento;
	private int numDeposito;

	public Deposito(double capacidad, boolean frigorifico, boolean propio){
		setCapacidad(capacidad);
		setFrigorifico(frigorifico);
		setPropio(propio);
		almacenamiento=new ArrayList<Paquetes>();
	}

	//deposito tercerizado frio
	public Deposito(double capacidad, double costoPorTonelada) {
		setCapacidad(capacidad);
		setPropio(false);
		setFrigorifico(true);
		setCostoPorTonelada(costoPorTonelada);
		almacenamiento=new ArrayList<Paquetes>();
	}

	boolean incorporarPaquete(String destino, double peso, double volumen,
			boolean frio) {
		Paquetes paquete=new Paquetes(peso, volumen, destino, frio);
		if(capacidad>=volumen && frigorifico==frio) {
			almacenamiento.add(paquete);
			capacidad=capacidad-volumen;
			return true;
		}
		return false;
	}
	
	int cantPaqAlmacenados() {
		return almacenamiento.size();
	}
	
	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append("Deposito nro: ").append(numDeposito);
		s.append(" {Capacidad=").append(capacidad);
		s.append(", Frigorifico=").append(frigorifico);
		s.append(", Propio=").append(propio);
		s.append(", CostoTonelada=").append(costoPorTonelada);
		s.append(",\n   Almacenado {");
		for(Paquetes p:almacenamiento) {
			s.append(p);
		}
		s.append("}}\n");
		return s.toString();
	}

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(double capacidad) {
		if(capacidad<1 || capacidad>500000)
			throw new RuntimeException("capacidad incorrecta");
		this.capacidad = capacidad;
	}

	public boolean isFrigorifico() {
		return frigorifico;
	}

	public void setFrigorifico(boolean frigorifico) {
		this.frigorifico = frigorifico;
	}

	public boolean isPropio() {
		return propio;
	}

	public void setPropio(boolean propio) {
		this.propio = propio;
	}

	public double getCostoPorTonelada() {
		return costoPorTonelada;
	}

	public void setCostoPorTonelada(double costoPorTonelada) {
		if(costoPorTonelada<1 || costoPorTonelada>1000)
			throw new RuntimeException("costo incorrecto");
		this.costoPorTonelada = costoPorTonelada;
	}

	public ArrayList<Paquetes> getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(ArrayList<Paquetes> almacenamiento){
		this.almacenamiento = almacenamiento;
	}
	
	public int getNumDeposito() {
		return numDeposito;
	}

	public void setNumDeposito(int numDeposito) {
		if(numDeposito<1)
			throw new RuntimeException("Debe ser un entero positivo");
		this.numDeposito = numDeposito;
	}
}
