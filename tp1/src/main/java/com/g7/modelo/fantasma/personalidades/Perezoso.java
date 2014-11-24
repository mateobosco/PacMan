package com.g7.modelo.fantasma.personalidades;

import java.util.ArrayList;
import java.util.Collection;

import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.laberinto.Camino;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Laberinto;

public class Perezoso implements Personalidad {
	
	private int vision;
	private Camino camino;
	private boolean viendoPacman;
	
	public Perezoso(){
		this.vision = Configuracion.getConfiguracion().getVisionPerezoso();
		this.camino = new Camino();
		this.viendoPacman = false;
	}
	
	@Override
	public String toString(){
		return "perezoso";
	}

	public Camino getCamino(Laberinto laberinto, Posicion inicio, Posicion fin) {
		Camino unCamino = laberinto.caminoMinimoEntre(inicio, fin);
		if(unCamino.tamanio() <= this.vision && unCamino.tamanio() > 0){
			return unCamino;
		}
		else{
			return unCamino = laberinto.caminoAleatorio(inicio);
		}
	}
	
	public boolean veoPacMan(Laberinto laberinto, Posicion inicio, Posicion fin){
		Camino unCamino = laberinto.caminoMinimoEntre(inicio, fin);
		if(unCamino.tamanio() <= this.vision && unCamino.tamanio() > 0){
			this.viendoPacman = true;
		}
		else{
			this.viendoPacman = false;
		}
		return this.viendoPacman;
	}
	
	public Posicion mover(Fantasma fantasma, Posicion posicionPacman){
		if(fantasma.estado() != EstadosFantasma.MUERTO){
			if(fantasma.estado() == EstadosFantasma.CAZADOR){
				if(veoPacMan(fantasma.getLaberinto(), fantasma.posicion(), posicionPacman) || this.camino.tamanio() == 0){
					this.camino = getCamino(fantasma.getLaberinto(), fantasma.posicion(), posicionPacman);
				}
			}
			else if(fantasma.estado() == EstadosFantasma.PRESA){
				if(veoPacMan(fantasma.getLaberinto(), fantasma.posicion(), posicionPacman) || this.camino.tamanio() == 0){
					this.camino = getCamino(fantasma.getLaberinto(), fantasma.posicion(), getPosicionAlejadaAPacMan(fantasma,posicionPacman));
				}
			}
			Celda celdaVieja = fantasma.getLaberinto().getCelda(fantasma.posicion());
			Celda celdaNueva = fantasma.getLaberinto().getCelda(this.camino.siguientePosicion());
			fantasma.setDireccionActual(celdaVieja.getDireccionAdyacencia(celdaNueva));
			celdaVieja.eliminarElementoPosicionable(fantasma);
			celdaNueva.addElementoPosicionable(fantasma);
			Collection<Posicionable> elementos = new ArrayList<Posicionable>();
			elementos.addAll(celdaNueva.getElementosPosicionable());
			for(Posicionable elemento : elementos){
				fantasma.superponer(elemento,celdaNueva);
			}
			return celdaNueva.posicion();
		}
		else{
			this.camino.vaciar();
			Celda celdaVieja = fantasma.getLaberinto().getCelda(fantasma.posicion());
			Celda celdaNueva = fantasma.getLaberinto().getCelda(new Posicion(Integer.parseInt(fantasma.getLaberinto().getInicioFantasma().substring(2,4)),Integer.parseInt(fantasma.getLaberinto().getInicioFantasma().substring(0,2))));
			celdaVieja.eliminarElementoPosicionable(fantasma);
			celdaNueva.addElementoPosicionable(fantasma);
			Collection<Posicionable> elementos = new ArrayList<Posicionable>();
			elementos.addAll(celdaNueva.getElementosPosicionable());
			for(Posicionable elemento : elementos){
				fantasma.superponer(elemento,celdaNueva);
			}
			return celdaNueva.posicion();
		}
	}

	public int getVision() {
		return vision;
	}
	
	private Posicion getPosicionAlejadaAPacMan(Fantasma fantasma, Posicion posicionPacman){
		if(fantasma.posicion().getCoordenadaX()<posicionPacman.getCoordenadaX()){
			if(fantasma.posicion().getCoordenadaY()<posicionPacman.getCoordenadaY()){
				Celda unaCelda = fantasma.getLaberinto().getCelda(fantasma.posicion());
				if(unaCelda.getCeldaIzquierda() != null){return unaCelda.getCeldaIzquierda().posicion();}
				if(unaCelda.getCeldaArriba() != null){return unaCelda.getCeldaArriba().posicion();}
				if(unaCelda.getCeldaDerecha() != null){return unaCelda.getCeldaDerecha().posicion();}
				if(unaCelda.getCeldaAbajo() != null){return unaCelda.getCeldaAbajo().posicion();}
				
			}
			else {
				Celda unaCelda = fantasma.getLaberinto().getCelda(fantasma.posicion());
				if(unaCelda.getCeldaIzquierda() != null){return unaCelda.getCeldaIzquierda().posicion();}
				if(unaCelda.getCeldaAbajo() != null){return unaCelda.getCeldaAbajo().posicion();}
				if(unaCelda.getCeldaDerecha() != null){return unaCelda.getCeldaDerecha().posicion();}
				if(unaCelda.getCeldaArriba() != null){return unaCelda.getCeldaArriba().posicion();}
				
			}
		}
		else{
			if(fantasma.posicion().getCoordenadaY()<posicionPacman.getCoordenadaY()){
				Celda unaCelda = fantasma.getLaberinto().getCelda(fantasma.posicion());
				if(unaCelda.getCeldaDerecha() != null){return unaCelda.getCeldaDerecha().posicion();}
				if(unaCelda.getCeldaArriba() != null){return unaCelda.getCeldaArriba().posicion();}
				if(unaCelda.getCeldaIzquierda() != null){return unaCelda.getCeldaIzquierda().posicion();}
				if(unaCelda.getCeldaAbajo() != null){return unaCelda.getCeldaAbajo().posicion();}
				
			}
			else {
				Celda unaCelda = fantasma.getLaberinto().getCelda(fantasma.posicion());
				if(unaCelda.getCeldaDerecha() != null){return unaCelda.getCeldaDerecha().posicion();}
				if(unaCelda.getCeldaAbajo() != null){return unaCelda.getCeldaAbajo().posicion();}
				if(unaCelda.getCeldaIzquierda() != null){return unaCelda.getCeldaIzquierda().posicion();}
				if(unaCelda.getCeldaArriba() != null){return unaCelda.getCeldaArriba().posicion();}
				
			}
		}
		return fantasma.posicion();
	}

}
