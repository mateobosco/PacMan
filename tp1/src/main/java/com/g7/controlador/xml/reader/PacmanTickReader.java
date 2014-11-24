package com.g7.controlador.xml.reader;

import java.io.InputStream;
import java.util.Enumeration;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.g7.modelo.direcciones.Direccion;
import com.g7.modelo.direcciones.DireccionesFactory;
import com.g7.util.reflexion.ReflexionUtils;

public class PacmanTickReader extends Reader {
	
	public static Direccion read(InputStream inputStream)throws ReaderException{
		Direccion pacmanDireccion = null;
		try{
			Document document=getDocument(inputStream);
			Element elementoJuego = getElement(document,"juego");
			Enumeration<Node> nodosEnum = getChildElements(elementoJuego,"pacman");
			while(nodosEnum.hasMoreElements()){
				Node node = nodosEnum.nextElement();
				String direccion;

				Enumeration<Attr> nodeAttributes = getAttributes(node);
				direccion = getAttribute(nodeAttributes, "direccion").getValue();
				
				DireccionesFactory direcciones = new DireccionesFactory();

				pacmanDireccion = (Direccion) ReflexionUtils.getMethod(direcciones.getClass(), direccion).invoke(direcciones);
				
			}
			
		}
		catch(Exception e){
			throw new ReaderException();
		}
		return pacmanDireccion;
	}

}
