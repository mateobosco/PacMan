package com.g7.modelo.fantasma.estados;


public class NivelTres extends Cazador {
	
	public NivelTres() {
	}

	public Estado estadoLuegoDeUnCiclo() {
		return this;
	}	

	public String getDescripcionEspecifica() {
		return EstadosFantasma.CAZADOR_NIVEL_TRES;
	}

}
