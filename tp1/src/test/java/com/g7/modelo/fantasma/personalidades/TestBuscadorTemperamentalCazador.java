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

public class TestBuscadorTemperamentalCazador {
	
	private static int ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos = 10;
	private static int ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres = 10;

	@Before
	public void setUp() throws Exception {
		Configuracion.getConfiguracion();
	}

	@Test
	public void fantasmaBuscadorTemperamentalNivelUnoNoVePacManYSeMueve(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(15),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(14,15),numeroAleatorio(14,15)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelUno(laberinto, new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Posicion posicion = fantasma.posicion();
		for(int i = 0; i < 5 ; i++){
			fantasma.vivir(pacman.posicion());
			assertFalse(posicion.esIgual(fantasma.posicion()));
			posicion = fantasma.posicion();
		}
	}
	
	@Test
	public void fantasmaBuscadorTemperamentalNivelUnoVePacManYSeAcerca(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(15),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelUno(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(2,3)));
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
	public void fantasmaBuscadorTemperamentalNivelUnoDejaDeVerAlPacManYSeAcercaALaUltimaPosicionPacMan(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelUno(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(2,3)));
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
	public void fantasmaBuscadorTemperamentalNivelUnoLlegaALaUltimaPosicionPacManYSeSigueMoviendo(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelUno(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(2,3)));
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
	
	@Test
	public void fantasmaBuscadorTemperamentalNivelDosNoVePacManYSeMueve(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(19,20),numeroAleatorio(19,20)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelDos(laberinto, new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Posicion posicion = fantasma.posicion();
		for(int i = 0; i < 2 ; i++){
			fantasma.vivir(pacman.posicion());
			assertFalse(posicion.esIgual(fantasma.posicion()));
			posicion = fantasma.posicion();
		}
	}
	
	@Test
	public void fantasmaBuscadorTemperamentalNivelDosVePacManYSeAcerca(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelDos(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(2,3)));
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
	public void fantasmaBuscadorTemperamentalNivelDosDejaDeVerAlPacManYSeAcercaALaUltimaPosicionPacMan(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelDos(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(2,3)));
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
	public void fantasmaBuscadorTemperamentalNivelDosLlegaALaUltimaPosicionPacManYSeSigueMoviendo(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelDos(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(3,4)));
		Camino camino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
		Posicion ultimaPosicionPacMan = pacman.posicion();
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
		pacman.vivir();
		pacman.vivir();
		pacman.vivir();
		pacman.setDireccion(new Abajo());
		camino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
		fantasma.vivir(pacman.posicion());
		while(fantasma.getPersonalidad().veoPacMan(laberinto, fantasma.posicion(), ultimaPosicionPacMan)){
			fantasma.vivir(ultimaPosicionPacMan);
			Camino otroCamino = laberinto.caminoMinimoEntre(fantasma.posicion(), ultimaPosicionPacMan);
			assertTrue(camino.tamanio()>=otroCamino.tamanio());
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
	
	@Test
	public void fantasmaBuscadorTemperamentalNivelTresNoVePacManYSeMueve(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(19,20),numeroAleatorio(19,20)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelTres(laberinto, new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Posicion posicion = fantasma.posicion();
		for(int i = 0; i < 5 ; i++){
			fantasma.vivir(pacman.posicion());
			assertFalse(posicion.esIgual(fantasma.posicion()));
			posicion = fantasma.posicion();
		}
	}
	
	@Test
	public void fantasmaBuscadorTemperamentalNivelTresVePacManYSeAcerca(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelTres(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(3,4)));
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
	public void fantasmaBuscadorTemperamentalNivelTresDejaDeVerAlPacManYSeAcercaALaUltimaPosicionPacMan(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelTres(laberinto, new Posicion(numeroAleatorio(2,3),numeroAleatorio(3,4)));
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
	public void fantasmaBuscadorTemperamentalNivelTresLlegaALaUltimaPosicionPacManYSeSigueMoviendo(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(25),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(numeroAleatorio(1),numeroAleatorio(1)));
		Laberinto laberinto = escenario.getLaberinto();
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelTres(laberinto, new Posicion(3,4));
		Camino camino = laberinto.caminoMinimoEntre(fantasma.posicion(), pacman.posicion());
		Posicion ultimaPosicionPacMan = pacman.posicion();
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
		pacman.vivir();
		pacman.vivir();
		pacman.vivir();
		pacman.setDireccion(new Abajo());
		camino = laberinto.caminoMinimoEntre(fantasma.posicion(), ultimaPosicionPacMan);
		fantasma.vivir(ultimaPosicionPacMan);
		while(fantasma.getPersonalidad().veoPacMan(laberinto, fantasma.posicion(), ultimaPosicionPacMan)){
			fantasma.vivir(ultimaPosicionPacMan);
			Camino otroCamino = laberinto.caminoMinimoEntre(fantasma.posicion(), ultimaPosicionPacMan);
			assertTrue(camino.tamanio()>=otroCamino.tamanio());
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
	
	private Fantasma crearFantasmaBuscadorTemperamentalNivelUno(Laberinto laberinto, Posicion posicion) {
		ConfiguracionTicks confTicks = new ConfiguracionTicks();
        Fantasma unFantasma = new Fantasma(laberinto,posicion,confTicks);
        unFantasma.setPersonalidad(new BuscadorTemperamental());
    	return unFantasma;
    }
	
	private Fantasma crearFantasmaBuscadorTemperamentalNivelDos(Laberinto laberinto, Posicion posicion){
		Celda unaCelda = laberinto.getCelda(posicion);
		Celda otraCelda = unaCelda.getCeldaDerecha();
        Fantasma unFantasma = crearFantasmaBuscadorTemperamentalNivelUno(laberinto,posicion);
        for (int i = 0; i < ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos; i+=2){
        	unFantasma.vivir(otraCelda.posicion());
        	unFantasma.vivir(unaCelda.posicion());
        }
    	return unFantasma;
    }
	
	private Fantasma crearFantasmaBuscadorTemperamentalNivelTres(Laberinto laberinto, Posicion posicion){
		Celda unaCelda = laberinto.getCelda(posicion);
		Celda otraCelda = unaCelda.getCeldaDerecha();
        Fantasma unFantasma = crearFantasmaBuscadorTemperamentalNivelDos(laberinto,posicion);
        for (int i = 0; i < ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres; i+=2){
        	unFantasma.vivir(otraCelda.posicion());
        	unFantasma.vivir(unaCelda.posicion());
        }
    	return unFantasma;
    }
	
	private int numeroAleatorio(int max) {
		return (int) Math.floor(max*Math.random());
	}
	
	private int numeroAleatorio(int min, int max) {
		return (int) Math.floor((max-min)*Math.random()+min);
	}
}
