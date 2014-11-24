package com.g7.modelo.direcciones;

import com.g7.modelo.laberinto.Celda;

public abstract class Direccion {
	
	public abstract Celda siguienteCelda(Celda celda);
	public abstract String toString();

}
