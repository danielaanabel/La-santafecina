package logica;


public class Paquetes {
	
	private double peso;
	private double volumen;
	private String destino;
	private boolean necesitaFrio;
	
	public Paquetes(double peso, double volumen, String destino, boolean necesitaFrio) {
		setPeso(peso);
		setVolumen(volumen);
		setDestino(destino);
		setNecesitaFrio(necesitaFrio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paquetes other = (Paquetes) obj;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (necesitaFrio != other.necesitaFrio)
			return false;
		if (peso != other.peso)
			return false;
		if (volumen!= other.volumen)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append("\n     Paquete {Peso=").append(peso);
		s.append(", Volumen=").append(volumen);
		s.append(", Destino=").append(destino);
		s.append(", Frio=").append(necesitaFrio).append("}");
		return s.toString();	
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		if(peso<1 || peso>1000)
			throw new RuntimeException("peso incorrecto");
		this.peso = peso;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		if(volumen<1)
			throw new RuntimeException("volumen incorrecto");
		this.volumen = volumen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public boolean isNecesitaFrio() {
		return necesitaFrio;
	}

	public void setNecesitaFrio(boolean necesitaFrio) {
		this.necesitaFrio = necesitaFrio;
	}	
}
