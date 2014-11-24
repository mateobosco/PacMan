package com.g7.modelo.laberinto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import com.g7.modelo.Posicion;

final class Vertice implements Comparable<Vertice>{
	public Celda celda;
	public int distancia;
	public Celda padre;
	public Posicion posicion;
	public Vertice(Celda celda, Celda padre, Posicion pos, int distancia){
		this.celda = celda;
		this.padre = padre;
		this.distancia = distancia;
		this.posicion = pos;
	}
	public int compareTo(Vertice otro) {
		return Integer.compare(distancia, otro.distancia);
	}
}

public class Dijkstra {
	
	private final static int INFINITO = Integer.MAX_VALUE; 

	
	
	public static Camino caminoMinimo(Matriz<Celda> celdas, Celda start, Celda end) {
		
		
		LinkedList<Vertice> vertices = new LinkedList<Vertice>();
		for(int i=0; i < celdas.getDimension().ancho() ; i++){
			for(int j=0; j < celdas.getDimension().alto() ; j++){
				Celda unaCelda = (Celda)celdas.at(new Posicion(i,j)); 
				if(unaCelda != null)
					if(unaCelda.getId().equals((start.getId())))
						vertices.add(new Vertice(start, null, start.posicion(), 0));
					else
						vertices.add(new Vertice(unaCelda, null, unaCelda.posicion(), INFINITO));
			}
		}
		
		PriorityQueue<Vertice> colaPrioridad = new PriorityQueue<Vertice>();
		Vertice source = new Vertice((Celda)start, null, start.posicion(), 0);
		colaPrioridad.add(source);
		
		while(!colaPrioridad.isEmpty()){
			Vertice u = colaPrioridad.poll();
			Collection<Celda> adyacentes = u.celda.getCeldasAdyacentes();
			Collection<Vertice> verticesAdy = new LinkedList<Vertice>();
			for(Celda celda : adyacentes){
				for(Vertice vertice : vertices)
					if(celda.getId().equals(vertice.celda.getId()))
						verticesAdy.add(vertice);
			}
			for(Vertice vertice : verticesAdy){
				if(vertice.distancia > u.distancia + 1){
					vertice.distancia = u.distancia + 1;
					vertice.padre = u.celda;
					colaPrioridad.add(vertice);
				}
			}
		}
		Camino camino = new Camino();
		List<Posicion> path = new ArrayList<Posicion>();
		Celda origen = end;
		while(origen!=null){
			for(Vertice vertice : vertices){
				if(origen != null && origen.posicion().esIgual(vertice.posicion)){
					path.add(vertice.posicion);
					origen = vertice.padre;
				}
			}
		}
		Collections.reverse(path);
		camino.addPosiciones(path);
		camino.siguientePosicion();
		return camino;
	}

}
