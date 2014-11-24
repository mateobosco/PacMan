package com.g7.modelo.laberinto;

import com.g7.modelo.Posicion;

public class Dimension {
	private int ancho;
	private int alto;
	
	public Dimension(int ancho, int alto){
		this.ancho = ancho;
		this.alto = alto;
	}
	
	public int alto(){
		return this.alto;
	}
	
	public int ancho(){
		return this.ancho;
	}

	public boolean contiene(Posicion posicion) {
		int x = posicion.getCoordenadaX();
		int y = posicion.getCoordenadaY();
		if (x < 0 || x >= this.ancho){
			return false;
		}
		if (y < 0 || y >= this.alto){
			return false;
		}
		return true;
	}

}
