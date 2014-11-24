package com.g7.vista;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.g7.controlador.xml.reader.LaberintoReader;
import com.g7.controlador.xml.reader.PersonajesReader;
import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Escenario;
import com.g7.modelo.Juego;
import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.direcciones.Direccion;
import com.g7.modelo.direcciones.DireccionesFactory;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.util.reflexion.ReflexionUtils;



public class VistaJuego extends Application implements Observer{	

	public static final int REFRESH_TIME = 100;
	public static final int INITIAL_TIME = 1000;
	public static int anchoCelda = 20;
	public static int altoCelda = 20;
	
	public static final int anchoPantalla = 380;
	public static final int altoPantalla = 440;
	
	public static final double fontSize = 18;
	
	private PacMan pacman;
	private Collection<Fantasma> fantasmas;
	private Collection<Fruta> frutas;
	private Collection<com.g7.vista.Celda> celdas;
	private Juego juego;
	private String vidasText;
	private Text vidas;
	private String puntosText;
	private Text puntos;
	private String nivelText;
	private Text nivel;
	private Text gameOver;
	private boolean actualizarEscenario;


	public static void main(String[] args) {
		launch();
	}

	public VistaJuego(){
		this.pacman = new PacMan();
		this.fantasmas = new ArrayList<Fantasma>();
		this.frutas = new ArrayList<Fruta>();
		this.celdas = new ArrayList<com.g7.vista.Celda>();
		this.vidasText = "Vidas: ";
		this.vidas = new Text();
		this.puntosText = "Puntos: ";
		this.puntos = new Text();
		this.nivelText = "Nivel: ";
		this.nivel = new Text();
		this.gameOver = new Text("Game Over");
		this.actualizarEscenario = true;
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("PacMan Grupo7");
		primaryStage.setResizable(false);
		this.juego = crearJuego();
		StackPane root = new StackPane();
		root.setAlignment(Pos.TOP_LEFT);
		this.gameOver.setOpacity(0);

		root.getChildren().add(vidas);
		root.getChildren().add(this.puntos);
		root.getChildren().add(this.nivel);
				
        KeyFrame oneFrame = new KeyFrame(Duration.millis(VistaJuego.REFRESH_TIME),
            new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if(actualizarEscenario){
						for(Escenario escenario : juego.getEscenarios()){
							if(escenario.isEjecutando()){
								VistaJuego.anchoCelda = Integer.parseInt(escenario.getLaberinto().getNodoAncho());
								VistaJuego.altoCelda = Integer.parseInt(escenario.getLaberinto().getNodoAlto());
								{
									vidas.setTranslateX(0);
									vidas.setTranslateY(0);
									vidas.setFont(Font.font("System",FontWeight.BOLD,VistaJuego.fontSize));
									vidas.setFill(Color.WHITE);
								}
								{
									puntos.setTranslateX(VistaJuego.anchoPantalla*0.4);
									puntos.setTranslateY(0);
									puntos.setFont(Font.font("System",FontWeight.BOLD,VistaJuego.fontSize));
									puntos.setFill(Color.WHITE);
								}
								{
									nivel.setTranslateX(VistaJuego.anchoPantalla-VistaJuego.anchoPantalla/5);
									nivel.setTranslateY(0);
									nivel.setFont(Font.font("System",FontWeight.BOLD,VistaJuego.fontSize));
									nivel.setFill(Color.WHITE);
								}
								
								for(Iterator<com.g7.vista.Celda> it = celdas.iterator(); it.hasNext();){
									com.g7.vista.Celda celdaView = it.next();
									celdaView.borrarCelda();
									((StackPane)primaryStage.getScene().getRoot()).getChildren().removeAll(celdaView.getShapes());
									it.remove();
								}
								for(com.g7.modelo.laberinto.Celda celda : escenario.getLaberinto().getCeldas()){
									com.g7.vista.Celda celdaView = new com.g7.vista.Celda(celda);
									celdas.add(celdaView);
									((StackPane)primaryStage.getScene().getRoot()).getChildren().addAll(celdaView.getShapes());
								}
								{
									pacman.borrarPacMan();
									juego.getPacman().deleteObserver(pacman);
									((StackPane)primaryStage.getScene().getRoot()).getChildren().removeAll(pacman.getImageViews());
									pacman = new PacMan();
									juego.getPacman().addObserver(pacman);
									((StackPane)primaryStage.getScene().getRoot()).getChildren().addAll(pacman.getImageViews());
								}
								for(Iterator<Fantasma> it = fantasmas.iterator(); it.hasNext();){
									Fantasma fantasmaView = it.next();
									fantasmaView.borrarFantasma();
									((StackPane)primaryStage.getScene().getRoot()).getChildren().removeAll(fantasmaView.getImageViews());
									it.remove();
								}
								for(com.g7.modelo.fantasma.Fantasma fantasma : escenario.getFantasmas()){
									Fantasma fantasmaView = new Fantasma();
									fantasma.addObserver(fantasmaView);
									fantasmaView.update(fantasma, null);
									fantasmas.add(fantasmaView);
									((StackPane)primaryStage.getScene().getRoot()).getChildren().addAll(fantasmaView.getImageViews());
								}
								for(Iterator<Fruta> it = frutas.iterator(); it.hasNext();){
									Fruta frutaView = it.next();
									frutaView.borrarFruta();
									((StackPane)primaryStage.getScene().getRoot()).getChildren().removeAll(frutaView.getImageViews());
									it.remove();
								}
								for(com.g7.modelo.Fruta fruta : escenario.getFrutas()){
									Fruta frutaView = new Fruta();
									fruta.addObserver(frutaView);
									frutaView.update(fruta, null);
									frutas.add(frutaView);
									((StackPane)primaryStage.getScene().getRoot()).getChildren().addAll(frutaView.getImageViews());
								}/*
								primaryStage.setWidth(VistaJuego.anchoCelda*escenario.getLaberinto().getDimension().ancho());
								primaryStage.setHeight(VistaJuego.altoCelda*(escenario.getLaberinto().getDimension().alto()+1));*/
								((StackPane)primaryStage.getScene().getRoot()).getChildren().removeAll(gameOver);
								((StackPane)primaryStage.getScene().getRoot()).getChildren().addAll(gameOver);
							}
						}
					}
					actualizarEscenario = false;
				}
        }); // oneFrame

        TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).build().play();
        primaryStage.setScene(new Scene(root, VistaJuego.anchoPantalla, VistaJuego.altoPantalla, Color.BLACK));
		primaryStage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
			DireccionesFactory direcciones = new DireccionesFactory();
			public void handle(KeyEvent event) {
				try {
					juego.getPacman().setDireccion((Direccion) ReflexionUtils.getMethod(direcciones.getClass(), event.getCode().toString()).invoke(direcciones));
				} catch (Exception e) {
					//System.err.println("No se encontró el método "+event.getCode().toString());
				} 
			}
		});

		this.juego.getPacman().addObserver(this);
		this.juego.addObserver(this);
        
		
		Thread th = new Thread(this.juego);
		th.setDaemon(true);
		th.start();
		primaryStage.show();
	}

	public PacMan getPacMan(){
		return this.pacman;
	}

	public void update(Observable o, final Object data){
		Platform.runLater(new Runnable() {
            public void run() {
            	vidas.setText(vidasText+juego.getPacman().getVidas());
        		puntos.setText(puntosText+juego.getPacman().getPuntaje());
        		nivel.setText(nivelText+String.format("%02d", juego.nivel()));
        		if(data != null){
        			actualizarEscenario = true;
        		}
        		if(juego.getPacman().isFinJuego()){
					gameOver.setFont(Font.font("System",FontWeight.BOLD,2*VistaJuego.fontSize));
					gameOver.setTranslateX(VistaJuego.anchoPantalla/4);
					gameOver.setTranslateY(VistaJuego.altoPantalla*0.45);
					gameOver.setFill(Color.WHITE);
					gameOver.setOpacity(1);
        		}
            }
        });
	}
	
	private Juego crearJuego(){
		Juego juego = new Juego(new com.g7.modelo.PacMan(new ConfiguracionTicks()));

		juego.addEscenario(crearEscenario(juego.getPacman()));
		juego.addEscenario(leerEscenario(juego.getPacman()));
		
		return juego;
	}
	
	private Escenario crearEscenario(com.g7.modelo.PacMan pacman){
		Escenario escenario = new Escenario(crearLaberintoCompletoDeNxMPosicionesSinBolitas(6,6),pacman);
		for(com.g7.modelo.laberinto.Celda celda : escenario.getLaberinto().getCeldas()){
			new com.g7.modelo.Bolita(escenario.getLaberinto(), celda.posicion());
		}

		Collection<Posicionable> personajes = new ArrayList<Posicionable>();
					
		personajes.add(pacman);
		/*
		{
			com.g7.modelo.fantasma.Fantasma fantasma = new com.g7.modelo.fantasma.Fantasma(escenario.getLaberinto(), new Posicion(4,0), new ConfiguracionTicks());
			personajes.add(fantasma);
		}*/
		{
			com.g7.modelo.Fruta fruta = new com.g7.modelo.Fruta(escenario.getLaberinto(), new Posicion(2,2), new ConfiguracionTicks());
			personajes.add(fruta);
		}
		escenario.setPersonajes(personajes);
		return escenario;
	}
	
	private Escenario leerEscenario(com.g7.modelo.PacMan pacman){
		FileInputStream laberintoSimple = null;
		try {
			laberintoSimple = new FileInputStream("/home/mateo/workspace/tp/tp/tp1/src/main/java/com/g7/controlador/xml/reader/LaberintoSimple.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		FileInputStream personajesSimple = null;
		try {
			personajesSimple = new FileInputStream("/home/mateo/workspace/tp/tp/tp1/src/main/java/com/g7/controlador/xml/reader/PersonajesSimple.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Escenario escenario = new Escenario(LaberintoReader.read(laberintoSimple), pacman);
		
		Collection<Posicionable> personajes = PersonajesReader.read(personajesSimple, escenario.getLaberinto(), pacman);
		escenario.setPersonajes(personajes);
		
		return escenario;
	}

	private static Laberinto crearLaberintoCompletoDeNxMPosicionesSinBolitas(int ancho, int alto){
		Dimension dim = new Dimension(ancho, alto);
		Laberinto laberinto = new Laberinto(dim);
		LinkedList<Celda> celdas = new LinkedList<Celda>();
		for (int i = 0; i < ancho ; i++){
			for (int j = 0 ; j < alto ; j++ ){
				Posicion posicion = new Posicion(i, j);
				Celda celda = new Celda(posicion);
				laberinto.agregarCelda(celda);
				celdas.add(celda);
			}
		}
		for (Celda celda1 : celdas) {
			for (Celda celda2 : celdas) {
				Posicion pos1 = celda1.posicion();
				Posicion pos2 = celda2.posicion();
				if (pos1.getCoordenadaX() == pos2.getCoordenadaX()-1 && pos1.getCoordenadaY() == pos2.getCoordenadaY()){celda1.setCeldaDerecha(celda2);}
				else if (pos1.getCoordenadaX() == pos2.getCoordenadaX()+1 && pos1.getCoordenadaY() == pos2.getCoordenadaY()){celda1.setCeldaIzquierda(celda2);}
				else if (pos1.getCoordenadaY() == pos2.getCoordenadaY()+1 && pos1.getCoordenadaX() == pos2.getCoordenadaX()){celda1.setCeldaArriba(celda2);}
				else if (pos1.getCoordenadaY() == pos2.getCoordenadaY()-1 && pos1.getCoordenadaX() == pos2.getCoordenadaX()){celda1.setCeldaAbajo(celda2);}
			}
		}
		laberinto.setInicioFantasma(String.format("%02d", alto-1)+String.format("%02d", ancho-1));
		laberinto.setInicioPacman(String.format("%02d", 0)+String.format("%02d", 0));
		return laberinto;
	}

}
