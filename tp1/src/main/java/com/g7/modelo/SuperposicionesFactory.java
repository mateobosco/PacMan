package com.g7.modelo;

import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.laberinto.Celda;

public class SuperposicionesFactory {
	
	public void pacManOnFantasma(PacMan pacman, Posicionable posicionable, Celda celdaNueva){
		if(((Fantasma)posicionable).estado().equals(EstadosFantasma.CAZADOR)){
			pacman.morir();
			celdaNueva.eliminarElementoPosicionable(pacman);
		}
		else if(((Fantasma)posicionable).estado().equals(EstadosFantasma.PRESA)){	
			((Fantasma)posicionable).morir();
			pacman.addPuntaje(((Fantasma)posicionable).getPuntaje());
			celdaNueva.eliminarElementoPosicionable(posicionable);
		}
	}
	
	public void pacManOnBolita(PacMan pacman, Posicionable posicionable, Celda celdaNueva){
		pacman.addPuntaje(((Bolita)posicionable).getPuntaje());
		((Bolita)posicionable).serComido();
		celdaNueva.eliminarElementoPosicionable(posicionable);
	}
	
	public void pacManOnBolon(PacMan pacman, Posicionable posicionable, Celda celdaNueva){
		pacman.addPuntaje(((Bolon)posicionable).getPuntaje());
		((Bolon)posicionable).serComido();
		celdaNueva.eliminarElementoPosicionable(posicionable);
	}
	
	public void pacManOnPacMan(PacMan pacman, Posicionable posicionable, Celda celdaNueva){
	
	}
	
	public void pacManOnFruta(PacMan pacman, Posicionable posicionable, Celda celdaNueva){
		pacman.addPuntaje(((Fruta)posicionable).getPuntaje());
		((Fruta)posicionable).morir();
		celdaNueva.eliminarElementoPosicionable(posicionable);
	}

	public void fantasmaOnPacMan(Fantasma fantasma, Posicionable posicionable, Celda celdaNueva){
		if(fantasma.estado().equals(EstadosFantasma.CAZADOR)){
			((PacMan)posicionable).morir();
			celdaNueva.eliminarElementoPosicionable(posicionable);
		}
		else if(fantasma.estado().equals(EstadosFantasma.PRESA)){
			fantasma.morir();
			((PacMan)posicionable).addPuntaje(fantasma.getPuntaje());
			celdaNueva.eliminarElementoPosicionable(fantasma);
		}
	}
	
	public void fantasmaOnBolita(Fantasma fantasma, Posicionable posicionable, Celda celdaNueva){

	}
	
	public void fantasmaOnBolon(Fantasma fantasma, Posicionable posicionable, Celda celdaNueva){

	}
	
	public void fantasmaOnFantasma(Fantasma fantasma, Posicionable posicionable, Celda celdaNueva){

	}
	
	public void fantasmaOnFruta(Fantasma fantasma, Posicionable posicionable, Celda celdaNueva){
		
	}
	
	public void frutaOnPacMan(Fruta fruta, Posicionable posicionable, Celda celdaNueva){
		((PacMan)posicionable).addPuntaje(fruta.getPuntaje());
		fruta.morir();
		celdaNueva.eliminarElementoPosicionable(fruta);
	}
	
	public void frutaOnBolita(Fruta fruta, Posicionable posicionable, Celda celdaNueva){

	}
	
	public void frutaOnBolon(Fruta fruta, Posicionable posicionable, Celda celdaNueva){

	}
	
	public void frutaOnFantasma(Fruta fruta, Posicionable posicionable, Celda celdaNueva){

	}
	
	public void frutaOnFruta(Fruta fruta, Posicionable posicionable, Celda celdaNueva){
		
	}
}
