package com.g7.modelo;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import com.g7.modelo.Bolon;
import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Posicion;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestBolon {
	
	private static int ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos = 10;
	private static int ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres = 10;
	private static int ciclosHastaEvolucionDeCazadorNivelTresACazadorNivelCuatro = 10;
	
	@Test
	public void fantasmaEnEstadoCazadorNivelUnoBolonComido(){
		Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5), numeroAleatorioMAX(5));
		Bolon unBolon = new Bolon(laberinto,unaPosicion);
		Fantasma unFantasma = crearFantasmaEnNivelUno();
		
		unBolon.serComido();
		unFantasma.vivir(new Posicion(0,0));
        assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
	}
	
	@Test
	public void fantasmaEnEstadoCazadorNivelDosBolonComido(){
		Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5), numeroAleatorioMAX(5));
		Bolon unBolon = new Bolon(laberinto,unaPosicion);
		Fantasma unFantasma = crearFantasmaEnNivelDos();
		
		unBolon.serComido();
		unFantasma.vivir(new Posicion(0,0));
		assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
	}
	
	@Test
	public void fantasmaEnEstadoCazadorNivelTresBolonComido(){
		Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5), numeroAleatorioMAX(5));
		Bolon unBolon = new Bolon(laberinto,unaPosicion);
		Fantasma unFantasma = crearFantasmaEnNivelTres();
		
		unBolon.serComido();
		unFantasma.vivir(new Posicion(0,0));
		assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
	}
	
	@Test
	public void fantasmaEnEstadoCazadorNivelCuatroBolonComido(){
		Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5), numeroAleatorioMAX(5));
		Bolon unBolon = new Bolon(laberinto,unaPosicion);
		Fantasma unFantasma = crearFantasmaEnNivelCuatro();
		
		unBolon.serComido();
		unFantasma.vivir(new Posicion(0,0));
		assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
	}
	
	@Test
	public void fantasmaEnEstadoMuertoBolonComido(){
		Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5), numeroAleatorioMAX(5));
		Bolon unBolon = new Bolon(laberinto,unaPosicion);
		Fantasma unFantasma = crearFantasmaMuerto();
		
		unBolon.serComido();
		unFantasma.vivir(new Posicion(0,0));
        assertEquals(EstadosFantasma.MUERTO, unFantasma.estado() );
	}
	
	@Test
	public void dosFantasmasNivelUnoBolonComido(){
		Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
		Posicion unaPosicion = new Posicion(numeroAleatorioMAX(5), numeroAleatorioMAX(5));
		Bolon unBolon = new Bolon(laberinto,unaPosicion);
		Fantasma unFantasma = crearFantasmaEnNivelUno();
		Fantasma otroFantasma = crearFantasmaEnNivelUno();
		
		unBolon.serComido();
		unFantasma.vivir(new Posicion(0,0));
		otroFantasma.vivir(new Posicion(0,0));
        assertEquals(EstadosFantasma.PRESA, unFantasma.estado() );
        assertEquals(EstadosFantasma.PRESA, otroFantasma.estado() );
	}
	
	
	
    private Fantasma crearFantasmaMuerto() {
		Fantasma unFantasma = crearFantasmaEnNivelUno();        
		unFantasma.convertirAPresa();
        unFantasma.morir();
        return unFantasma;
    }
    
    private Fantasma crearFantasmaEnNivelUno() {
    	ConfiguracionTicks confTicks = new ConfiguracionTicks();
    	Laberinto laberinto = crearLaberintoCompletoDeNPosiciones(10);
        Fantasma unFantasma = new Fantasma(laberinto,new Posicion(numeroAleatorioMAX(5),numeroAleatorioMAX(5)),confTicks);
        return unFantasma;
    }
    
    private Fantasma crearFantasmaEnNivelDos(){
        Fantasma unFantasma = crearFantasmaEnNivelUno();
        for (int i = 0; i < TestBolon.ciclosHastaEvolucionDeCazadorNivelUnoACazadorNivelDos; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        return unFantasma;
    }
    
    private Fantasma crearFantasmaEnNivelTres(){
        Fantasma unFantasma = crearFantasmaEnNivelDos();
        for (int i = 0; i < TestBolon.ciclosHastaEvolucionDeCazadorNivelDosACazadorNivelTres; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        return unFantasma;
    }
    
    private Fantasma crearFantasmaEnNivelCuatro(){
        Fantasma unFantasma = crearFantasmaEnNivelTres();
        for (int i = 0; i < TestBolon.ciclosHastaEvolucionDeCazadorNivelTresACazadorNivelCuatro; i++){
            unFantasma.vivir(new Posicion(0,0));
        }
        return unFantasma;
    }
	
    @SuppressWarnings("unused")
	private int numeroAleatorio() {
		return (int) Math.round(100.0*Math.random());
	}
    
	private int numeroAleatorioMAX(int n) {
		return (int) Math.round(100.0*Math.random()) % n ;
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

}
