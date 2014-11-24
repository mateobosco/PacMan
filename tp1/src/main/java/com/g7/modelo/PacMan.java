package com.g7.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.g7.modelo.direcciones.Derecha;
import com.g7.modelo.direcciones.Direccion;
import com.g7.modelo.direcciones.Movible;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.util.reflexion.ReflexionUtils;

public class PacMan extends Observable implements Posicionable,Movible {

	private Posicion posicionActual;
	private Direccion direccionActual;
	private Laberinto laberinto;
	private boolean finJuego;
	private int puntaje;
	private float cantidadMovimientosRestantes;
	private float cantidadDeMovimientosEnUnTick;
	private ConfiguracionTicks conf;
	private int vidas;
	private String id;

	private Lock lock;
	
	public PacMan(ConfiguracionTicks conf){
		this.finJuego = false;
		this.puntaje = 0;
		this.direccionActual = new Derecha();
		this.conf = conf;
		this.cantidadDeMovimientosEnUnTick = this.conf.getCantidadDeMovimientosPacManEnUnTick();
		this.cantidadMovimientosRestantes = this.cantidadDeMovimientosEnUnTick;
		this.vidas = 3;
		this.id = "pacman";

		lock = new ReentrantLock();
	}
	
	public Posicion posicion() {
		return this.posicionActual;
	}

	public void setDireccion(Direccion direccion){
		lock.lock();
		this.direccionActual = direccion;
		this.setChanged();
		lock.unlock();
	}

	public void vivir(){
		while(this.cantidadMovimientosRestantes - 1 >= 0){
			if(!this.finJuego){
				mover();
			}
			this.cantidadMovimientosRestantes--;
			this.notifyObservers();
		}
		this.cantidadMovimientosRestantes += this.cantidadDeMovimientosEnUnTick;
	}

	public Direccion direccion(){
		return this.direccionActual;
	}

	public void superponer(Posicionable posicionable, Celda celdaNueva){
		try{
			SuperposicionesFactory superPosiciones = new SuperposicionesFactory();
			ReflexionUtils.getMethod(superPosiciones.getClass(), "pacManOn"+posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1)).invoke(superPosiciones,this,posicionable,celdaNueva);
		}catch(Exception e){
			System.err.println("No se encontró el método pacManOn"+posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1));
		}
	}

	public void morir() {
		this.vidas--;
		this.setChanged();
		if(this.vidas <= 0){
			this.finJuego = true;
			this.notifyObservers();
		}
	}

	public boolean isFinJuego() {
		return finJuego;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void addPuntaje(int puntaje) {
		this.puntaje += puntaje;
	}

	public int getVidas(){
		return this.vidas;
	}

	protected void mover(){
		lock.lock();
		Celda celdaVieja = this.laberinto.getCelda(this.posicionActual);
		Celda celdaNueva = this.direccion().siguienteCelda(celdaVieja);
		if (celdaNueva != null && celdaVieja.esVecino(celdaNueva)){
			celdaVieja.eliminarElementoPosicionable(this);
			celdaNueva.addElementoPosicionable(this);
			Collection<Posicionable> elementos = new ArrayList<Posicionable>();
			elementos.addAll(celdaNueva.getElementosPosicionable());
			for(Posicionable elemento : elementos){
				superponer(elemento,celdaNueva);
			}
		}
		this.posicionActual = celdaNueva.posicion();
		this.setChanged();
		lock.unlock();
	}

	public void copiar(PacMan otroPacman){
		this.posicionActual = otroPacman.posicion();
		this.direccionActual = otroPacman.direccion();
		this.puntaje = otroPacman.getPuntaje();
		this.vidas = otroPacman.getVidas();
		this.finJuego = otroPacman.isFinJuego();
		this.setChanged();
	}

	public void setPosicion(Posicion pos){
		this.laberinto.getCelda(pos).addElementoPosicionable(this);
		this.posicionActual = pos;
		Collection<Posicionable> elementos = new ArrayList<Posicionable>();
		elementos.addAll(laberinto.getCelda(this.posicionActual).getElementosPosicionable());
		for(Posicionable elemento : elementos){
			superponer(elemento,laberinto.getCelda(this.posicionActual));
		}
		this.setChanged();
	}

	public void setLaberinto(Laberinto laberinto){
		if(this.posicionActual != null && this.laberinto != null){
			this.laberinto.getCelda(this.posicionActual).eliminarElementoPosicionable(this);
		}
		this.laberinto = laberinto;
	}

	public String getId(){
		return this.id;
	}
	
	public void setVidas(int vidas){
		this.vidas = vidas;
	}

}
