package com.g7.modelo;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import com.g7.modelo.Bolon;
import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.PacMan;
import com.g7.modelo.Posicion;
import com.g7.modelo.direcciones.Derecha;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestPacManConBolasYBolones {

	@Test
	public void test(){
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNPosiciones(10),pacman);
		pacman.setLaberinto(escenario.getLaberinto());
		pacman.setPosicion(new Posicion(0,0));
		pacman.setDireccion(new Derecha());
		Fantasma fantasma = new Fantasma(escenario.getLaberinto(), new Posicion(6,6),new ConfiguracionTicks());
		@SuppressWarnings("unused")
		Bolon unBolon = new Bolon(escenario.getLaberinto(), new Posicion(1,0));
		
		pacman.vivir();
		fantasma.vivir(pacman.posicion());
		assertEquals(EstadosFantasma.PRESA, fantasma.estado() );
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
