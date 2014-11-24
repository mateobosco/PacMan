package com.g7.vista;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

import com.g7.modelo.Bolita;
import com.g7.modelo.Bolon;
import com.g7.modelo.Posicionable;

public class Celda implements Observer{

	private Collection<Rectangle> bordes;
	private Circle bola;
	private String id;
	private Collection<Text> texts;

	private final Duration SEC_15 = Duration.millis(15);

	public Celda(com.g7.modelo.laberinto.Celda celda){

		this.bordes = new ArrayList<Rectangle>();
		this.texts = new ArrayList<Text>();
		this.bola = new Circle();
		
		this.updateReference(celda);
	}
	
	public void updateReference(com.g7.modelo.laberinto.Celda celda){
		this.id = celda.getId();
		this.bola.setFill(Color.WHITE);
		this.bola.setOpacity(0);
		
		for(Posicionable posicionable : celda.getElementosPosicionable()){
			if(posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1).equals("Bolon")){
				this.bola.setRadius(VistaJuego.anchoCelda*0.2);
				this.bola.setOpacity(1);
				this.bola.setTranslateX(celda.posicion().getCoordenadaX()*VistaJuego.anchoCelda+VistaJuego.anchoCelda/2-VistaJuego.anchoCelda*0.2);
				this.bola.setTranslateY(celda.posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda+VistaJuego.altoCelda/2);
				((Bolon)posicionable).addObserver(this);
			}
			else if(posicionable.getClass().getName().substring(posicionable.getClass().getName().lastIndexOf('.')+1).equals("Bolita")){
				this.bola.setRadius(VistaJuego.anchoCelda*0.05);
				this.bola.setOpacity(1);
				this.bola.setTranslateX(celda.posicion().getCoordenadaX()*VistaJuego.anchoCelda+VistaJuego.anchoCelda/2-VistaJuego.anchoCelda*0.05);
				this.bola.setTranslateY(celda.posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda+VistaJuego.altoCelda/2);
				((Bolita)posicionable).addObserver(this);
			}
		}
		
		if(celda.getCeldaAbajo() == null){
			Rectangle rec = new Rectangle();
			rec.setTranslateX(celda.posicion().getCoordenadaX()*VistaJuego.anchoCelda);
			rec.setTranslateY(celda.posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda+VistaJuego.altoCelda);
			rec.setWidth(VistaJuego.anchoCelda);
			rec.setHeight(2);
			rec.setFill(Color.BLUE);
			this.bordes.add(rec);
		}
		if(celda.getCeldaArriba() == null){
			Rectangle rec = new Rectangle();
			rec.setTranslateX(celda.posicion().getCoordenadaX()*VistaJuego.anchoCelda);
			rec.setTranslateY(celda.posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda);
			rec.setWidth(VistaJuego.anchoCelda);
			rec.setHeight(2);
			rec.setFill(Color.BLUE);
			this.bordes.add(rec);
		}
		if(celda.getCeldaDerecha() == null){
			Rectangle rec = new Rectangle();
			rec.setTranslateX(celda.posicion().getCoordenadaX()*VistaJuego.anchoCelda+VistaJuego.anchoCelda);
			rec.setTranslateY(celda.posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda);
			rec.setWidth(2);
			rec.setHeight(VistaJuego.altoCelda);
			rec.setFill(Color.BLUE);
			this.bordes.add(rec);
		}
		if(celda.getCeldaIzquierda() == null){
			Rectangle rec = new Rectangle();
			rec.setTranslateX(celda.posicion().getCoordenadaX()*VistaJuego.anchoCelda);
			rec.setTranslateY(celda.posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda);
			rec.setWidth(2);
			rec.setHeight(VistaJuego.altoCelda);
			rec.setFill(Color.BLUE);
			this.bordes.add(rec);
		}
	}

	public Collection<Shape> getShapes() {
		Collection<Shape> shapes = new ArrayList<Shape>();
		shapes.addAll(bordes);
		shapes.add(this.bola);
		return shapes;
	}

	public void update(Observable o, Object data){
		Platform.runLater(new Runnable() {
            public void run() {
            	FadeTransition ft = new FadeTransition(SEC_15);
        		ft.setNode(bola);
        		ft.setToValue(0.0f);
        		ft.play();
            }
        });
	}

	public String getId(){
		return id;
	}

	public Collection<Text> getTexts(){
		return this.texts;
	}
	
	public void borrarCelda(){
		for(Shape shape : this.getShapes()){
			FadeTransition ft = new FadeTransition(Duration.millis(1));
			ft.setNode(shape);
			ft.setToValue(0.0f);
			ft.play();
		}
	}

}
