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

public class Fruta implements Observer{

	private ImageView imgFrutaView;

	private final Duration SEC_15 = Duration.millis(15);

	public Fruta(){

		Image imgFruta = new Image("com/g7/vista/Fruta.png", VistaJuego.anchoCelda*0.9, VistaJuego.altoCelda*0.9, true, true, false);
		this.imgFrutaView = new ImageView(imgFruta);
		
	}

	public Collection<ImageView> getImageViews() {
		Collection<ImageView> imgFrutaViews = new ArrayList<ImageView>();
		imgFrutaViews.add(this.imgFrutaView);
		return imgFrutaViews;
	}

	public void update(final Observable o, Object data){
		Platform.runLater(new Runnable() {
            public void run() {
            	for(ImageView img : getImageViews()){
        			img.setTranslateX(((com.g7.modelo.Fruta)o).posicion().getCoordenadaX()*VistaJuego.anchoCelda+VistaJuego.anchoCelda*0.1);
        			img.setTranslateY(((com.g7.modelo.Fruta)o).posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda+VistaJuego.altoCelda*0.1);
        			if(((com.g7.modelo.Fruta)o).direccion().toString().equals("izquierda")){
        				img.setScaleX(-1);
        				img.setRotate(0);
        			}
        			else if(((com.g7.modelo.Fruta)o).direccion().toString().equals("derecha")){
        				img.setScaleX(1);
        				img.setRotate(0);
        			}
        			else if(((com.g7.modelo.Fruta)o).direccion().toString().equals("arriba")){
        				img.setScaleX(1);
        				img.setRotate(-90);
        			}
        			else if(((com.g7.modelo.Fruta)o).direccion().toString().equals("abajo")){
        				img.setScaleX(-1);
        				img.setRotate(-90);
        			}
        			if(((com.g7.modelo.Fruta)o).isComido()){
        				FadeTransition ft = new FadeTransition(SEC_15);
        				ft.setNode(img);
        				ft.setToValue(0.0f);
        				ft.play();
        			}
        		}
            }
        });
	}
	
	public void borrarFruta(){
		for(ImageView imgView : this.getImageViews()){
			FadeTransition ft = new FadeTransition(Duration.millis(1));
			ft.setNode(imgView);
			ft.setToValue(0.0f);
			ft.play();
		}
	}

}
