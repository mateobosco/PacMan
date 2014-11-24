package com.g7.controlador;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.g7.controlador.xml.reader.PersonajesReader;
import com.g7.controlador.xml.writer.PersonajesTickWriter;
import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.PacMan;
import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestPersonajesTickWriter {

	private StringBuilder input;
	private Collection<Posicionable> personajes;
	private Laberinto laberinto;

	@Test
	public void outputTiene489Caracteres() {
		
		OutputStream outputStream = new ByteArrayOutputStream();
		PersonajesTickWriter.write(personajes,outputStream);
		assertEquals(489, outputStream.toString().length());
	}

	@Before
	public void setUp() {
		input = new StringBuilder();
		input.append("<juego   posicionPacman=\"0409\" fila=\"04\" columna=\"09\" sentido=\"izquierda\" puntaje=\"0\" finJuego=\"false\">");
		input.append("<fantasma id=\"1\" nodo=\"1010\" fila=\"10\" columna=\"10\" sentido=\"arriba\" personalidad=\"zonzo\" estado=\"cazador\" />");
		input.append("<fantasma id=\"2\" nodo=\"1009\" fila=\"10\" columna=\"09\" sentido=\"arriba\" personalidad=\"zonzo\" estado=\"cazador\" />");
		input.append("<fantasma id=\"3\" nodo=\"1008\" fila=\"10\" columna=\"08\" sentido=\"izquierda\" personalidad=\"zonzo\" estado=\"cazador\" />");
		input.append("</juego>");
		
		laberinto = crearLaberintoCompletoDeNPosiciones(11);
		
		PacMan pacman = new PacMan(new ConfiguracionTicks());
		personajes = PersonajesReader.read(new ByteArrayInputStream(input.toString().getBytes()), laberinto, pacman);
		pacman.setLaberinto(laberinto);
		pacman.setPosicion(new Posicion(0,0));
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
