package com.g7.modelo.laberinto;

import java.util.ArrayList;
import java.util.Collection;

import com.g7.modelo.Bolita;
import com.g7.modelo.Bolon;
import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;

public class Laberinto implements Cloneable{

	private Matriz<Celda> celdas;
	private String nodoAlto,nodoAncho,inicioPacman,inicioFantasma;

	public Laberinto(Dimension dimension){
		this.celdas = new Matriz<Celda>(dimension);
		this.inicioPacman = "0000";
		this.inicioFantasma = "0000";
		this.nodoAlto = "30";
		this.nodoAncho = "30";
	}
	
	public void agregarCelda(Celda celda) {
		this.celdas.add(celda);
	}

	public Camino caminoMinimoEntre(Posicion comienzo, Posicion fin) {
		if(((Celda)celdas.at(comienzo)).getCeldasAdyacentes().size() > 0){
			return Dijkstra.caminoMinimo(this.celdas, (Celda)celdas.at(comienzo), (Celda)celdas.at(fin));
		}
		Camino unCamino = new Camino();
		unCamino.addPosicion(comienzo);
		return unCamino;
	}
	
	public Celda getCelda(String id){
		for(int i=0; i < celdas.getDimension().ancho() ; i++){
			for(int j=0; j < celdas.getDimension().alto() ; j++){
				Celda unaCelda = (Celda)celdas.at(new Posicion(i,j)); 
				if(unaCelda != null)
					if(unaCelda.getId().equals(id))
						return unaCelda;
			}
		}
		return null;
	}
	
	public Dimension getDimension(){
		return this.celdas.getDimension();
	}
	
	public void agregarPosicionable(Posicionable posicionable){
		this.celdas.addElementoPosicionableEnCelda(posicionable);
	}
	
	public void eliminarPosicionable(Posicionable posicionable){
		this.celdas.eliminarElementoPosicionableEnCelda(posicionable);
	}

	public Collection<Celda> getCeldas(){
		Collection<Celda> celdasList = new ArrayList<Celda>();
		for(int i=0; i < celdas.getDimension().ancho() ; i++){
			for(int j=0; j < celdas.getDimension().alto() ; j++){
				Celda unaCelda = (Celda)celdas.at(new Posicion(i,j)); 
				if(unaCelda != null)
					celdasList.add(unaCelda);
			}
		}
		return celdasList;
	}

	public String getNodoAlto() {
		return nodoAlto;
	}

	public void setNodoAlto(String nodoAlto) {
		this.nodoAlto = nodoAlto;
	}

	public String getNodoAncho() {
		return nodoAncho;
	}

	public void setNodoAncho(String nodoAncho) {
		this.nodoAncho = nodoAncho;
	}

	public String getInicioPacman() {
		return inicioPacman;
	}

	public void setInicioPacman(String inicioPacman) {
		this.inicioPacman = inicioPacman;
	}

	public String getInicioFantasma() {
		return inicioFantasma;
	}

	public void setInicioFantasma(String inicioFantasma) {
		this.inicioFantasma = inicioFantasma;
	}
	
	public Celda getCelda(Posicion posicion){
		return (Celda)celdas.at(posicion);
	}

	public Camino caminoAleatorio(Posicion comienzo) {
		Celda unaCelda = generarCeldaAleatoria();
		Camino unCamino = null;
		if(unaCelda.getId() != ((Celda)celdas.at(comienzo)).getId()){
			unCamino = Dijkstra.caminoMinimo(this.celdas, (Celda)celdas.at(comienzo), unaCelda);
			if(unCamino.tamanio() > 0){
				return unCamino;
			}
		}
		for(Celda celda : ((Celda)celdas.at(comienzo)).getCeldasAdyacentes()){
			unCamino = Dijkstra.caminoMinimo(this.celdas, (Celda)celdas.at(comienzo), celda);
			break;
		}
		return unCamino;
	}
	
	public boolean isVacio(){
		boolean isVacio = true;
		for(Celda celda : getCeldas()){
			Collection<Posicionable> posicionables = new ArrayList<Posicionable>();
			posicionables.addAll(celda.getElementosPosicionable());
			for(Posicionable posicionable : posicionables){
				if(posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1).equals("Bolon")||
						posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1).equals("Bolita")||
						posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1).equals("Fruta")){
					isVacio = false;
					break;
				}
			}
		}
		return isVacio;
	}
	
	public Laberinto clone() throws CloneNotSupportedException{
		Laberinto laberintoClon = new Laberinto(this.celdas.getDimension());
		laberintoClon.setInicioFantasma(this.getInicioFantasma());
		laberintoClon.setInicioPacman(this.getInicioPacman());
		laberintoClon.setNodoAlto(this.getNodoAlto());
		laberintoClon.setNodoAncho(this.getNodoAlto());
		for(Celda celda : getCeldas()){
			Celda nuevaCelda = new Celda(celda.getId(), celda.posicion());
			laberintoClon.agregarCelda(nuevaCelda);
			for(Posicionable posicionable : celda.getElementosPosicionable()){
				if(posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1).equals("Bolon")){
					new Bolon(laberintoClon, nuevaCelda.posicion());
				}
				else if(posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1).equals("Bolita")){
					new Bolita(laberintoClon, nuevaCelda.posicion());
				}
			}
		}
		for(Celda celda : getCeldas()){
			if(celda.getCeldaAbajo() != null)laberintoClon.getCelda(celda.getId()).setCeldaAbajo(laberintoClon.getCelda(celda.getCeldaAbajo().getId()));
			if(celda.getCeldaArriba() != null)laberintoClon.getCelda(celda.getId()).setCeldaArriba(laberintoClon.getCelda(celda.getCeldaArriba().getId()));
			if(celda.getCeldaDerecha() != null)laberintoClon.getCelda(celda.getId()).setCeldaDerecha(laberintoClon.getCelda(celda.getCeldaDerecha().getId()));
			if(celda.getCeldaIzquierda() != null)laberintoClon.getCelda(celda.getId()).setCeldaIzquierda(laberintoClon.getCelda(celda.getCeldaIzquierda().getId()));
		}
		return laberintoClon;
	}
	
	private Celda generarCeldaAleatoria(){
		Posicion pos = new Posicion(numeroAleatorio(getDimension().ancho()),numeroAleatorio(getDimension().alto()));
		if((Celda)celdas.at(pos) != null){
			return (Celda)celdas.at(pos);
		}
		else
			return generarCeldaAleatoria();
	}
	
	private int numeroAleatorio(int n) {
		return (int) Math.round(100.0*Math.random()) % n;
	}
}
