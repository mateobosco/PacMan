package com.g7.modelo.fantasma;

import java.util.Observable;

import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.SuperposicionesFactory;
import com.g7.modelo.direcciones.Derecha;
import com.g7.modelo.direcciones.Direccion;
import com.g7.modelo.direcciones.Movible;
import com.g7.modelo.fantasma.estados.Estado;
import com.g7.modelo.fantasma.estados.EstadosFantasma;
import com.g7.modelo.fantasma.estados.NivelUno;
import com.g7.modelo.fantasma.personalidades.Personalidad;
import com.g7.modelo.fantasma.personalidades.Zonzo;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.util.reflexion.ReflexionUtils;

public class Fantasma extends Observable implements Posicionable, Movible {
	
	private Posicion posicionActual;
	private Estado estadoActual;
	private Direccion direccionActual;
	private Laberinto laberinto;
	private String id;
	private Personalidad personalidad;
	static private boolean bolonComido;
	static private int fantasmasExistentes;
	static private int fantasmasPasadosAPresa;

	private float cantidadMovimientosRestantes;
	private float cantidadDeMovimientosEnUnTick;
	private ConfiguracionTicks conf;
	private int puntaje;
	
	public Fantasma(Laberinto laberinto, Posicion posicion, ConfiguracionTicks conf) {
		this("0",laberinto,posicion,conf);
	}
	
	public Fantasma(String id, Laberinto laberinto, Posicion posicion, ConfiguracionTicks conf) {
		this.posicionActual = posicion;
		this.estadoActual = new NivelUno();
		this.direccionActual = new Derecha();
		this.laberinto = laberinto;
		this.laberinto.agregarPosicionable(this);
		this.personalidad = new Zonzo();
		fantasmasExistentes ++;
		this.id = id;
		setBolonComido(false);
		this.conf = conf;
		this.cantidadDeMovimientosEnUnTick = this.conf.getCantidadDeMovimientosFantasmaEnUnTick(estadoActual);
		this.cantidadMovimientosRestantes = this.cantidadDeMovimientosEnUnTick;
		this.puntaje = 1;
	}
	
	public void vivir(Posicion posicion){
		if (bolonComido && fantasmasPasadosAPresa <= fantasmasExistentes) {
			this.estadoActual = this.estadoActual.convertirAPresa();
			fantasmasPasadosAPresa ++;
			if (fantasmasPasadosAPresa == fantasmasExistentes){
				setBolonComido(false);
				fantasmasPasadosAPresa = 0;
			}
		}
		while(this.cantidadMovimientosRestantes - 1 >= 0){
			if(!this.estadoActual.getDescripcion().equals(EstadosFantasma.MUERTO)){
				this.mover(posicion);
			}
			this.cantidadDeMovimientosEnUnTick = this.conf.getCantidadDeMovimientosFantasmaEnUnTick(estadoActual);
			this.cantidadMovimientosRestantes--;
			this.estadoActual = this.estadoActual.estadoLuegoDeUnCiclo();
			this.notifyObservers();
		}
		this.cantidadMovimientosRestantes += this.cantidadDeMovimientosEnUnTick;
	}
	
	public void convertirAPresa() {
		this.estadoActual = this.estadoActual.convertirAPresa();
		this.setChanged();
	}
	
	public void morir()	{
		this.estadoActual = this.estadoActual.morir();
		this.setPosicion(new Posicion(new Integer(this.laberinto.getInicioFantasma().substring(2, 4)),new Integer(this.laberinto.getInicioFantasma().substring(0, 2))));
		this.setChanged();
		this.notifyObservers();
	}
	
	public Posicion posicion()	{
		return this.posicionActual;
	}
	
	protected void mover(Posicion posicion) {
		this.posicionActual = this.personalidad.mover(this, posicion);
		this.setChanged();
	}

	public String estado() {
		return estadoActual.getDescripcion();
	}
	
	public static void setBolonComido(boolean estado) {
		bolonComido = estado;
	}

	public void setDireccionActual(Direccion direccionActual) {
		this.direccionActual = direccionActual;
	}

	public String getId() {
		return id;
	}
	
	public Estado getEstado(){
		return this.estadoActual;
	}

	public Direccion direccion(){
		return this.direccionActual;
	}
	
	public void superponer(Posicionable posicionable, Celda celdaNueva){
		try{
			SuperposicionesFactory superPosiciones = new SuperposicionesFactory();
			ReflexionUtils.getMethod(superPosiciones.getClass(), "fantasmaOn"+posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1)).invoke(superPosiciones,this,posicionable,celdaNueva);
		}catch(Exception e){
			System.err.println("No se encontró el método fantasmaOn"+posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1));
		}
	}

	public Personalidad getPersonalidad() {
		return personalidad;
	}

	public void setPersonalidad(Personalidad personalidad) {
		this.personalidad = personalidad;
	}

	public Laberinto getLaberinto() {
		return laberinto;
	}

	public void vivir() {
		this.vivir(this.direccion().siguienteCelda(laberinto.getCelda(posicionActual)).posicion());
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	
	public void setPosicion(Posicion pos){
		this.laberinto.getCelda(this.posicionActual).eliminarElementoPosicionable(this);
		this.laberinto.getCelda(pos).addElementoPosicionable(this);
		this.posicionActual = pos;
		this.setChanged();
	}
	
	public void setLaberinto(Laberinto laberinto){
		this.laberinto = laberinto;
	}
}
