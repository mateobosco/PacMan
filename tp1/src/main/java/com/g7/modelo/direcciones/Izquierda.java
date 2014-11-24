package com.g7.modelo.direcciones;

import com.g7.modelo.laberinto.Celda;

public class Izquierda extends Direccion {

	@Override
	public Celda siguienteCelda(Celda celda) {
		if(celda.getCeldaIzquierda() != null)
			return celda.getCeldaIzquierda();
		else 
			return celda;
	}
	
	@Override
	public String toString(){
		return "izquierda";
	}

}
