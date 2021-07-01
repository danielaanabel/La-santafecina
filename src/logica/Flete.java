package logica;
public class Flete extends Transporte{
	
	private int cantAcompa�antes;
	private double costoFijoAcompa�ante;

	public Flete(String numeroID, double cargaMaxima, double capacidad, double costoPorKm,
			String destino, boolean enViaje, int cantAcompa�antes, double costoFijoAcompa�ante, TipoTransporte tipo) {
		super(numeroID, cargaMaxima, capacidad, false, costoPorKm, destino, enViaje, tipo);
		setCantAcompa�antes(cantAcompa�antes);
		setCostoFijoAcompa�ante(costoFijoAcompa�ante);
	}
	
	@Override
	double obtenerCostoViaje(String idTransp,int km){
		double costo=0;
		if(getNumeroID().compareTo(idTransp)==0){
			if (isEnViaje()==false) 
				throw new RuntimeException("el camion no esta en viaje");
			else {
				costo=costo+(getCostoPorKm()*km)+(cantAcompa�antes*costoFijoAcompa�ante)+getCostoCargaTerc();	
			}
		}
		return costo;
	}

	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append(" Trasportes {N�meroID=").append(getNumeroID());
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

	public int getCantAcompa�antes() {
		return cantAcompa�antes;
	}

	public void setCantAcompa�antes(int cantAcompa�antes) {
		if(cantAcompa�antes<0 || cantAcompa�antes>3)
			throw new RuntimeException("solo se permiten hasta 3 acompa�antes");
		this.cantAcompa�antes = cantAcompa�antes;
	}

	public double getCostoFijoAcompa�ante() {
		return costoFijoAcompa�ante;
	}

	public void setCostoFijoAcompa�ante(double costoFijoAcompa�ante) {
		if(costoFijoAcompa�ante<1) {
			throw new RuntimeException("debe ser decimal positivo");
		}
		this.costoFijoAcompa�ante = costoFijoAcompa�ante;
	}	
}
