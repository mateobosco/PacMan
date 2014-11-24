package com.g7.controlador.xml.reader;

import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.g7.modelo.ConfiguracionTicks;
import com.g7.modelo.Fruta;
import com.g7.modelo.PacMan;
import com.g7.modelo.Posicion;
import com.g7.modelo.Posicionable;
import com.g7.modelo.direcciones.Direccion;
import com.g7.modelo.direcciones.DireccionesFactory;
import com.g7.modelo.fantasma.Fantasma;
import com.g7.modelo.fantasma.personalidades.Personalidad;
import com.g7.modelo.fantasma.personalidades.PersonalidadesFactory;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.util.reflexion.ReflexionUtils;

public class PersonajesReader extends Reader {
	
	public static Collection<Posicionable> read(InputStream inputStream, Laberinto laberinto, PacMan pacman)throws ReaderException{
		Collection<Posicionable> personajes = new Vector<Posicionable>();
		try{
			Document document=getDocument(inputStream);
			Element elementoJuego = getElement(document,"juego");
			Enumeration<Attr> juegoAttributes = getAttributes(elementoJuego);
			@SuppressWarnings("unused")
			String posicionPacman,fila,columna,sentido,puntaje,finJuego;
			columna = getAttribute(juegoAttributes, "columna").getValue();
			fila = getAttribute(juegoAttributes, "fila").getValue();
			finJuego = getAttribute(juegoAttributes, "finJuego").getValue();
			posicionPacman = getAttribute(juegoAttributes, "posicionPacman").getValue();
			puntaje = getAttribute(juegoAttributes, "puntaje").getValue();
			sentido = getAttribute(juegoAttributes, "sentido").getValue();
			
			ConfiguracionTicks confTicks = new ConfiguracionTicks();
			//PacMan pacman = new PacMan(laberinto,new Posicion(Integer.valueOf(columna),Integer.valueOf(fila)),confTicks);
			
			DireccionesFactory direcciones = new DireccionesFactory();
			pacman.setDireccion((Direccion) ReflexionUtils.getMethod(direcciones.getClass(), sentido).invoke(direcciones));
			
			pacman.addPuntaje(Integer.valueOf(puntaje));
			if(finJuego.equals("true"))
				pacman.morir();
			
			personajes.add(pacman);
			
			Enumeration<Node> nodosEnumFantasmas = getChildElements(elementoJuego,"fantasma");
			while(nodosEnumFantasmas.hasMoreElements()){
				Node node = nodosEnumFantasmas.nextElement();
				@SuppressWarnings("unused")
				String idFantasma,nodoFantasma,filaFantasma,columnaFantasma,sentidoFantasma,personalidadFantasma,estadoFantasma;

				Enumeration<Attr> nodeAttributes = getAttributes(node);
				
				columnaFantasma = getAttribute(nodeAttributes, "columna").getValue();
				estadoFantasma = getAttribute(nodeAttributes, "estado").getValue();
				filaFantasma = getAttribute(nodeAttributes, "fila").getValue();
				idFantasma = getAttribute(nodeAttributes, "id").getValue();
				nodoFantasma = getAttribute(nodeAttributes, "nodo").getValue();
				personalidadFantasma = getAttribute(nodeAttributes, "personalidad").getValue();
				sentidoFantasma = getAttribute(nodeAttributes, "sentido").getValue();
				
				Fantasma fantasma = new Fantasma(idFantasma, laberinto, new Posicion(Integer.valueOf(columnaFantasma),Integer.valueOf(filaFantasma)),confTicks);
				PersonalidadesFactory personalidades = new PersonalidadesFactory();
				fantasma.setPersonalidad((Personalidad) ReflexionUtils.getMethod(personalidades.getClass(), personalidadFantasma).invoke(personalidades));
				fantasma.setDireccionActual((Direccion) ReflexionUtils.getMethod(direcciones.getClass(), sentidoFantasma).invoke(direcciones));
				
				personajes.add(fantasma);
				
			}
			
			Enumeration<Node> nodosEnumFrutas = getChildElements(elementoJuego,"fruta");
			while(nodosEnumFrutas.hasMoreElements()){
				Node node = nodosEnumFrutas.nextElement();
				@SuppressWarnings("unused")
				String idFruta,nodoFruta,filaFruta,columnaFruta,sentidoFruta;

				Enumeration<Attr> nodeAttributes = getAttributes(node);
				
				columnaFruta = getAttribute(nodeAttributes, "columna").getValue();
				filaFruta = getAttribute(nodeAttributes, "fila").getValue();
				idFruta = getAttribute(nodeAttributes, "id").getValue();
				nodoFruta = getAttribute(nodeAttributes, "nodo").getValue();
				sentidoFruta = getAttribute(nodeAttributes, "sentido").getValue();
				
				Fruta fruta = new Fruta(idFruta, laberinto, new Posicion(Integer.valueOf(columnaFruta),Integer.valueOf(filaFruta)),confTicks);
				fruta.setDireccionActual((Direccion) ReflexionUtils.getMethod(direcciones.getClass(), sentidoFruta).invoke(direcciones));
				
				personajes.add(fruta);
				
			}
			
		}
		catch(Exception e){
			throw new ReaderException();
		}
		return personajes;
	}

}
