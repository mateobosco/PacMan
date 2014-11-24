package com.g7.modelo.fantasma.personalidades;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Escenario;
import com.g7.modelo.PacMan;
import com.g7.modelo.Posicion;
import com.g7.modelo.direcciones.Abajo;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.Configuracion;
import com.g7.modelo.laberinto.Camino;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestBuscadorCazador {

	@Before
	public void setUp() throws Exception {
		Configuracion.getConfiguracion();
	}

	@Test
	public void fantasmaBuscadorNoVePacManYSeMueve(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(20),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(14,15),numeroAleatorio(14,15)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorNivelUno(laberinto, new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Posicion posicion = fantasma.posicion();
		for(int i = 0; i < 5 ; i++){
			fantasma.vivir(pacman.posicion());
			assertFalse(posicion.esIgual(fantasma.posicion()));
			posicion = fantasma.posicion();
		}
	}
	
	@Test
	public void fantasmaBuscadorVePacManYSeAcerca(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(20),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorNivelUno(laberinto, new Posicion(numeroAleatorio(1),numeroAleatorio(3,4)));
		Camino camino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
		for(int i = 0; i < 2 ; i++){
			if(camino.tamanio() > 1){
				fantasma.vivir(pacman.posicion());
				Camino otroCamino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
				assertTrue(camino.tamanio()>otroCamino.tamanio());
				camino = otroCamino;
			}
		}
	}
	
	@Test
	public void fantasmaBuscadorDejaDeVerAlPacManYSeAcercaALaUltimaPosicionPacMan(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(20),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(0,0));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorNivelUno(laberinto, new Posicion(numeroAleatorio(1),numeroAleatorio(3,4)));
		Camino camino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
		Posicion ultimaPosicionPacMan = pacman.posicion();;
		for(int i = 0; i < 2 ; i++){
			if(camino.tamanio() > 1){
				fantasma.vivir(pacman.posicion());
				Camino otroCamino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
				assertTrue(camino.tamanio()>otroCamino.tamanio());
				camino = otroCamino;
			}
		}
		while(fantasma.getPersonalidad().veoPacMan(laberinto, fantasma.posicion(), pacman.posicion())){
			ultimaPosicionPacMan = pacman.posicion();
			pacman.vivir();
		}
		pacman.vivir();
		pacman.setDireccion(new Abajo());
		camino = laberinto.caminoMinimoEntre(fantasma.posicion(), ultimaPosicionPacMan);
		fantasma.vivir(ultimaPosicionPacMan);
		for(int i = 0; i < 2 ; i++){
			if(camino.tamanio() > 1){
				fantasma.vivir(pacman.posicion());
				Camino otroCamino = laberinto.caminoMinimoEntre(fantasma.posicion(), ultimaPosicionPacMan);
				assertTrue(camino.tamanio()>otroCamino.tamanio());
				camino = otroCamino;
			}
		}
	}
	
	@Test
	public void fantasmaBuscadorLlegaALaUltimaPosicionPacManYSeSigueMoviendo(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(20),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorNivelUno(laberinto, new Posicion(numeroAleatorio(1),numeroAleatorio(3,4)));
		Camino camino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
		Posicion ultimaPosicionPacMan = pacman.posicion();;
		for(int i = 0; i < 2 ; i++){
			if(camino.tamanio() > 1){
				fantasma.vivir(pacman.posicion());
				Camino otroCamino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
				assertTrue(camino.tamanio()>otroCamino.tamanio());
				camino = otroCamino;
			}
		}
		while(fantasma.getPersonalidad().veoPacMan(laberinto, fantasma.posicion(), pacman.posicion())){
			ultimaPosicionPacMan = pacman.posicion();
			pacman.vivir();
		}
		pacman.vivir();
		pacman.setDireccion(new Abajo());
		camino = laberinto.caminoMinimoEntre(fantasma.posicion(), ultimaPosicionPacMan);
		fantasma.vivir(ultimaPosicionPacMan);
		while(fantasma.getPersonalidad().veoPacMan(laberinto, fantasma.posicion(), ultimaPosicionPacMan)){
			pacman.vivir();
			fantasma.vivir(pacman.posicion());
			Camino otroCamino = laberinto.caminoMinimoEntre(fantasma.posicion(), ultimaPosicionPacMan);
			assertTrue(camino.tamanio()>otroCamino.tamanio());
			camino = otroCamino;
			if(fantasma.posicion().esIgual(ultimaPosicionPacMan)){
				break;
			}
		}
		Posicion posicion = fantasma.posicion();
		for(int i = 0; i < 2 ; i++){
			fantasma.vivir(pacman.posicion());
			assertFalse(posicion.esIgual(fantasma.posicion()));
			posicion = fantasma.posicion();
		}
	}

	private Laberinto crearLaberintoCompletoDeNPosiciones(int cantidad){
		Dimension dim = new Dimension(cantidad, cantidad);
		Laberinto laberinto = new Laberinto(dim);
		LinkedList<Celda> celdas = new LinkedList<Celda>();
		for (int i = 0; i < cantidad ; i++){
			for (int j = 0 ; j < cantidad ; j++ ){
				Posicion posicion = new Posicion(i, j);
				Celda celda = new Celda(posicion);
				laberinto.agregarCelda(celda);
				celdas.add(celda);
			}
		}
		for (Celda celda1 : celdas) {
			for (Celda celda2 : celdas) {
				Posicion pos1 = celda1.posicion();
				Posicion pos2 = celda2.posicion();
				if (pos1.getCoordenadaX() == pos2.getCoordenadaX()-1 && pos1.getCoordenadaY() == pos2.getCoordenadaY()){celda1.setCeldaDerecha(celda2);}
				else if (pos1.getCoordenadaX() == pos2.getCoordenadaX()+1 && pos1.getCoordenadaY() == pos2.getCoordenadaY()){celda1.setCeldaIzquierda(celda2);}
				else if (pos1.getCoordenadaY() == pos2.getCoordenadaY()+1 && pos1.getCoordenadaX() == pos2.getCoordenadaX()){celda1.setCeldaArriba(celda2);}
				else if (pos1.getCoordenadaY() == pos2.getCoordenadaY()-1 && pos1.getCoordenadaX() == pos2.getCoordenadaX()){celda1.setCeldaAbajo(celda2);}
			}
		}
		return laberinto;
    }
	
	private Fantasma crearFantasmaBuscadorNivelUno(Laberinto laberinto, Posicion posicion) {
		ConfiguracionTicks confTicks = new ConfiguracionTicks();
        Fantasma unFantasma = new Fantasma(laberinto,posicion,confTicks);
        unFantasma.setPersonalidad(new Buscador());
    	return unFantasma;
    }
	
	private int numeroAleatorio(int max) {
		return (int) Math.floor(max*Math.random());
	}
	
	private int numeroAleatorio(int min, int max) {
		return (int) Math.floor((max-min)*Math.random()+min);
	}
}
