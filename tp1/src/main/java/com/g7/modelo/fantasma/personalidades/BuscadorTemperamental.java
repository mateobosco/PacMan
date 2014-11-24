package com.g7.modelo.fantasma.personalidades;

import java.util.ArrayList;
import java.util.Collection;

import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.Cazador;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.laberinto.Camino;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Laberinto;

public class BuscadorTemperamental implements Personalidad {
	
	private int vision;
	private Camino camino;
	
	public BuscadorTemperamental(){
		this.vision = Configuracion.getConfiguracion().getVisionBuscadorTemperamentalNivelUno();
		this.camino = new Camino();
	}
	
	@Override
	public String toString(){
		return "buscadorTemperamental";
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
		return (unCamino.tamanio() <= this.vision && unCamino.tamanio() > 0);
	}
	
	public Posicion mover(Fantasma fantasma, Posicion posicionPacman){
		if(fantasma.estado() != EstadosFantasma.MUERTO){
			if(fantasma.estado() == EstadosFantasma.CAZADOR){
				if(((Cazador)fantasma.getEstado()).getDescripcionEspecifica() == EstadosFantasma.CAZADOR_NIVEL_UNO){
					this.vision = Configuracion.getConfiguracion().getVisionBuscadorTemperamentalNivelUno();
				}
				else if(((Cazador)fantasma.getEstado()).getDescripcionEspecifica() == EstadosFantasma.CAZADOR_NIVEL_DOS){
					this.vision = Configuracion.getConfiguracion().getVisionBuscadorTemperamentalNivelDos();
				}
				else if(((Cazador)fantasma.getEstado()).getDescripcionEspecifica() == EstadosFantasma.CAZADOR_NIVEL_TRES){
					this.vision = Configuracion.getConfiguracion().getVisionBuscadorTemperamentalNivelTres();
				}
				if(veoPacMan(fantasma.getLaberinto(), fantasma.posicion(), posicionPacman) || this.camino.tamanio() == 0){
					this.camino = getCamino(fantasma.getLaberinto(), fantasma.posicion(), posicionPacman);
				}
			}
			else if(fantasma.estado() == EstadosFantasma.PRESA){
				this.vision = Configuracion.getConfiguracion().getVisionBuscadorTemperamentalNivelUno();
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
		return fantasma.posicion();

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
