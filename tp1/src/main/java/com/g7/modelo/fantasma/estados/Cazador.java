package com.g7.modelo.fantasma.estados;


public abstract class Cazador implements Estado {

	public Estado convertirAPresa()	{
		return new Presa();
	}
	
	public Estado morir() {
		return this;
	}
	
	public String getDescripcion() {
		return EstadosFantasma.CAZADOR;
	}
	
	public abstract String getDescripcionEspecifica();

}
