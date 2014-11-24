package com.g7.controlador.xml.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.g7.util.xml.XML;
import com.g7.util.xml.XMLException;

public abstract class Writer {
	
	protected static Document getDocument() throws XMLException {
		Document document = null;
		document = XML.newDocument();

		return document;
	}
	
	protected static Node appendChild(Node parent, String childName, String value, Collection<Attr> childAttributes) throws XMLException{
		return XML.appendChild(parent, childName, value, childAttributes);
	}
	
	protected static void writeOutputStream(Document document, OutputStream outputStream) throws XMLException {
		try {

			String xml = XML.convertDocumentToXML(document);
			outputStream.write(xml.getBytes());
			outputStream.flush();

		} catch (IOException IOE) {
			throw new XMLException(IOE.getMessage());
		}
	}

}
