package com.g7.modelo;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.g7.controlador.xml.reader.PersonajesReader;
import com.g7.controlador.xml.writer.LaberintoTickWriter;
import com.g7.controlador.xml.writer.PersonajesTickWriter;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestJuego {

	private StringBuilder inputPacManTick;
	
	private Juego juego;
	
	@Test
	public void testJuegoUnaVezYElPacmanYaSuma59Puntos() {
		OutputStream outputStreamLaberinto = new ByteArrayOutputStream();
		OutputStream outputStreamPersonajes = new ByteArrayOutputStream();
		//Direccion direccion = PacmanTickReader.read(new ByteArrayInputStream(inputPacManTick.toString().getBytes()));
		juego.jugar();
		for(Escenario escenario : juego.getEscenarios()){
			LaberintoTickWriter.write(escenario.getLaberinto(), outputStreamLaberinto);
			PersonajesTickWriter.write(escenario.getPersonajes(), outputStreamPersonajes);
		}
		assertEquals(59,juego.getPacman().getPuntaje());
	}
	
	@Before
	public void setUp(){
		
		StringBuilder inputPersonajes = new StringBuilder();
		inputPersonajes.append("<juego   posicionPacman=\"0003\" fila=\"00\" columna=\"03\" sentido=\"izquierda\" puntaje=\"0\" finJuego=\"false\">");
		inputPersonajes.append("<fantasma id=\"1\" nodo=\"0010\" fila=\"00\" columna=\"10\" sentido=\"arriba\" personalidad=\"zonzo\" estado=\"cazador\" />");
		inputPersonajes.append("<fantasma id=\"2\" nodo=\"0000\" fila=\"00\" columna=\"00\" sentido=\"arriba\" personalidad=\"perezoso\" estado=\"cazador\" />");
		inputPersonajes.append("<fantasma id=\"3\" nodo=\"0011\" fila=\"00\" columna=\"11\" sentido=\"izquierda\" personalidad=\"buscadorTemperamental\" estado=\"cazador\" />");
		inputPersonajes.append("</juego>");
		
		inputPacManTick = new StringBuilder();
		inputPacManTick.append("<juego>");
		inputPacManTick.append("<pacman direccion=\"izquierda\" />");
		inputPacManTick.append("</juego>");
		
		juego = new Juego(new PacMan(new ConfiguracionTicks()));
		
		juego.addEscenario(new Escenario(crearLaberintoCompletoDeNxMPosicionesSinBolitas(15,1),juego.getPacman()));
		for(Escenario escenario : juego.getEscenarios()){
			for(Celda celda : escenario.getLaberinto().getCeldas()){
				ponerBolitaEnCelda(escenario.getLaberinto(), celda);
			}
			Collection<Posicionable> personajes = PersonajesReader.read(new ByteArrayInputStream(inputPersonajes.toString().getBytes()), escenario.getLaberinto(),juego.getPacman());
			escenario.setPersonajes(personajes);
		}
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
		Celda celdaFinal = laberinto.getCelda(new Posicion(ancho-1,0));
		celdaInicial.setCeldaIzquierda(celdaFinal);
		celdaFinal.setCeldaDerecha(celdaInicial);
		return laberinto;
    }

	private void ponerBolitaEnCelda(Laberinto laberinto, Celda celda){
		new Bolita(laberinto,celda.posicion());
	}
}
