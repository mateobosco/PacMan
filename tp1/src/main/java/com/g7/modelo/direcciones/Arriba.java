package com.g7.modelo.direcciones;

import com.g7.modelo.laberinto.Celda;

public class Arriba extends Direccion {

	@Override
	public Celda siguienteCelda(Celda celda) {
		if(celda.getCeldaArriba() != null)
			return celda.getCeldaArriba();
		else 
			return celda;
	}
	
	@Override
	public String toString(){
		return "arriba";
	}

}
