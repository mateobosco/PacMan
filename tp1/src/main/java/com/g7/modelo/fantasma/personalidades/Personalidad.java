package com.g7.modelo.fantasma.personalidades;

import com.g7.modelo.Posicion;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.laberinto.Camino;
import com.g7.modelo.laberinto.Laberinto;

public interface Personalidad {
	
	public abstract Camino getCamino(Laberinto laberinto, Posicion inicio, Posicion fin); 
	public boolean veoPacMan(Laberinto laberinto, Posicion inicio, Posicion fin);
	public Posicion mover(Fantasma fantasma, Posicion posicionPacman);
	public int getVision();
}
