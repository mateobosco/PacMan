package com.g7.controlador.xml.writer;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.g7.modelo.Posicionable;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.util.xml.XMLException;

public class LaberintoTickWriter extends Writer {

	public static void write(Laberinto laberinto, OutputStream outputStream) throws WriterException{
		try{
			Document document = getDocument();
			Node nodoLaberinto = null;

			ArrayList<Attr> attributes = new ArrayList<Attr>();

			Attr ancho = document.createAttribute("ancho");
			ancho.setValue(String.format("%02d", laberinto.getDimension().ancho()));
			attributes.add(ancho);
			Attr alto = document.createAttribute("alto");
			alto.setValue(String.format("%02d", laberinto.getDimension().alto()));
			attributes.add(alto);
			Attr nodoAncho = document.createAttribute("nodoAncho");
			nodoAncho.setValue(laberinto.getNodoAncho());
			attributes.add(nodoAncho);
			Attr nodoAlto = document.createAttribute("nodoAlto");
			nodoAlto.setValue(laberinto.getNodoAlto());
			attributes.add(nodoAlto);
			Attr inicioPacman = document.createAttribute("inicioPacman");
			inicioPacman.setValue(laberinto.getInicioPacman());
			attributes.add(inicioPacman);
			Attr inicioFantasmas = document.createAttribute("inicioFantasmas");
			inicioFantasmas.setValue(laberinto.getInicioFantasma());
			attributes.add(inicioFantasmas);

			nodoLaberinto = appendChild(document,"laberinto", null, attributes);

			Collection<Celda> celdas = laberinto.getCeldas();

			for(Celda celda : celdas){
				ArrayList<Attr> celdaAttributes = new ArrayList<Attr>();

				Attr id = document.createAttribute("id");
				id.setValue(celda.getId());
				celdaAttributes.add(id);
				Attr fila = document.createAttribute("fila");
				fila.setValue(String.format("%02d", celda.posicion().getCoordenadaY()));
				celdaAttributes.add(fila);
				Attr columna = document.createAttribute("columna");
				columna.setValue(String.format("%02d", celda.posicion().getCoordenadaX()));
				celdaAttributes.add(columna);
				Attr contiene = document.createAttribute("contiene");
				for(Posicionable elemento : celda.getElementosPosicionable()){
					if(elemento.getClass().getName().substring(elemento.getClass().getName().lastIndexOf('.')+1).equals("Bolon")){
						contiene.setValue("bolon");
					}
					else if(elemento.getClass().getName().substring(elemento.getClass().getName().lastIndexOf('.')+1).equals("Bolita")){
						contiene.setValue("bolita");
					}
				}
				celdaAttributes.add(contiene);
				Attr izquierda = document.createAttribute("izquierda");
				izquierda.setValue(celda.getCeldaIzquierda()==null?"":celda.getCeldaIzquierda().getId());
				celdaAttributes.add(izquierda);
				Attr derecha = document.createAttribute("derecha");
				derecha.setValue(celda.getCeldaDerecha()==null?"":celda.getCeldaDerecha().getId());
				celdaAttributes.add(derecha);
				Attr arriba = document.createAttribute("arriba");
				arriba.setValue(celda.getCeldaArriba()==null?"":celda.getCeldaArriba().getId());
				celdaAttributes.add(arriba);
				Attr abajo = document.createAttribute("abajo");
				abajo.setValue(celda.getCeldaAbajo()==null?"":celda.getCeldaAbajo().getId());
				celdaAttributes.add(abajo);

				appendChild(nodoLaberinto,"nodo", null, celdaAttributes);

			}

			writeOutputStream(document, outputStream);
		}
		catch(XMLException XMLE){
			throw new WriterException(XMLE.getMessage());
		}
	}

}
