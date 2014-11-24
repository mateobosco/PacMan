package com.g7.modelo.laberinto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.direcciones.Abajo;
import com.g7.modelo.direcciones.Arriba;
import com.g7.modelo.direcciones.Derecha;
import com.g7.modelo.direcciones.Direccion;
import com.g7.modelo.direcciones.Izquierda;

public class Celda implements Posicionable{

	private Posicion posicion;
	private String id;
	private Collection<Posicionable> elementosPosicionable;
	private Celda celdaDerecha;
	private Celda celdaIzquierda;
	private Celda celdaArriba;
	private Celda celdaAbajo;
	
	public Celda(String id, Posicion posicion) {
		this.posicion = posicion;
		this.setId(id);
		this.celdaAbajo = null;
		this.celdaArriba = null;
		this.celdaDerecha = null;
		this.celdaIzquierda = null;
		this.elementosPosicionable = new ArrayList<Posicionable>();
	}
	
	public Celda(Posicion posicion) {
		this(String.format("%02d", posicion.getCoordenadaY()).concat(String.format("%02d", posicion.getCoordenadaX())), posicion);
	}

	public Posicion posicion() {
		return this.posicion;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean esVecino(Celda celda){
		if (celda.equals(this.celdaAbajo)){ return true;}
		if (celda.equals(this.celdaArriba)){ return true;}
		if (celda.equals(this.celdaDerecha)){ return true;}
		if (celda.equals(this.celdaIzquierda)){ return true;}
		return false;
	}
	
	public void addElementoPosicionable(Posicionable posicionable){
		this.elementosPosicionable.add(posicionable);
	}
	
	public Collection<Posicionable> getElementosPosicionable(){
		return this.elementosPosicionable;
	}
	
	public boolean estaVacia(){
		return this.elementosPosicionable.isEmpty();
	}
	
	public boolean eliminarElementoPosicionable(Posicionable posicionable){
		boolean eliminado = false;
		for (Iterator<Posicionable> iterator = this.elementosPosicionable.iterator(); iterator.hasNext();) {
		    Posicionable elemento = iterator.next();
		    if (elemento.equals(posicionable)) {
		        iterator.remove();
		        eliminado = true;
		    }
		}
		return eliminado;
	}

	public Celda getCeldaDerecha() {
		return celdaDerecha;
	}

	public void setCeldaDerecha(Celda celdaDerecha) {
		this.celdaDerecha = celdaDerecha;
	}

	public Celda getCeldaIzquierda() {
		return celdaIzquierda;
	}

	public void setCeldaIzquierda(Celda celdaIzquierda) {
		this.celdaIzquierda = celdaIzquierda;
	}

	public Celda getCeldaArriba() {
		return celdaArriba;
	}

	public void setCeldaArriba(Celda celdaArriba) {
		this.celdaArriba = celdaArriba;
	}

	public Celda getCeldaAbajo() {
		return celdaAbajo;
	}

	public void setCeldaAbajo(Celda celdaAbajo) {
		this.celdaAbajo = celdaAbajo;
	}
	
	public Collection<Celda> getCeldasAdyacentes() {
		Collection<Celda> adyacentes = new LinkedList<Celda>();
		if (this.celdaAbajo != null){adyacentes.add(this.celdaAbajo);}
		if (this.celdaArriba != null){adyacentes.add(this.celdaArriba);}
		if (this.celdaDerecha != null){adyacentes.add(this.celdaDerecha);}
		if (this.celdaIzquierda != null){adyacentes.add(this.celdaIzquierda);}
		return adyacentes;
	}
	
	public Direccion getDireccionAdyacencia(Celda otraCelda){
		if(this.celdaAbajo != null && this.celdaAbajo.getId() == otraCelda.getId())
			return new Abajo();
		if(this.celdaArriba != null && this.celdaArriba.getId() == otraCelda.getId())
			return new Arriba();
		if(this.celdaDerecha != null && this.celdaDerecha.getId() == otraCelda.getId())
			return new Derecha();
		if(this.celdaIzquierda != null && this.celdaIzquierda.getId() == otraCelda.getId())
			return new Izquierda();
		return null;
	}
	
}
