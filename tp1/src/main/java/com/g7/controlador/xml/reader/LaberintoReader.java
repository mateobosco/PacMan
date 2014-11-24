package com.g7.controlador.xml.reader;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.g7.modelo.BolasFactory;
import com.g7.modelo.Posicion;
import com.g7.modelo.laberinto.Celda;
import com.g7.modelo.laberinto.Dimension;
import com.g7.modelo.laberinto.Laberinto;
import com.g7.util.reflexion.ReflexionUtils;

public class LaberintoReader extends Reader{
	
	public static Laberinto read(InputStream inputStream)throws ReaderException{
		Laberinto laberinto = null;
		try{
			Document document=getDocument(inputStream);
			Element elementoLaberinto = getElement(document,"laberinto");
			Enumeration<Attr> laberintoAttributes = getAttributes(elementoLaberinto);
			String ancho,alto,nodoAncho,nodoAlto,inicioPacman,inicioFantasma;
			alto = getAttribute(laberintoAttributes, "alto").getValue();
			ancho = getAttribute(laberintoAttributes, "ancho").getValue();
			inicioFantasma = getAttribute(laberintoAttributes, "inicioFantasmas").getValue();
			inicioPacman = getAttribute(laberintoAttributes, "inicioPacman").getValue();
			nodoAlto = getAttribute(laberintoAttributes, "nodoAlto").getValue();
			nodoAncho = getAttribute(laberintoAttributes, "nodoAncho").getValue();
			laberinto = new Laberinto(new Dimension(Integer.valueOf(ancho),Integer.valueOf(alto)));
			laberinto.setNodoAlto(nodoAlto);
			laberinto.setNodoAncho(nodoAncho);
			laberinto.setInicioFantasma(inicioFantasma);
			laberinto.setInicioPacman(inicioPacman);
			
			Enumeration<Node> nodosEnum = getChildElements(elementoLaberinto,"nodo");
			Vector<Nodo> nodos = new Vector<Nodo>();
			while(nodosEnum.hasMoreElements()){
				Node node = nodosEnum.nextElement();
				Nodo nodo = new Nodo();

				Enumeration<Attr> nodeAttributes = getAttributes(node);
				nodo.abajo = getAttribute(nodeAttributes, "abajo").getValue();
				nodo.arriba = getAttribute(nodeAttributes, "arriba").getValue();
				nodo.columna = getAttribute(nodeAttributes, "columna").getValue();
				nodo.contiene = getAttribute(nodeAttributes, "contiene").getValue();
				nodo.derecha = getAttribute(nodeAttributes, "derecha").getValue();
				nodo.fila = getAttribute(nodeAttributes, "fila").getValue();
				nodo.id = getAttribute(nodeAttributes, "id").getValue();
				nodo.izquierda = getAttribute(nodeAttributes, "izquierda").getValue();

				nodos.add(nodo);
				
				Celda celda = new Celda(nodo.id, new Posicion(Integer.valueOf(nodo.columna), Integer.valueOf(nodo.fila)));
				laberinto.agregarCelda(celda);
				
				BolasFactory bolas = new BolasFactory();
				if(!nodo.contiene.isEmpty()){
					ReflexionUtils.getMethod(bolas.getClass(), nodo.contiene).invoke(bolas,laberinto,celda.posicion());
				}
			}
			
			setAdyacentes(laberinto, nodos);
			
		}
		catch(Exception e){
			throw new ReaderException(e.getMessage());
		}
		return laberinto;
	}
	
	protected static void setAdyacentes(Laberinto laberinto, Vector<Nodo> nodos){
		for(Nodo nodo : nodos){
			Celda celda = laberinto.getCelda(nodo.id);
			if(nodo.abajo != ""){
				celda.setCeldaAbajo(laberinto.getCelda(nodo.abajo));
			}
			if(nodo.arriba != ""){
				celda.setCeldaArriba(laberinto.getCelda(nodo.arriba));
			}
			if(nodo.derecha != ""){
				celda.setCeldaDerecha(laberinto.getCelda(nodo.derecha));
			}
			if(nodo.izquierda != ""){
				celda.setCeldaIzquierda(laberinto.getCelda(nodo.izquierda));
			}
		}
	}
	
	private static class Nodo{
		public String id;
		public String fila;
		public String columna;
		public String contiene;
		public String izquierda;
		public String derecha;
		public String arriba;
		public String abajo;
		
		public Nodo(){}
		
	}
}
