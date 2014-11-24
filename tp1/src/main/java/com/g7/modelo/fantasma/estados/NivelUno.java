package com.g7.modelo.fantasma.estados;


public class NivelUno extends Cazador {
	
	private int ciclosHastaEvolucion;
	
	public NivelUno() {
		this.ciclosHastaEvolucion = Configuracion.getConfiguracion().getCiclosHastaEvolucionCazadorNivelUno();
	}

	public Estado estadoLuegoDeUnCiclo() {
		this.ciclosHastaEvolucion--;
		if(this.ciclosHastaEvolucion == 0)
			return new NivelDos();
		return this;
	}
	
	public String getDescripcionEspecifica() {
		return EstadosFantasma.CAZADOR_NIVEL_UNO;
	}

}
