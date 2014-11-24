package com.g7.controlador.xml.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.g7.util.xml.XML;
import com.g7.util.xml.XMLException;

public abstract class Reader {
	
	protected static Properties getProperties(String sFile)throws ReaderException{
		Properties prop=null;
		try{

			InputStream in = Reader.class.getResourceAsStream(sFile);
			
			prop=new Properties();
			prop.load(in);
		
		}catch(IOException IOE){
			throw new ReaderException(IOE.getMessage());
		}
		return prop;
	}
	
	protected static Document getDocument(InputStream inputStream)throws XMLException{
		return XML.readXMLInputStream(inputStream);
	}
	
	protected static Enumeration<Attr> getAttributes(Node node) throws XMLException{
		return XML.getAttributes(node);
	}
	
	protected static Element getElement(Node node,String elementName)throws XMLException{
		Element element=XML.getChildElement(node,elementName);
		if(element==null){
			throw new XMLException("No se encuentra el Elemento "+elementName);
		}

		return element;
	}
	
	protected static Enumeration<Node> getChildElements(Node node,String childElementName){
		return XML.getElements(node,childElementName);
	}
	
	protected static Attr getAttribute(Enumeration<Attr> attributes,String attributeName)throws XMLException{
		Attr attr=XML.getAttribute(attributes, attributeName);
		if(attr==null){
			throw new XMLException("No se encuentra el Atributo "+attributeName);
		}

		return attr;
	}

}
