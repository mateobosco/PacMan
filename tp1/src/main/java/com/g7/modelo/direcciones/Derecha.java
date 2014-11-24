package com.g7.modelo.direcciones;

import com.g7.modelo.laberinto.Celda;

public class Derecha extends Direccion {

	@Override
	public Celda siguienteCelda(Celda celda) {
		if(celda.getCeldaDerecha() != null)
			return celda.getCeldaDerecha();
		else 
			return celda;
	}
	
	@Override
	public String toString(){
		return "derecha";
	}

}
