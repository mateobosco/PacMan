package com.g7.modelo.fantasma.personalidades;

public class PersonalidadesFactory {
	
	public Personalidad zonzo(){
		return new Zonzo();
	}
	
	public Personalidad perezoso(){
		return new Perezoso();
	}
	
	public Personalidad buscador(){
		return new Buscador();
	}
	
	public Personalidad buscadorTemperamental(){
		return new BuscadorTemperamental();
	}

}
