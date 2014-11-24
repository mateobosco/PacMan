package com.g7.modelo;

import java.util.Observable;

import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.laberinto.Laberinto;

public class Bolon extends Observable implements Posicionable {
	
	private Laberinto laberinto;
	private Posicion posicion;
	private int puntaje;
	private String id;
	
	public Bolon(Laberinto laberinto, Posicion posicion) {
		this.laberinto = laberinto;
		this.posicion = posicion;
		this.laberinto.agregarPosicionable(this);
		this.puntaje = 0;
		this.id = "bolon";
	}
	public Posicion posicion() {
		return posicion;
	}
	
	public void serComido(){
		Fantasma.setBolonComido(true);
		this.setChanged();
		this.notifyObservers();
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	
	public String getId(){
		return this.id;
	}

}
