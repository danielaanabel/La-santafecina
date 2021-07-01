package logica;
public class MegaTrailer extends Transporte{
	
	private double seguroDeCarga;
	private double costoFijo;
	private double comida;

//solo se los puede utilizar en viajes de más de 500 km
	public MegaTrailer(String numeroID, double cargaMaxima, double capacidad, boolean frigorifico, double costoPorKm,
			String destino, boolean enViaje, double seguroDeCarga, double costoFijo, double comidaConductor, TipoTransporte tipo) {
		super(numeroID, cargaMaxima, capacidad, frigorifico, costoPorKm, destino, enViaje, tipo);
		setSeguroDeCarga(seguroDeCarga);
		setCostoFijo(costoFijo);
		setComida(comidaConductor);
	}
	
	@Override
	double obtenerCostoViaje(String idTransp,int km){
		double costo=0;
		if(getNumeroID().compareTo(idTransp)==0){
			if (isEnViaje()==false) 
				throw new RuntimeException("el camion no esta en viaje");
			else {
				costo=costo+(getCostoPorKm()*km)+seguroDeCarga+costoFijo+comida+getCostoCargaTerc();
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
		if(seguroDeCarga<1) {
			throw new RuntimeException("debe ser decimal positivo");
		}
		this.seguroDeCarga = seguroDeCarga;
	}

	public double getCostoFijo() {
		return costoFijo;
	}

	public void setCostoFijo(double costoFijo) {
		if(costoFijo<1) {
			throw new RuntimeException("debe ser decimal positivo");
		}
		this.costoFijo = costoFijo;
	}

	public double getComida() {
		return comida;
	}

	public void setComida(double comida) {
		if(comida<1) {
			throw new RuntimeException("debe ser decimal positivo");
		}
		this.comida = comida;
	}
}
