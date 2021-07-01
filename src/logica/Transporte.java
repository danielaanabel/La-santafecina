package logica;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Transporte {
	
	private String numeroID;
	private TipoTransporte tipo;
	private double cargaMaxima;
	private double capacidad;
	private final double capacidadInicial;
	private final double cargaMaximaInicial;
	private boolean frigorifico;
	private double costoPorKm;
	private String destino;
	private boolean enViaje;
	private ArrayList<Paquetes> caja;
	private double costoCargaTerc;

	public Transporte(String numeroID, double cargaMaxima,
			double capacidad, boolean frigorifico, double costoPorKm,
			String destino, boolean enViaje, TipoTransporte tipo) {
		setNumeroID(numeroID);
		setCargaMaxima(cargaMaxima);
		setCapacidad(capacidad);
		setFrigorifico(frigorifico);
		setCostoPorKm(costoPorKm);
		setDestino(destino);
		setEnViaje(enViaje);
		setTipo(tipo);
		caja=new ArrayList<Paquetes>();
		setCostoCargaTerc(0);
		capacidadInicial=capacidad;
		cargaMaximaInicial=cargaMaxima;
	}

	double cargarTransporte(Deposito deposito) {
		double volumenCargado=0;
		double pesoCargado=0;
		if(destino==null || enViaje==true)
			throw new RuntimeException("el transporte esta en viaje o no tiene destino");
		else{
			if(deposito.isFrigorifico()==frigorifico){
				Iterator<Paquetes> p=deposito.getAlmacenamiento().iterator();
				while(p.hasNext()){
					Paquetes paquete=p.next();
					if(destino.equals(paquete.getDestino()) && capacidad>=paquete.getVolumen() 
							&& cargaMaxima>=paquete.getPeso()){
						caja.add(paquete);
						volumenCargado+=paquete.getVolumen();
						setCargaMaxima(cargaMaxima-paquete.getPeso());
						deposito.setCapacidad(deposito.getCapacidad()+paquete.getVolumen());//vacia el espacio en el deposito
						p.remove();
						if(deposito.getCostoPorTonelada()>0){
							pesoCargado+=paquete.getPeso();
						}
					}
				}
				setCostoCargaTerc(costoCargaTerc+(pesoCargado*deposito.getCostoPorTonelada()/1000));
			}
		}
		setCapacidad(capacidad-volumenCargado);
		return volumenCargado;
	}

	void iniciarViaje(String idTransp){
		if(numeroID.equals(idTransp)){
			if(!caja.isEmpty() && enViaje==false) {
				setEnViaje(true);
			}
			else {
				throw new RuntimeException("El trasporte ya esta en viaje o no esta cargado");
			}
		}
	}

	void finalizarViaje(String idTransp) {
		if(numeroID.compareTo(idTransp)==0){
			if(enViaje==false) 
				throw new RuntimeException("el camión no esta en viaje");
			else {
				for (Paquetes p:caja) {
					setCapacidad(capacidad+p.getVolumen());//vaciamos la capacidad del camion
					setCargaMaxima(cargaMaxima+p.getPeso());//vaciamos el peso del camion
				}
				caja.clear();
				setDestino(null);
				setEnViaje(false);
			}
		}
	}

	abstract double obtenerCostoViaje(String idTransp,int km);

	void asignarDestino(String destino,int km){
		if(km<500 && getTipo().equals(TipoTransporte.TrailerComun))
			setDestino(destino);
		else if (km>500 && getTipo().equals(TipoTransporte.MegaTrailer))
			setDestino(destino);
		else if(getTipo().equals(TipoTransporte.Fletes))
			setDestino(destino);
	}
	
	void moverMercaderia(Transporte t) {
		t.getCaja().addAll(caja);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return false;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transporte other = (Transporte) obj;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (caja == null) {
			if (other.caja != null)
				return false;
		}for(Paquetes p1:getCaja()){
			if(!other.getCaja().contains(p1))
				return false;
		}
		return true;
	}

	boolean otroVacioIgual(Transporte otro){
		if (this.numeroID == otro.numeroID)
			return false;
		if (getClass() != otro.getClass())
			return false;//si es distinto tipo
		if(!destino.equals(otro.destino))
			return false;//si es distinto destino
		if(frigorifico!=otro.isFrigorifico())
			return false;//si es distinto tipo frio
		if(!otro.caja.isEmpty())
			return false;// si no esta vacio
		if(capacidadInicial>otro.getCapacidad())
			return false;//si la capacidad del averiado es mayor
		if(cargaMaximaInicial>otro.getCargaMaxima())
			return false;//si la cargaMaxima del averiado es mayor
		return true;
	}

	@Override
	public String toString(){
		StringBuilder s=new StringBuilder();
		s.append("Trasportes {NúmeroID=").append(numeroID);
		s.append(", CargaMaxima=").append(cargaMaxima);
		s.append(", Capacidad=").append(capacidad);
		s.append(", Frigorifico=").append(frigorifico);
		s.append(", costoKm=").append(costoPorKm);
		s.append(", Destino=").append(destino);
		s.append(", enViaje=").append(enViaje);
		s.append(",\n   Caja {");
		for(Paquetes p:caja) {
			s.append(p);
		}
		s.append("}}\n");
		return s.toString();
	}

	public String getNumeroID() {
		return numeroID;
	}

	public void setNumeroID(String numeroID) {
		if(numeroID.length()!=7)
			throw new RuntimeException("id debe contenter 7 caracteres");
		this.numeroID = numeroID;
	}

	public double getCargaMaxima() {
		return cargaMaxima;
	}

	public void setCargaMaxima(double cargaMaxima) {
		if(cargaMaxima<1 || cargaMaxima>25000)
			throw new RuntimeException("carga incorrecta");
		this.cargaMaxima = cargaMaxima;
	}

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(double capacidad) {
		if(capacidad<1 || capacidad >1000)
			throw new RuntimeException("capacidad incorrecta");
		this.capacidad = capacidad;
	}

	public boolean isFrigorifico() {
		return frigorifico;
	}

	public void setFrigorifico(boolean frigorifico) {
		this.frigorifico = frigorifico;
	}

	public double getCostoPorKm() {
		return costoPorKm;
	}

	public void setCostoPorKm(double costoPorKm) {
		if(costoPorKm<1 || costoPorKm>100)
			throw new RuntimeException("costo incorrecto");
		this.costoPorKm = costoPorKm;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public boolean isEnViaje() {
		return enViaje;
	}

	public void setEnViaje(boolean enViaje) {
		this.enViaje = enViaje;
	}

	public ArrayList<Paquetes> getCaja() {
		return caja;
	}

	public void setCaja(ArrayList<Paquetes> caja){
		this.caja = caja;
	}

	public double getCostoCargaTerc() {
		return costoCargaTerc;
	}

	public void setCostoCargaTerc(double costoCargaTerc) {
		if(costoCargaTerc<0)
			throw new RuntimeException("costo incorrecto debe ser positivo");
		this.costoCargaTerc = costoCargaTerc;
	}

	public double getCapacidadInicial() {
		return capacidadInicial;
	}

	public double getCargaMaximaInicial() {
		return cargaMaximaInicial;
	}
	public TipoTransporte getTipo() {
		return tipo;
	}
	public void setTipo(TipoTransporte tipo) {
		this.tipo = tipo;
	}

}
