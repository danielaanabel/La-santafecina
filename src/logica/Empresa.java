package logica;
import java.util.*;

public class Empresa {
	
	private String nombre;
	private String cuit;
	private LinkedList<Deposito> depositos;
	private HashMap<String, Transporte> flotaTransporte;
	private LinkedList<Viaje> destinos;
	private int numDeposito;

	public Empresa(String cuit, String nombre) {
		setNombre(nombre);
		setCuit(cuit);
		depositos=new LinkedList<Deposito>();
		flotaTransporte=new HashMap<String,Transporte>();
		destinos=new LinkedList<Viaje>();
		numDeposito=0;
	}

	public int agregarDeposito(double capacidad, boolean frigorifico, boolean propio) {
		if(propio==false && frigorifico==true){
			throw new RuntimeException("error utilizar agregarDepTercerizFrio");
		}
		Deposito deposito=new Deposito(capacidad, frigorifico, propio);
		depositos.add(deposito);
		deposito.setNumDeposito(++numDeposito);
		return numDeposito;
	}

	public int agregarDepTercerizFrio(double capacidad, double costoPorTonelada) {
		Deposito depFrio =new Deposito(capacidad, costoPorTonelada);
		depositos.add(depFrio);
		depFrio.setNumDeposito(++numDeposito);
		return numDeposito;
	}

	public void agregarDestino(String destino, int km){
		Viaje viaje=new Viaje(destino, km);
		destinos.add(viaje);	
	}

	public void agregarTrailer(String idTransp, double cargaMax, double capacidad,
			boolean frigorifico, double costoKm, double segCarga){
		if(buscarTransporte(idTransp)!=null)
			throw new RuntimeException("el númeroId no puede ser igual a otro transporte");
		TrailerComun trailer=new TrailerComun(idTransp, cargaMax, capacidad, frigorifico, costoKm, null, false, segCarga, TipoTransporte.TrailerComun);
		flotaTransporte.put(idTransp, trailer);
	}

	public void agregarMegaTrailer(String idTransp, double cargaMax, double capacidad,
			boolean frigorifico, double costoKm, double segCarga, double costoFijo, double
			comida) {
		if(buscarTransporte(idTransp)!=null)
			throw new RuntimeException("el númeroId no puede ser igual a otro transporte");
		MegaTrailer megaTrailer=new MegaTrailer(idTransp, cargaMax, capacidad, frigorifico, costoKm, null, false, segCarga, costoFijo, comida,TipoTransporte.MegaTrailer);
		flotaTransporte.put(idTransp, megaTrailer);
	}

	public void agregarFlete(String idTransp, double cargaMax, double capacidad, double
			costoKm, int acomp, double costoPorAcom) {
		if(buscarTransporte(idTransp)!=null)
			throw new RuntimeException("el númeroId no puede ser igual a otro transporte");
		Flete flete=new Flete(idTransp, cargaMax, capacidad, costoKm, null, false, acomp, costoPorAcom, TipoTransporte.Fletes); 
		flotaTransporte.put(idTransp, flete);
	}

	public void asignarDestino(String idTransp, String destino){
		int km=0;
		Transporte t=buscarTransporte(idTransp);
		if(t!=null){
			if(t.getDestino()==null){
				km=cantKmViaje(destino);
				if(km!=-1) //si existe el destino
					t.asignarDestino(destino, km);
			}
			else {
				throw new RuntimeException("El transporte ya tiene destino asignado");
			}
		}
	}

	public boolean incorporarPaquete(String destino, double peso, double volumen,boolean frio) {
		for(Deposito d:depositos){
			if(d.incorporarPaquete(destino, peso, volumen, frio))
				return true;
		}
		return false;
	}

	public double cargarTransporte(String idTransp){
		double volumenCarg=0;
		Transporte t=buscarTransporte(idTransp);
		if(t!=null) {
			for(Deposito d:depositos){
				volumenCarg+=t.cargarTransporte(d);
			}
		}
		return volumenCarg;
	}

	public void iniciarViaje(String idTransp){
		if(buscarTransporte(idTransp)!=null)
			buscarTransporte(idTransp).iniciarViaje(idTransp);
	}

