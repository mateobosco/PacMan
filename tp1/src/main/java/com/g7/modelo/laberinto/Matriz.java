package com.g7.modelo.laberinto;

import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;

public class Matriz <T extends Celda>{

	private Celda[][] matriz;
	private Dimension dimension;
	
	public Matriz(Dimension dimension) {
		this.matriz = new Celda[dimension.ancho()][dimension.alto()]; 
		this.dimension = dimension;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void add(Celda celda) {
		this.matriz[celda.posicion().getCoordenadaX()][celda.posicion().getCoordenadaY()] = celda;	
	}
	
	public boolean posicionVacia(Posicion posicion){
		return this.matriz[posicion.getCoordenadaX()][posicion.getCoordenadaY()] == null;
	}
	
	
	public Celda at(Posicion posicion) {
		return this.matriz[posicion.getCoordenadaX()][posicion.getCoordenadaY()];	
	}

	public void addElementoPosicionableEnCelda(Posicionable posicionable) {
		Celda celda = at(posicionable.posicion());
		celda.addElementoPosicionable(posicionable);
	}
	
	public void eliminarElementoPosicionableEnCelda(Posicionable posicionable) {
		Celda celda = at(posicionable.posicion());
		celda.eliminarElementoPosicionable(posicionable);
	}
	
}
