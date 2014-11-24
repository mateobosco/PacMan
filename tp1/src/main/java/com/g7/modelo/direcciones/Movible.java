package com.g7.modelo.direcciones;

import com.g7.modelo.Posicionable;
import com.g7.modelo.laberinto.Celda;

public interface Movible extends Posicionable {
	void vivir();
	Direccion direccion();
	void superponer(Posicionable posicionable, Celda celdaNueva);
}