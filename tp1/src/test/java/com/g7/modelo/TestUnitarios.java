package com.g7.modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.g7.modelo.Posicion;
import com.g7.modelo.direcciones.Arriba;
import com.g7.modelo.direcciones.Derecha;
import com.g7.modelo.direcciones.Izquierda;
import com.g7.modelo.laberinto.Celda;

/**
 * Test unitarios
 */
public class TestUnitarios {
	
	private static int unidadesDeMovimiento = 1;
	
	private int numeroAleatorio() {
		return (int) Math.round(100.0*Math.random());
	}
	
	@Test
	public void moverseALaDerecha() {
		Celda celdaInicial = new Celda(new Posicion(numeroAleatorio(), numeroAleatorio()));
		Celda otraCelda = new Celda(new Posicion(celdaInicial.posicion().getCoordenadaX()+TestUnitarios.unidadesDeMovimiento,celdaInicial.posicion().getCoordenadaY()));
		celdaInicial.setCeldaDerecha(otraCelda);
		Derecha direccionDerecha = new Derecha();
		Celda celdaFinal = direccionDerecha.siguienteCelda(celdaInicial);
		assertEquals(otraCelda.posicion().getCoordenadaX(), celdaFinal.posicion().getCoordenadaX());
	}

	@Test
	public void moverseALaIzquierda() {
		Celda celdaInicial = new Celda(new Posicion(numeroAleatorio(), numeroAleatorio()));
		Celda otraCelda = new Celda(new Posicion(celdaInicial.posicion().getCoordenadaX()-TestUnitarios.unidadesDeMovimiento,celdaInicial.posicion().getCoordenadaY()));
		celdaInicial.setCeldaIzquierda(otraCelda);
		Izquierda direccionIzquierda = new Izquierda();
		Celda celdaFinal = direccionIzquierda.siguienteCelda(celdaInicial);
		assertEquals(otraCelda.posicion().getCoordenadaX(), celdaFinal.posicion().getCoordenadaX());
	}
	
	public void moverseArriba() {
		Celda celdaInicial = new Celda(new Posicion(numeroAleatorio(), numeroAleatorio()));
		Celda otraCelda = new Celda(new Posicion(celdaInicial.posicion().getCoordenadaX(),celdaInicial.posicion().getCoordenadaY()+TestUnitarios.unidadesDeMovimiento));
		celdaInicial.setCeldaIzquierda(otraCelda);
		Arriba direccionArriba = new Arriba();
		Celda celdaFinal = direccionArriba.siguienteCelda(celdaInicial);
		assertEquals(otraCelda.posicion().getCoordenadaY(), celdaFinal.posicion().getCoordenadaY());
	}
	
	public void moverseAbajo() {
		Celda celdaInicial = new Celda(new Posicion(numeroAleatorio(), numeroAleatorio()));
		Celda otraCelda = new Celda(new Posicion(celdaInicial.posicion().getCoordenadaX(),celdaInicial.posicion().getCoordenadaY()-TestUnitarios.unidadesDeMovimiento));
		celdaInicial.setCeldaIzquierda(otraCelda);
		Arriba direccionArriba = new Arriba();
		Celda celdaFinal = direccionArriba.siguienteCelda(celdaInicial);
		assertEquals(otraCelda.posicion().getCoordenadaY(), celdaFinal.posicion().getCoordenadaY());
	}
}
