package com.g7.modelo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.vista.VistaJuego;

public class Escenario {
	private Laberinto laberinto;
	private PacMan pacman;
	private Collection<Fantasma> fantasmas;
	private Collection<Fruta> frutas;
	private Laberinto laberintoBase;
	private boolean ejecutando;
	
	public Escenario(Laberinto laberinto, PacMan pacman){
		this.fantasmas = new ArrayList<Fantasma>();
		this.frutas = new ArrayList<Fruta>();
		setLaberinto(laberinto);
		this.ejecutando = false;
		this.pacman = pacman;
	}
	
	private void setLaberinto(Laberinto laberinto){
		this.laberinto = laberinto;
	}
	
	public Collection<Fantasma> getFantasmas() {
		return fantasmas;
	}
	
	public Collection<Posicionable> getPersonajes(){
		Collection<Posicionable> personajes = new ArrayList<Posicionable>();
		personajes.add(this.pacman);
		personajes.addAll(this.fantasmas);
		personajes.addAll(this.frutas);
		return personajes;
	}
	
	public Laberinto getLaberinto(){
		return this.laberinto;
	}
	
	public void setPacMan(PacMan pacman){
		this.pacman = pacman;
	}
	
	public PacMan setPersonajes(Collection<Posicionable> personajes) {
		for(Posicionable personaje : personajes){
			if(personaje.getClass().getName().substring(personaje.getClass().getName().lastIndexOf('.')+1).equals("PacMan")){
				if(this.pacman == null)
					this.pacman = (PacMan)personaje;
				else{
					this.pacman.copiar((PacMan)personaje);
				}
					
			}
			else if(personaje.getClass().getName().substring(personaje.getClass().getName().lastIndexOf('.')+1).equals("Fantasma")){
				fantasmas.add((Fantasma)personaje);
			}
			else if(personaje.getClass().getName().substring(personaje.getClass().getName().lastIndexOf('.')+1).equals("Fruta")){
				frutas.add((Fruta)personaje);
			}
		}
		return this.pacman;
	}
	
	public void ejecutarEscenario(){
		this.ejecutando = true;
		long timeElapsed = 0l;
		pacman.setLaberinto(this.laberinto);
		pacman.setPosicion(new Posicion(new Integer(this.laberinto.getInicioPacman().substring(2, 4)),new Integer(this.laberinto.getInicioPacman().substring(0, 2))));
		for(Fruta fruta : this.frutas){
			fruta.revivir();
			fruta.setLaberinto(this.laberinto);
		}
		for(Fantasma fantasma : this.fantasmas){
			fantasma.setLaberinto(this.laberinto);
			fantasma.setPosicion(new Posicion(new Integer(this.laberinto.getInicioFantasma().substring(2, 4)),new Integer(this.laberinto.getInicioFantasma().substring(0, 2))));
			fantasma.resetEstadoFantasma();
		}
		try {
			this.laberintoBase = laberinto.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		while(!this.laberinto.isVacio() && !pacman.isFinJuego()){
			try {
				java.util.Date date= new java.util.Date();
				Timestamp initTime = new Timestamp(date.getTime());
				pacman.vivir();
				for(Fantasma fantasma : this.fantasmas){
					fantasma.vivir(pacman.posicion());
				}
				for(Fruta fruta : this.frutas){
					fruta.vivir();
				}
				Timestamp endTime = new Timestamp(date.getTime());
				timeElapsed = endTime.getTime() - initTime.getTime();
				if(VistaJuego.REFRESH_TIME - timeElapsed > 0)
					Thread.sleep(VistaJuego.REFRESH_TIME - timeElapsed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			this.setLaberinto(this.laberintoBase.clone());
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
		this.ejecutando = false;
	}
	
	public boolean isEjecutando(){
		return this.ejecutando;
	}
	
	public Collection<Fruta> getFrutas(){
		return this.frutas;
	}
	
	public PacMan getPacMan(){
		return this.pacman;
	}

}
