package com.g7.modelo.fantasma.personalidades;


public class Configuracion {

	private static Configuracion configuracion;
	
	private int visionZonzo;
	private int visionPerezoso;
	private int visionBuscador;
	private int visionBuscadorTemperamentalNivelUno;
	private int visionBuscadorTemperamentalNivelDos;
	private int visionBuscadorTemperamentalNivelTres;
	
	public int getVisionZonzo() {
		return visionZonzo;
	}
	public void setVisionZonzo(int visionZonzo) {
		this.visionZonzo = visionZonzo;
	}
	public int getVisionPerezoso() {
		return visionPerezoso;
	}
	public void setVisionPerezoso(int visionPerezoso) {
		this.visionPerezoso = visionPerezoso;
	}
	public int getVisionBuscador() {
		return visionBuscador;
	}
	public void setVisionBuscador(int visionBuscador) {
		this.visionBuscador = visionBuscador;
	}
	public int getVisionBuscadorTemperamentalNivelUno() {
		return visionBuscadorTemperamentalNivelUno;
	}
	public void setVisionBuscadorTemperamentalNivelUno(int visionBuscadorTemperamental) {
		this.visionBuscadorTemperamentalNivelUno = visionBuscadorTemperamental;
	}
	public int getVisionBuscadorTemperamentalNivelDos() {
		return visionBuscadorTemperamentalNivelDos;
	}
	public void setVisionBuscadorTemperamentalNivelDos(int visionBuscadorTemperamental) {
		this.visionBuscadorTemperamentalNivelDos = visionBuscadorTemperamental;
	}
	public int getVisionBuscadorTemperamentalNivelTres() {
		return visionBuscadorTemperamentalNivelTres;
	}
	public void setVisionBuscadorTemperamentalNivelTres(int visionBuscadorTemperamental) {
		this.visionBuscadorTemperamentalNivelTres = visionBuscadorTemperamental;
	}
	
	private Configuracion()	{
		visionZonzo = 4;
		visionPerezoso = 8;
		visionBuscador = 10;
		visionBuscadorTemperamentalNivelUno = 12;
		visionBuscadorTemperamentalNivelDos = 14;
		visionBuscadorTemperamentalNivelTres = 16;
		
		
	}
	
	public static Configuracion getConfiguracion() {
		if(Configuracion.configuracion == null)
			Configuracion.configuracion = new Configuracion();
		return Configuracion.configuracion;
	}

	
}
