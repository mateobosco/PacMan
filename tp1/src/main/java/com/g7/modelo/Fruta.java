package com.g7.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import com.g7.modelo.direcciones.Derecha;
import com.g7.modelo.direcciones.Direccion;
import com.g7.modelo.direcciones.Movible;
import com.g7.modelo.laberinto.Camino;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.util.reflexion.ReflexionUtils;

public class Fruta extends Observable implements Posicionable, Movible {
	
	private Posicion posicionActual;
	private Direccion direccionActual;
	private Laberinto laberinto;
	private String id;
	private boolean frutaComida;
	private Camino camino;

	private float cantidadMovimientosRestantes;
	private float cantidadDeMovimientosEnUnTick;
	private ConfiguracionTicks conf;
	private int puntaje;
	
	public Fruta(Laberinto laberinto, Posicion posicion, ConfiguracionTicks conf) {
		this("0",laberinto,posicion,conf);
	}
	
	public Fruta(String id, Laberinto laberinto, Posicion posicion, ConfiguracionTicks conf) {
		this.posicionActual = posicion;
		this.direccionActual = new Derecha();
		this.laberinto = laberinto;
		this.laberinto.agregarPosicionable(this);
		this.id = id;
		this.conf = conf;
		this.cantidadDeMovimientosEnUnTick = this.conf.getCantidadDeMovimientosFrutaEnUnTick();
		this.cantidadMovimientosRestantes = this.cantidadDeMovimientosEnUnTick;
		this.frutaComida = false;
		this.puntaje = 10;
		this.camino = this.laberinto.caminoAleatorio(this.posicionActual);
	}
	
	public void morir()	{
		this.frutaComida = true;
		this.setChanged();		
		this.notifyObservers();
	}
	
	public Posicion posicion()	{
		return this.posicionActual;
	}
	
	protected void mover() {
		if(this.camino.tamanio() == 0){
			this.camino = this.laberinto.caminoAleatorio(this.posicionActual);
		}
		Celda celdaVieja = this.laberinto.getCelda(this.posicionActual);
		Celda celdaNueva = this.laberinto.getCelda(this.camino.siguientePosicion());
		this.setDireccionActual(celdaVieja.getDireccionAdyacencia(celdaNueva));
		celdaVieja.eliminarElementoPosicionable(this);
		celdaNueva.addElementoPosicionable(this);
		Collection<Posicionable> elementos = new ArrayList<Posicionable>();
		elementos.addAll(celdaNueva.getElementosPosicionable());
		for(Posicionable elemento : elementos){
			this.superponer(elemento,celdaNueva);
		}
		this.posicionActual = celdaNueva.posicion();
		this.setChanged();
		this.notifyObservers();
	}

	public void setDireccionActual(Direccion direccionActual) {
		this.direccionActual = direccionActual;
	}

	public String getId() {
		return id;
	}

	public Direccion direccion(){
		return this.direccionActual;
	}
	
	public void superponer(Posicionable posicionable, Celda celdaNueva){
		try{
			SuperposicionesFactory superPosiciones = new SuperposicionesFactory();
			ReflexionUtils.getMethod(superPosiciones.getClass(), "frutaOn"+posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1)).invoke(superPosiciones,this,posicionable,celdaNueva);
		}catch(Exception e){
			System.err.println("No se encontró el método frutaOn"+posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1));
		}
	}

	public Laberinto getLaberinto() {
		return laberinto;
	}

	public void vivir(){
		while(this.cantidadMovimientosRestantes - 1 >= 0){
			if(!this.isComido()){
				this.mover();
			}
			this.cantidadMovimientosRestantes--;
		}
		this.cantidadMovimientosRestantes += this.cantidadDeMovimientosEnUnTick;
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	
	public boolean isComido(){
		return this.frutaComida;
	}
	
	public void setLaberinto(Laberinto laberinto){
		this.laberinto = laberinto;
	}
	
	public void revivir(){
		this.frutaComida = false;
	}

}
