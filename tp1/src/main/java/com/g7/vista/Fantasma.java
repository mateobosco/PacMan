package com.g7.vista;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import com.g7.modelo.fantasma.estados.EstadosFantasma;

public class Fantasma implements Observer{

	private ImageView imgFantasmaView;
	private ImageView imgFantasmaPresaView;

	private final Duration SEC_15 = Duration.millis(15);

	public Fantasma(){

		Image imgFantasma = new Image("com/g7/vista/Fantasma.png", VistaJuego.anchoCelda*0.9, VistaJuego.altoCelda*0.9, true, true, false);
		Image imgFantasmaPresa = new Image("com/g7/vista/FantasmaPresa.png", VistaJuego.anchoCelda*0.9, VistaJuego.altoCelda*0.9, true, true, false);
		this.imgFantasmaView = new ImageView(imgFantasma);
		this.imgFantasmaPresaView = new ImageView(imgFantasmaPresa);
		imgFantasmaPresaView.setOpacity(0.0f);
		
	}

	public Collection<ImageView> getImageViews() {
		Collection<ImageView> imgFantasmaViews = new ArrayList<ImageView>();
		imgFantasmaViews.add(this.imgFantasmaView);
		imgFantasmaViews.add(this.imgFantasmaPresaView);
		return imgFantasmaViews;
	}

	public void update(final Observable o, Object data){
		Platform.runLater(new Runnable() {
            public void run() {
            	for(ImageView img : getImageViews()){
        			img.setTranslateX(((com.g7.modelo.fantasma.Fantasma)o).posicion().getCoordenadaX()*VistaJuego.anchoCelda+VistaJuego.anchoCelda*0.1);
        			img.setTranslateY(((com.g7.modelo.fantasma.Fantasma)o).posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda+VistaJuego.altoCelda*0.1);
        			if(((com.g7.modelo.fantasma.Fantasma)o).direccion().toString().equals("izquierda")){
        				img.setScaleX(-1);
        				img.setRotate(0);
        			}
        			else if(((com.g7.modelo.fantasma.Fantasma)o).direccion().toString().equals("derecha")){
        				img.setScaleX(1);
        				img.setRotate(0);
        			}
        			else if(((com.g7.modelo.fantasma.Fantasma)o).direccion().toString().equals("arriba")){
        				img.setScaleX(1);
        				img.setRotate(-90);
        			}
        			else if(((com.g7.modelo.fantasma.Fantasma)o).direccion().toString().equals("abajo")){
        				img.setScaleX(-1);
        				img.setRotate(-90);
        			}
        		}
        		if(((com.g7.modelo.fantasma.Fantasma)o).estado().equals(EstadosFantasma.CAZADOR)){
        			FadeTransition ft1 = new FadeTransition(SEC_15);
        			ft1.setNode(imgFantasmaPresaView);
        			ft1.setToValue(0.0f);
        			ft1.play();
        			FadeTransition ft2 = new FadeTransition(SEC_15);
        			ft2.setNode(imgFantasmaView);
        			ft2.setToValue(1.0f);
        			ft2.play();
        		}
        		else if(((com.g7.modelo.fantasma.Fantasma)o).estado().equals(EstadosFantasma.PRESA)){
        			FadeTransition ft1 = new FadeTransition(SEC_15);
        			ft1.setNode(imgFantasmaView);
        			ft1.setToValue(0.0f);
        			ft1.play();
        			FadeTransition ft2 = new FadeTransition(SEC_15);
        			ft2.setNode(imgFantasmaPresaView);
        			ft2.setToValue(1.0f);
        			ft2.play();
        		}
        		else if(((com.g7.modelo.fantasma.Fantasma)o).estado().equals(EstadosFantasma.MUERTO)){
        			FadeTransition ft1 = new FadeTransition(SEC_15);
        			ft1.setNode(imgFantasmaView);
        			ft1.setToValue(0.0f);
        			ft1.play();
        			FadeTransition ft2 = new FadeTransition(SEC_15);
        			ft2.setNode(imgFantasmaPresaView);
        			ft2.setToValue(0.0f);
        			ft2.play();
        		}
            }
        });
	}
	
	public void borrarFantasma(){
		for(ImageView imgView : this.getImageViews()){
			FadeTransition ft = new FadeTransition(Duration.millis(1));
			ft.setNode(imgView);
			ft.setToValue(0.0f);
			ft.play();
		}
	}

}
