package com.g7.modelo.direcciones;

public class DireccionesFactory {
	
	public Derecha derecha(){
		return new Derecha();
	}
	
	public Derecha RIGHT(){
		return derecha();
	}

	public Izquierda izquierda(){
		return new Izquierda();
	}
	
	public Izquierda LEFT(){
		return izquierda();
	}
	
	public Arriba arriba(){
		return new Arriba();
	}
	
	public Arriba UP(){
		return arriba();
	}
	
	public Abajo abajo(){
		return new Abajo();
	}
	
	public Abajo DOWN(){
		return abajo();
	}
}
