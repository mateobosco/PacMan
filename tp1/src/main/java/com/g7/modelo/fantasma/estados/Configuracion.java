package com.g7.modelo.fantasma.estados;

public class Configuracion {
	
	private static Configuracion configuracion;
	
	private int ciclosHastaEvolucionCazadorNivelUno;
	private int ciclosHastaEvolucionCazadorNivelDos;
	private int ciclosHastaEvolucionCazadorNivelTres;
	private int ciclosHastaEvolucionPresa;
	private int ciclosHastaEvolucionMuerto;
	
	public int getCiclosHastaEvolucionCazadorNivelUno() {
		return ciclosHastaEvolucionCazadorNivelUno;
	}

	public void setCiclosHastaEvolucionCazadorNivelUno(
			int ciclosHastaEvolucionCazadorNivelUno) {
		this.ciclosHastaEvolucionCazadorNivelUno = ciclosHastaEvolucionCazadorNivelUno;
	}

	public int getCiclosHastaEvolucionCazadorNivelDos() {
		return ciclosHastaEvolucionCazadorNivelDos;
	}

	public void setCiclosHastaEvolucionCazadorNivelDos(
			int ciclosHastaEvolucionCazadorNivelDos) {
		this.ciclosHastaEvolucionCazadorNivelDos = ciclosHastaEvolucionCazadorNivelDos;
	}

	public int getCiclosHastaEvolucionCazadorNivelTres() {
		return ciclosHastaEvolucionCazadorNivelTres;
	}

	public void setCiclosHastaEvolucionCazadorNivelTres(
			int ciclosHastaEvolucionCazadorNivelTres) {
		this.ciclosHastaEvolucionCazadorNivelTres = ciclosHastaEvolucionCazadorNivelTres;
	}
	
	public int getCiclosHastaEvolucionPresa() {
		return ciclosHastaEvolucionPresa;
	}

	public void setCiclosHastaEvolucionPresa(int ciclosHastaEvolucionPresa) {
		this.ciclosHastaEvolucionPresa = ciclosHastaEvolucionPresa;
	}

	public int getCiclosHastaEvolucionMuerto() {
		return ciclosHastaEvolucionMuerto;
	}

	public void setCiclosHastaEvolucionMuerto(int ciclosHastaEvolucionMuerto) {
		this.ciclosHastaEvolucionMuerto = ciclosHastaEvolucionMuerto;
	}

	private Configuracion()	{
		ciclosHastaEvolucionCazadorNivelUno = 20;
		ciclosHastaEvolucionCazadorNivelDos = 20;
		ciclosHastaEvolucionCazadorNivelTres = 20;
		ciclosHastaEvolucionPresa = 40;
		ciclosHastaEvolucionMuerto = 10;
		
	}
	
	public static Configuracion getConfiguracion() {
		if(Configuracion.configuracion == null)
			Configuracion.configuracion = new Configuracion();
		return Configuracion.configuracion;
	}

}
