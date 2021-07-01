package logica;
public class Flete extends Transporte{
	
	private int cantAcompañantes;
	private double costoFijoAcompañante;

	public Flete(String numeroID, double cargaMaxima, double capacidad, double costoPorKm,
			String destino, boolean enViaje, int cantAcompañantes, double costoFijoAcompañante, TipoTransporte tipo) {
		super(numeroID, cargaMaxima, capacidad, false, costoPorKm, destino, enViaje, tipo);
		setCantAcompañantes(cantAcompañantes);
		setCostoFijoAcompañante(costoFijoAcompañante);
	}
	
	@Override
	double obtenerCostoViaje(String idTransp,int km){
		double costo=0;
		if(getNumeroID().compareTo(idTransp)==0){
			if (isEnViaje()==false) 
				throw new RuntimeException("el camion no esta en viaje");
			else {
				costo=costo+(getCostoPorKm()*km)+(cantAcompañantes*costoFijoAcompañante)+getCostoCargaTerc();	
			}
		}
		return costo;
	}

	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append(" Trasportes {NúmeroID=").append(getNumeroID());
		s.append(", CargaMaxima=").append(getCargaMaxima());
		s.append(", Capacidad=").append(getCapacidad());
		s.append(", Frigorifico=").append(isFrigorifico());
		s.append(", costoKm=").append(getCostoPorKm());
		s.append(", Destino=").append(getDestino());
		s.append(", enViaje=").append(isEnViaje());
		s.append(",\n   Caja {");
		for(Paquetes p:getCaja()) {
			s.append(p);
		}
		s.append("}}\n");
		return s.toString();
	}

	public int getCantAcompañantes() {
		return cantAcompañantes;
	}

	public void setCantAcompañantes(int cantAcompañantes) {
		if(cantAcompañantes<0 || cantAcompañantes>3)
			throw new RuntimeException("solo se permiten hasta 3 acompañantes");
		this.cantAcompañantes = cantAcompañantes;
	}

	public double getCostoFijoAcompañante() {
		return costoFijoAcompañante;
	}

	public void setCostoFijoAcompañante(double costoFijoAcompañante) {
		if(costoFijoAcompañante<1) {
			throw new RuntimeException("debe ser decimal positivo");
		}
		this.costoFijoAcompañante = costoFijoAcompañante;
	}	
}
