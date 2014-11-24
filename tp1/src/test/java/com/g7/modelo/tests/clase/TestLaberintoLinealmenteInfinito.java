package com.g7.modelo.tests.clase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.g7.modelo.Bolita;
import com.g7.modelo.Bolon;
import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Escenario;
import com.g7.modelo.PacMan;
import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.direcciones.Izquierda;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.Configuracion;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.fantasma.personalidades.BuscadorTemperamental;
import com.g7.modelo.fantasma.personalidades.Zonzo;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestLaberintoLinealmenteInfinito {
	private Configuracion conf;

	@Before
	public void setUp(){
		conf = Configuracion.getConfiguracion();
	}

	@Test
	public void fantasmaAvanzaSiempreDerechaYQuedaEnPosicionInicialMas1(){
		Laberinto laberinto = crearLaberintoCompletoDeNxMPosicionesSinBolitas(11,1);
		for(Celda celda : laberinto.getCeldas()){
			if(!celda.posicion().esIgual(new Posicion(0,0))){
				if(!celda.posicion().esIgual(new Posicion(4,0))){
					ponerBolitaEnCelda(laberinto, celda);
				}
				else{
					ponerBolonEnCelda(laberinto, celda);
				}
			}
		}
		ConfiguracionTicks confTicks = new ConfiguracionTicks();
		confTicks.setCantidadDeMovimientosFantasmaNivelDosEnUnTick(1.0f);
		confTicks.setCantidadDeMovimientosFantasmaNivelTresEnUnTick(1.0f);
		Fantasma fantasma = crearFantasmaZonzoNivelUno(laberinto, new Posicion(0,0),confTicks);
		for(int i = 0; i < 12 ; i++){
			fantasma.vivir(laberinto.getCelda(fantasma.posicion()).getCeldaDerecha().posicion());
		}
		assertTrue(fantasma.posicion().esIgual(new Posicion(1,0)));
		for(Celda celda : laberinto.getCeldas()){
			for(Posicionable elemento : celda.getElementosPosicionable()){
				if(celda.posicion().esIgual(new Posicion(4,0)))
					assertEquals("Bolon",elemento.getClass().getName().substring(elemento.getClass().getName().lastIndexOf('.')+1));
				else{
					if(!elemento.getClass().getName().substring(elemento.getClass().getName().lastIndexOf('.')+1).equals("Fantasma"))
						assertEquals("Bolita",elemento.getClass().getName().substring(elemento.getClass().getName().lastIndexOf('.')+1));
				}
			}
		}
	}
	
	@Test
	public void pacmanAvanzaSiempreDerechaYQuedaEnPosicionInicialMas2(){
		ConfiguracionTicks confTicks = new ConfiguracionTicks();
		confTicks.setCantidadDeMovimientosPacManEnUnTick(1.0f);
		PacMan pacman = new PacMan(confTicks);
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNxMPosicionesSinBolitas(11,1),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(2,0));
		Laberinto laberinto = escenario.getLaberinto();
		
		for(Celda celda : laberinto.getCeldas()){
			if(!celda.posicion().esIgual(new Posicion(2,0))){
				if(!celda.posicion().esIgual(new Posicion(4,0))){
					ponerBolitaEnCelda(laberinto, celda);
				}
				else{
					ponerBolonEnCelda(laberinto, celda);
				}
			}
		}
		
		Celda unaCelda = laberinto.getCelda(pacman.posicion()).getCeldaDerecha();
		unaCelda = unaCelda.getCeldaDerecha();
		Posicion posicion = unaCelda.posicion();
		for(int i = 0; i < 13 ; i++){
			pacman.vivir();
		}
		assertTrue(posicion.esIgual(pacman.posicion()));
		for(Celda celda : laberinto.getCeldas()){
			if(!celda.posicion().esIgual(posicion))
				assertTrue(celda.estaVacia());
		}
	}
	
	@Test
	public void fantasmaPersigueAPacmanAvanzandoSiempreIzquierdaYQuedaA3CeldasALaIzquierda(){
		ConfiguracionTicks confTicks = new ConfiguracionTicks();
		confTicks.setCantidadDeMovimientosPacManEnUnTick(1.0f);
		confTicks.setCantidadDeMovimientosFantasmaNivelUnoEnUnTick(0.5f);
		PacMan pacman = new PacMan(confTicks);
		pacman.setVidas(1);
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNxMPosicionesSinBolitas(11,1),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(0,0));
		Laberinto laberinto = escenario.getLaberinto();

		Fantasma fantasma = crearFantasmaZonzoNivelUno(laberinto, new Posicion(10,0), confTicks);
		Posicion posicionIzquierda = laberinto.getCelda(fantasma.posicion()).getCeldaIzquierda().posicion();
		for(int i = 0; i < 7 ; i++){
			pacman.vivir();
			fantasma.vivir(posicionIzquierda);
			posicionIzquierda = laberinto.getCelda(fantasma.posicion()).getCeldaIzquierda().posicion();
		}
		Posicion posicion = new Posicion(7,0);
		assertTrue(fantasma.posicion().esIgual(posicion));
		assertTrue(pacman.isFinJuego());
		for(Celda celda : laberinto.getCeldas()){
			for(Posicionable elemento : celda.getElementosPosicionable()){
				if(celda.posicion().esIgual(posicion))
					assertEquals(fantasma,elemento);
				else
					assertTrue(celda.estaVacia());
			}
		}
	}
	
	@Test
	public void fantasmaSeConvierteEnPresaSeEscapaDelPacmanYPacmanQuedaA2CeldasALaIzquierda(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNxMPosicionesSinBolitas(11,1),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(2,0));
		Laberinto laberinto = escenario.getLaberinto();
		
		for(Celda celda : laberinto.getCeldas()){
			if(celda.posicion().esIgual(new Posicion(4,0))){
				ponerBolonEnCelda(laberinto, celda);
			}
		}
		Fantasma fantasma = crearFantasmaBuscadorTemperamentalNivelUno(laberinto, new Posicion(0,0),new ConfiguracionTicks());
		for(int i = 0; i < 3 ; i++){
			pacman.vivir();
			fantasma.vivir(pacman.posicion());
			if(fantasma.estado().equals(EstadosFantasma.PRESA))
				pacman.setDireccion(new Izquierda());
		}
		Posicion posicion = new Posicion(0,0);
		assertEquals(EstadosFantasma.MUERTO, fantasma.estado());
		assertTrue(pacman.posicion().esIgual(posicion));
	}
	
	private Laberinto crearLaberintoCompletoDeNxMPosicionesSinBolitas(int ancho, int alto){
		Dimension dim = new Dimension(ancho, alto);
		Laberinto laberinto = new Laberinto(dim);
		LinkedList<Celda> celdas = new LinkedList<Celda>();
		for (int i = 0; i < ancho ; i++){
			for (int j = 0 ; j < alto ; j++ ){
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
		Celda celdaInicial = laberinto.getCelda(new Posicion(0,0));
		Celda celdaFinal = laberinto.getCelda(new Posicion(10,0));
		celdaInicial.setCeldaIzquierda(celdaFinal);
		celdaFinal.setCeldaDerecha(celdaInicial);
		return laberinto;
    }
	
	private void ponerBolitaEnCelda(Laberinto laberinto, Celda celda){
			new Bolita(laberinto,celda.posicion());
    }
	
	private void ponerBolonEnCelda(Laberinto laberinto, Celda celda){
		new Bolon(laberinto,celda.posicion());
	}
	
	private Fantasma crearFantasmaZonzoNivelUno(Laberinto laberinto, Posicion posicion, ConfiguracionTicks confTicks) {
        Fantasma unFantasma = new Fantasma(laberinto,posicion,confTicks);
        unFantasma.setPersonalidad(new Zonzo());
    	return unFantasma;
    }
	
	private Fantasma crearFantasmaBuscadorTemperamentalNivelUno(Laberinto laberinto, Posicion posicion, ConfiguracionTicks confTicks) {
		conf.setCiclosHastaEvolucionCazadorNivelUno(2);
		conf.setCiclosHastaEvolucionCazadorNivelDos(4);
        conf.setCiclosHastaEvolucionCazadorNivelTres(8);
        Fantasma unFantasma = new Fantasma(laberinto,posicion,confTicks);
        unFantasma.setPersonalidad(new BuscadorTemperamental());
    	return unFantasma;
    }

}
