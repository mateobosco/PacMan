package com.g7.vista;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PacMan implements Observer{

	private Collection<ImageView> imgPacManViews;
	private SequentialTransition seqTPacMan;
	private SequentialTransition seqTPacManCerrado;

	private final Duration SEC_15 = Duration.millis(15);

	public PacMan(){

		this.imgPacManViews = new ArrayList<ImageView>();

		Image imgPacMan = new Image("com/g7/vista/PacMan.png", VistaJuego.anchoCelda*0.9, VistaJuego.altoCelda*0.9, true, true, false);
		Image imgPacManCerrado = new Image("com/g7/vista/PacManCerrado.png", VistaJuego.anchoCelda*0.9, VistaJuego.altoCelda*0.9, true, true, false);
		ImageView imgPacManView = new ImageView(imgPacMan);
		ImageView imgPacManCerradoView = new ImageView(imgPacManCerrado);
		imgPacManCerradoView.setOpacity(0.0f);

		this.seqTPacMan = createSequentialTransitionForImageView(imgPacManView,true);
		this.seqTPacManCerrado = createSequentialTransitionForImageView(imgPacManCerradoView,false);

		this.imgPacManViews.add(imgPacManView);
		this.imgPacManViews.add(imgPacManCerradoView);
	}

	public Collection<ImageView> getImageViews() {
		return this.imgPacManViews;
	}

	public void update(final Observable o, Object data){
		Platform.runLater(new Runnable() {
            public void run() {
            	for(ImageView img : getImageViews()){
        			img.setTranslateX(((com.g7.modelo.PacMan)o).posicion().getCoordenadaX()*VistaJuego.anchoCelda+VistaJuego.anchoCelda*0.1);
        			img.setTranslateY(((com.g7.modelo.PacMan)o).posicion().getCoordenadaY()*VistaJuego.altoCelda+VistaJuego.altoCelda+VistaJuego.altoCelda*0.1);
        			if(((com.g7.modelo.PacMan)o).direccion().toString().equals("izquierda")){
        				img.setScaleX(-1);
        				img.setRotate(0);
        			}
        			else if(((com.g7.modelo.PacMan)o).direccion().toString().equals("derecha")){
        				img.setScaleX(1);
        				img.setRotate(0);
        			}
        			else if(((com.g7.modelo.PacMan)o).direccion().toString().equals("arriba")){
        				img.setScaleX(1);
        				img.setRotate(-90);
        			}
        			else if(((com.g7.modelo.PacMan)o).direccion().toString().equals("abajo")){
        				img.setScaleX(-1);
        				img.setRotate(-90);
        			}
        			if(((com.g7.modelo.PacMan)o).isFinJuego()){
        				FadeTransition ft = new FadeTransition(SEC_15);
        				ft.setNode(img);
        				ft.setToValue(0.0f);
        				seqTPacMan.stop();
        				seqTPacManCerrado.stop();
        				ft.play();
        			}
        		}
            }
        });
	}

	private SequentialTransition createSequentialTransitionForImageView(ImageView imView, boolean firstFilled){
		PauseTransition pt = new PauseTransition(Duration.millis(200));

		FadeTransition ft1 = new FadeTransition(SEC_15);
		ft1.setToValue(0.0f);

		FadeTransition ft2 = new FadeTransition(SEC_15);
		ft2.setToValue(1.0f);

		if(firstFilled){
			SequentialTransition seqT = new SequentialTransition(imView, pt, ft1, pt, ft2);
			seqT.setCycleCount(SequentialTransition.INDEFINITE);
			seqT.play();
			return seqT;
		}
		else{
			SequentialTransition seqT = new SequentialTransition(imView, pt, ft2, pt, ft1);
			seqT.setCycleCount(SequentialTransition.INDEFINITE);
			seqT.play();
			return seqT;
		}
	}
	
	public void borrarPacMan(){
		for(ImageView imgView : this.getImageViews()){
			FadeTransition ft = new FadeTransition(Duration.millis(1));
			ft.setNode(imgView);
			ft.setToValue(0.0f);
			ft.play();
		}
	}

}
