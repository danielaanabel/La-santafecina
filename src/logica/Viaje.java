package logica;


public class Viaje {
	
	private String destino;
	private int distanciaEnkm;
	
	public Viaje(String destino, int distanciaEnkm) {
		setDestino(destino);
		setDistanciaEnkm(distanciaEnkm);
	}

	@Override
	public String toString() {
		return " Viaje {destino=" + destino + ", Distancia=" + distanciaEnkm + "}\n";
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino){
		this.destino = destino;
	}

	public int getDistanciaEnkm() {
		return distanciaEnkm;
	}

	public void setDistanciaEnkm(int distanciaEnkm) {
		if(distanciaEnkm<1)
			throw new RuntimeException("debe ser entero positivo");
		this.distanciaEnkm = distanciaEnkm;
	}	
}
