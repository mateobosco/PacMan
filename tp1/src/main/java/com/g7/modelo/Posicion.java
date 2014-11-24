package com.g7.modelo;

public class Posicion {
	
	private int coordenadaX;
	private int coordenadaY;
	
	public Posicion(int coordenadaX, int coordenadaY) {
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
	}
	
	public int getCoordenadaX() {
		return this.coordenadaX;
	}

	public int getCoordenadaY() {
		return this.coordenadaY;
	}
	
	public void setCoordenadaX(int coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	
	public void setCoordenadaY(int coordenadaY) {
		this.coordenadaY = coordenadaY;
	}
	
	public boolean esIgual(Posicion posicionAcomparar) {
		return (this.coordenadaX == posicionAcomparar.getCoordenadaX()) && (this.coordenadaY == posicionAcomparar.getCoordenadaY());
	}
}
