package com.g7.modelo.direcciones;

import com.g7.modelo.laberinto.Celda;

public class Abajo extends Direccion {

	@Override
	public Celda siguienteCelda(Celda celda) {
		if(celda.getCeldaAbajo() != null)
			return celda.getCeldaAbajo();
		else 
			return celda;
	}
	
	@Override
	public String toString(){
		return "abajo";
	}

}
