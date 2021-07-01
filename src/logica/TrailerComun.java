package logica;

public class TrailerComun extends Transporte{
	
	private double seguroDeCarga;

//solo se pueden utilizar en viajes con un destino no mayor de 500 km
	public TrailerComun(String numeroID, double cargaMaxima, double capacidad, boolean frigorifico, double costoPorKm,
			String destino, boolean enViaje, double seguroDeCarga, TipoTransporte tipo) {
		super(numeroID, cargaMaxima, capacidad, frigorifico, costoPorKm, destino, enViaje, tipo);
		setSeguroDeCarga(seguroDeCarga);
	}
	
	@Override
	double obtenerCostoViaje(String idTransp,int km){
		double costo=0;
		if(getNumeroID().compareTo(idTransp)==0){
			if (isEnViaje()==false) 
				throw new RuntimeException("el camion no esta en viaje");
			else {
				costo=costo+(getCostoPorKm()*km)+seguroDeCarga+getCostoCargaTerc();
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

	public double getSeguroDeCarga() {
		return seguroDeCarga;
	}

	public void setSeguroDeCarga(double seguroDeCarga) {
		if(seguroDeCarga<1)
			throw new RuntimeException("debe ser decimal positivo");
		this.seguroDeCarga = seguroDeCarga;
	}	
}
