package com.g7.modelo.fantasma.estados;


public class Muerto implements Estado {
	
	private int ciclosHastaEvolucion;
	
	public Muerto()	{
		this.ciclosHastaEvolucion = Configuracion.getConfiguracion().getCiclosHastaEvolucionMuerto();
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
		return this;
	}
	
	public String getDescripcion() {
		return EstadosFantasma.MUERTO;
	}

}
