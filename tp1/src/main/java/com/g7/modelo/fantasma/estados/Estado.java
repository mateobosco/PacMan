package com.g7.modelo.fantasma.estados;

public abstract interface Estado {
	
	public abstract Estado estadoLuegoDeUnCiclo();
	public abstract Estado convertirAPresa();
	public abstract Estado morir();
	public abstract String getDescripcion();

}
