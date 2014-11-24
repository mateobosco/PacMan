package com.g7.modelo.fantasma.estados;


public class Presa implements Estado {
	
	private int ciclosHastaEvolucion;
	
	public Presa() {
		this.ciclosHastaEvolucion = Configuracion.getConfiguracion().getCiclosHastaEvolucionPresa();
	}

	public Estado estadoLuegoDeUnCiclo() {
		this.ciclosHastaEvolucion--;
		if(this.ciclosHastaEvolucion == 0)
			return new NivelUno();
		return this;
	}

	public Estado convertirAPresa() {
		return this;
	}

	public Estado morir() {
		return new Muerto();
	}
	
	
	public String getDescripcion() {
		return EstadosFantasma.PRESA;
	}

}
