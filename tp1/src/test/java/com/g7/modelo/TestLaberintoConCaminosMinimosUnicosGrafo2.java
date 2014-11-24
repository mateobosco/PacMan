package com.g7.modelo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.g7.modelo.Posicion;
import com.g7.modelo.laberinto.Camino;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;

public class TestLaberintoConCaminosMinimosUnicosGrafo2 {

	@Test
	public void caminoEntreEyBTieneDosPosiciones() {

		Camino caminoMinimo = this.laberinto.caminoMinimoEntre(this.celdaE.posicion(),this.celdaB.posicion());
		
		assertTrue(caminoMinimo.tamanio() == 2);
	}
	
	@Test
	public void caminoMinimoEntreEyB() {

		Camino caminoMinimo = this.laberinto.caminoMinimoEntre(this.celdaE.posicion(),this.celdaB.posicion());
		
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaD.posicion()));
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaB.posicion()));

	}
	
	@Test
	public void caminoMinimoEntreAyFTieneTresPosiciones() {

		Camino caminoMinimo = laberinto.caminoMinimoEntre(this.celdaA.posicion(),this.celdaF.posicion());
		
		assertTrue(caminoMinimo.tamanio() == 3);
	}
	
	@Test
	public void caminoMinimoEntreAyF() {

		Camino caminoMinimo = laberinto.caminoMinimoEntre(this.celdaA.posicion(),this.celdaF.posicion());
		
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaD.posicion()));
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaE.posicion()));
		assertTrue(caminoMinimo.siguientePosicion().esIgual(this.celdaF.posicion()));

	}

	private Laberinto laberinto;
	
	private Celda celdaA;
	private Celda celdaB;
	private Celda celdaC;
	private Celda celdaD;
	private Celda celdaE;
	private Celda celdaF;
	
	@Before
	public void setUp(){
		
		this.laberinto = new Laberinto(new Dimension(10,10));
		celdaA = new Celda(new Posicion(1,1));
		celdaB = new Celda(new Posicion(1,2));
		celdaC = new Celda(new Posicion(2,1));
		celdaD = new Celda(new Posicion(2,2));
		celdaE = new Celda(new Posicion(1,3));
		celdaF = new Celda(new Posicion(2,3));
		
		celdaA.setCeldaAbajo(celdaD);
		celdaA.setCeldaArriba(celdaC);
		
		celdaB.setCeldaArriba(celdaC);
		celdaB.setCeldaAbajo(celdaD);
		celdaB.setCeldaDerecha(celdaF);
		
		celdaC.setCeldaArriba(celdaA);
		celdaC.setCeldaAbajo(celdaB);
		
		celdaD.setCeldaArriba(celdaA);
		celdaD.setCeldaAbajo(celdaB);
		celdaD.setCeldaDerecha(celdaE);
		
		celdaE.setCeldaAbajo(celdaD);
		celdaE.setCeldaArriba(celdaF);

		celdaF.setCeldaAbajo(celdaB);
		celdaF.setCeldaIzquierda(celdaE);
		
		this.laberinto.agregarCelda(celdaA);
		this.laberinto.agregarCelda(celdaB);
		this.laberinto.agregarCelda(celdaC);
		this.laberinto.agregarCelda(celdaD);
		this.laberinto.agregarCelda(celdaE);
		this.laberinto.agregarCelda(celdaF);
		
	}
	
}
