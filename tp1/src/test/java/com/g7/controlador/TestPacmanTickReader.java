package com.g7.controlador;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;

import com.g7.controlador.xml.reader.PacmanTickReader;
import com.g7.modelo.direcciones.Direccion;

public class TestPacmanTickReader {

	private StringBuilder input;

	@Test
	public void direccionPacmanEsArriba() {

		Direccion direccion = PacmanTickReader.read(new ByteArrayInputStream(input.toString().getBytes()));
		assertEquals("arriba", direccion.toString());
	}

	@Before
	public void setUp() {
		input = new StringBuilder();
		input.append("<juego>");
		input.append("<pacman direccion=\"arriba\" />");
		input.append("</juego>");
	}



}
