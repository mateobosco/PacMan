package com.g7.modelo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.g7.modelo.Posicion;
import com.g7.modelo.laberinto.Camino;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestLaberintoConCaminosMinimosUnicosGrafo1 {

	@Test
	public void caminoEntreByDTieneTresPosiciones() {

		Camino caminoMinimo = this.laberinto.caminoMinimoEntre(this.celdaB.posicion(),this.celdaD.posicion());
		
		assertTrue(caminoMinimo.tamanio() == 3);
	}
	
	@Test
	public void caminoMinimoEntreByD() {

		Camino caminoMinimo = this.laberinto.caminoMinimoEntre(this.celdaB.posicion(),this.celdaD.posicion());
		
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaC.posicion()));
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaE.posicion()));
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaD.posicion()));

	}
	
	@Test
	public void caminoMinimoEntreCyATieneUnaPosiciones() {

		Camino caminoMinimo = laberinto.caminoMinimoEntre(this.celdaC.posicion(),this.celdaA.posicion());
		
		assertTrue(caminoMinimo.tamanio() == 1);
	}
	
	@Test
	public void caminoMinimoEntreCyA() {

		Camino caminoMinimo = laberinto.caminoMinimoEntre(this.celdaC.posicion(),this.celdaA.posicion());
		
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaA.posicion()));

	}
	
	@Test
	public void caminoMinimoEntreEyATieneDosPosiciones() {

		Camino caminoMinimo = laberinto.caminoMinimoEntre(this.celdaE.posicion(),this.celdaA.posicion());
		
		assertTrue(caminoMinimo.tamanio() == 2);
	}
	
	@Test
	public void caminoMinimoEntreEyA() {

		Camino caminoMinimo = laberinto.caminoMinimoEntre(this.celdaE.posicion(),this.celdaA.posicion());
		
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaC.posicion()));
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaA.posicion()));

	}

	private Laberinto laberinto;
	
	private Celda celdaA;
	private Celda celdaB;
	private Celda celdaC;
	private Celda celdaD;
	private Celda celdaE;
	
	@Before
	public void setUp(){
		
		Dimension dim = new Dimension(10,10);
		this.laberinto = new Laberinto(dim);
		celdaA = new Celda(new Posicion(1,1));
		celdaB = new Celda(new Posicion(1,2));
		celdaC = new Celda(new Posicion(1,3));
		celdaD = new Celda(new Posicion(1,4));
		celdaE = new Celda(new Posicion(1,5));
		
		celdaA.setCeldaDerecha(celdaB);
		celdaA.setCeldaIzquierda(celdaC);
		
		celdaB.setCeldaDerecha(celdaA);
		celdaB.setCeldaIzquierda(celdaC);
		
		celdaC.setCeldaDerecha(celdaA);
		celdaC.setCeldaIzquierda(celdaB);
		celdaC.setCeldaAbajo(celdaE);
		
		celdaD.setCeldaDerecha(celdaE);
		
		celdaE.setCeldaAbajo(celdaC);
		celdaE.setCeldaIzquierda(celdaD);
		
		this.laberinto.agregarCelda(celdaA);
		this.laberinto.agregarCelda(celdaB);
		this.laberinto.agregarCelda(celdaC);
		this.laberinto.agregarCelda(celdaD);
		this.laberinto.agregarCelda(celdaE);
		
	}
	
}
