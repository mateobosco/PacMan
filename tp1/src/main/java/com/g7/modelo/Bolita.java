package com.g7.modelo;

import java.util.Observable;

import com.g7.modelo.laberinto.Laberinto;

public class Bolita extends Observable implements Posicionable{
	private Laberinto laberinto;
	private Posicion posicion;
	private int puntaje;
	private String id;
	
	public Bolita(Laberinto laberinto, Posicion posicion) {
		this.laberinto = laberinto;
		this.posicion = posicion;
		this.laberinto.agregarPosicionable(this);
		this.puntaje = 1;
		this.id = "bolita";
	}
	public Posicion posicion() {
		return posicion;
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	
	public void serComido(){
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getId(){
		return this.id;
	}

}
