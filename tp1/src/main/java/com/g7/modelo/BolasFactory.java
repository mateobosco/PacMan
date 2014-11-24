package com.g7.modelo;

import com.g7.modelo.laberinto.Laberinto;

public class BolasFactory {
	
	public Bolon bolon(Laberinto laberinto, Posicion posicion){
		return new Bolon(laberinto,posicion);
	}

	public Bolita bolita(Laberinto laberinto, Posicion posicion){
		return new Bolita(laberinto,posicion);
	}

}
