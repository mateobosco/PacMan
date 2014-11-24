package com.g7.controlador.xml.writer;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.g7.modelo.PacMan;
import com.g7.modelo.Posicionable;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.util.xml.XMLException;

public class PersonajesTickWriter extends Writer{
	
	public static void write(Collection<Posicionable> personajes, OutputStream outputStream) throws WriterException{
		try{
			Document document = getDocument();
			Node juego = null;
			for(Posicionable personaje : personajes){
				if(personaje.getClass().getName().substring(personaje.getClass().getName().lastIndexOf('.')+1).equals("PacMan")){
					ArrayList<Attr> attributes = new ArrayList<Attr>();
					
					Attr posicionPacman = document.createAttribute("posicionPacman");
					posicionPacman.setValue(String.format("%02d", personaje.posicion().getCoordenadaX()).concat(String.format("%02d", personaje.posicion().getCoordenadaY())));
					attributes.add(posicionPacman);
					Attr fila = document.createAttribute("fila");
					fila.setValue(String.format("%02d", personaje.posicion().getCoordenadaX()));
					attributes.add(fila);
					Attr columna = document.createAttribute("columna");
					columna.setValue(String.format("%02d", personaje.posicion().getCoordenadaY()));
					attributes.add(columna);
					Attr sentido = document.createAttribute("sentido");
					sentido.setValue(((PacMan)personaje).direccion().toString());
					attributes.add(sentido);
					Attr puntaje = document.createAttribute("puntaje");
					puntaje.setValue("");
					attributes.add(puntaje);
					Attr finJuego = document.createAttribute("finJuego");
					finJuego.setValue("");
					attributes.add(finJuego);
					
					juego = appendChild(document,"juego", null, attributes);
				}
			}
			if(juego != null){
				for(Posicionable personaje : personajes){
					if(personaje.getClass().getName().substring(personaje.getClass().getName().lastIndexOf('.')+1).equals("Fantasma")){
						ArrayList<Attr> attributes = new ArrayList<Attr>();
						
						Attr id = document.createAttribute("id");
						id.setValue(((Fantasma)personaje).getId());
						attributes.add(id);
						Attr nodo = document.createAttribute("nodo");
						nodo.setValue(String.format("%02d", personaje.posicion().getCoordenadaX()).concat(String.format("%02d", personaje.posicion().getCoordenadaY())));
						attributes.add(nodo);
						Attr fila = document.createAttribute("fila");
						fila.setValue(String.format("%02d", personaje.posicion().getCoordenadaX()));
						attributes.add(fila);
						Attr columna = document.createAttribute("columna");
						columna.setValue(String.format("%02d", personaje.posicion().getCoordenadaY()));
						attributes.add(columna);
						Attr sentido = document.createAttribute("sentido");
						sentido.setValue(((Fantasma)personaje).direccion().toString());
						attributes.add(sentido);
						Attr personalidad = document.createAttribute("personalidad");
						personalidad.setValue(((Fantasma)personaje).getPersonalidad().toString());
						attributes.add(personalidad);
						Attr estado = document.createAttribute("estado");
						estado.setValue(((Fantasma)personaje).estado());
						attributes.add(estado);
						
						appendChild(juego,"fantasma", null, attributes);
					}
				}
			}
			writeOutputStream(document, outputStream);
		}
		catch(XMLException XMLE){
			throw new WriterException(XMLE.getMessage());
		}
	}

}
