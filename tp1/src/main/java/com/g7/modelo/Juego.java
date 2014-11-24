package com.g7.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

public class Juego extends Observable implements Runnable{
	private Collection<Escenario> escenarios;
	private PacMan pacman;
	private int nivel;

	public Juego(PacMan pacman){
		escenarios = new ArrayList<Escenario>();
		nivel = 1;
		this.pacman = pacman;
	}

	public Collection<Escenario> getEscenarios() {
		return escenarios;
	}

	public void addEscenario(Escenario escenario) {
		if(pacman != null)
			escenario.setPacMan(pacman);
		this.escenarios.add(escenario);
	}

	public PacMan getPacman() {
		return pacman;
	}

	public void setPacman(PacMan pacman) {
		this.pacman = pacman;
	}

	public void jugar(){
		while(!pacman.isFinJuego()){
			for(Escenario escenario : escenarios){
				escenario.ejecutarEscenario();
				if(!pacman.isFinJuego()){
					subirNivel();
				}
			}
		}
	}

	public void run() {
		jugar();		
	}

	public int nivel(){
		return this.nivel;
	}
	
	public void subirNivel(){
		this.nivel++;
		this.setChanged();
		this.notifyObservers(this);
	}

}
