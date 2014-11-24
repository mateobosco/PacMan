package com.g7.modelo.laberinto;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.g7.modelo.Posicion;

public class Camino {

	private Queue<Posicion> posiciones;
	
	public Camino(){
		this.posiciones = new LinkedList<Posicion>();
	}
	
	public int tamanio() {
		return  posiciones.size();
	}

	public Posicion siguientePosicion() {
		return posiciones.poll();
	}
	
	public void addPosicion(Posicion pos){
		this.posiciones.add(pos);
	}
	
	public void addPosiciones(List<Posicion> positions){
		this.posiciones.addAll(positions);
	}
	
	public void vaciar(){
		this.posiciones.clear();
	}

}
