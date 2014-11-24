package com.g7.modelo.fantasma.estados;


public class NivelDos extends Cazador {
	
	private int ciclosHastaEvolucion;
	
	public NivelDos() {
		this.ciclosHastaEvolucion = Configuracion.getConfiguracion().getCiclosHastaEvolucionCazadorNivelDos();;
	}

	public Estado estadoLuegoDeUnCiclo() {
		this.ciclosHastaEvolucion--;
		if(this.ciclosHastaEvolucion == 0)
			return new NivelTres();
		return this;
	}
	
	public String getDescripcionEspecifica() {
		return EstadosFantasma.CAZADOR_NIVEL_DOS;
	}
}