	void finalizarViaje(String idTransp){
		if(buscarTransporte(idTransp)!=null)
			buscarTransporte(idTransp).finalizarViaje(idTransp);
	}

	public double obtenerCostoViaje(String idTransp) {
		double costo=0;
		Transporte t=buscarTransporte(idTransp);
		if(t!=null){
			if(cantKmViaje(t.getDestino())!=-1)
				costo=t.obtenerCostoViaje(idTransp, cantKmViaje(t.getDestino()));
		}
		return costo;
	}

	public String obtenerTransporteIgual(String idTransp){
		String transpIgual=null;
		Transporte t=buscarTransporte(idTransp);
		if(t!=null){
			for(Transporte otroTransp:flotaTransporte.values()){
				if(t.equals(otroTransp))
					transpIgual=otroTransp.getNumeroID();
			}
		}
		return transpIgual;
	}

	public String enviarTranspReemplazo(String idTransp){
		String reemplazo=null;
		for(Transporte t:flotaTransporte.values()) {//quitar esto
			if(t.getNumeroID().equals(idTransp) && t.isEnViaje()==true){
				for(Transporte otro:flotaTransporte.values()){
					if(t.otroVacioIgual(otro)){		
						reemplazo=otro.getNumeroID();
						t.moverMercaderia(otro);
						iniciarViaje(reemplazo);
						finalizarViaje(idTransp);	
					}
				}
			}
		}
		return reemplazo;
	}

	Transporte buscarTransporte(String idTransp) {
		return flotaTransporte.get(idTransp);
	}

	//devuelve cantidad de km hasta el destino y si no lo encontro devuelve -1
	int cantKmViaje(String destino){
		for(Viaje v:destinos){
			if(v.getDestino().equals(destino))
				return v.getDistanciaEnkm();
		}
		return -1;
	}

	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append("Empresa nombre=").append(nombre);
		s.append(", Cuit=").append(cuit).append("\n");
		s.append("Depositos [\n");
		for(Deposito d:depositos) {
			s.append(d);
		}
		s.append("]\n");
		s.append("Transportes [\n");
		for(Transporte t:flotaTransporte.values()) {
			s.append(t);
		}
		s.append("]\n");
		s.append("Destinos [\n");
		for(Viaje v:destinos) {
			s.append(v);
		}
		s.append("]\n");

		return s.toString();
	}

	public String cantTranspEnViaje() {
		int contTrailer=0;
		int contMega=0;
		int contFlete=0;
		TreeMap<String,Integer> transpEnViaje=new TreeMap<String, Integer>();
		for(Transporte t:flotaTransporte.values()) {
			if(t.isEnViaje()==true)
				if(t.getTipo().equals(TipoTransporte.TrailerComun))
					contTrailer++;
				else if (t.getTipo().equals(TipoTransporte.MegaTrailer)) 
					contMega++;
				else 
					contFlete++;
		}
		transpEnViaje.put("Trailer Comun", contTrailer);
		transpEnViaje.put("Mega trailer",contMega);
		transpEnViaje.put("Fletes",contFlete);
		return transpEnViaje.toString();
	}

	public String cantPaqEnDepFrio() {
		int paqPropio=0;
		int paqTerceriz=0;
		TreeMap<String, Integer> cantPaq=new TreeMap<String, Integer>();
		for(Deposito d:depositos) {
			if(d.isFrigorifico()==true)
				if(d.isPropio()==true)
					paqPropio+=d.cantPaqAlmacenados();
				else 
					paqTerceriz+=d.cantPaqAlmacenados();
		}
		cantPaq.put("Depositos propios", paqPropio);
		cantPaq.put("Depositos tercerizados",paqTerceriz);

		return cantPaq.toString();	
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if(nombre.length()>30 || nombre.length()<1)
			throw new RuntimeException("El nombre de la empresa estar entre 1 y 30 caracteres");
		this.nombre = nombre;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		if(cuit.length()!=11)
			throw new RuntimeException("el cuit debe contener 11 caracteres");
		this.cuit = cuit;
	}
}