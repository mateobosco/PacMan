package com.g7.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XML {
	
	/**Read Document from File*/
	public static Document readXMLInputStream(InputStream inputStream)throws XMLException{
		return readDocumentFromInputStream(inputStream);
	}
	
	/**Write Document to File*/
	public static void writeXMLOutputStream(Document document, OutputStream outputStream)throws XMLException{
		writeDocumentToOutputStream(document, outputStream);
	}
	
	/**Convierte un XML de Document a String*/
	public static String convertDocumentToXML(Document document)throws XMLException{
		String sResult=null;
		try{
			Transformer transformer = getTransformer();
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			transformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
			transformer.transform(new DOMSource(document), result);
			sResult=stringWriter.getBuffer().toString();
		} catch (TransformerConfigurationException TCE) {
			throw new XMLException(TCE.getMessage());	
		} catch (TransformerException TE) {
			throw new XMLException(TE.getMessage());	
		}        		
		return sResult;
	}
	
	/**Append Child to a Node
	 * @throws XMLException */
	public static Node appendChild(Node parent, String childName, String value, Collection<Attr> childAttributes) throws XMLException{
		Document document=parent.getOwnerDocument();
		if(document==null)
			document=(Document)parent;
		Element resultElement=null;
		try{
			resultElement=document.createElement(childName);
			resultElement.setNodeValue(value);
			for(Attr attr : childAttributes){
				resultElement.setAttributeNode(attr);
			}
			parent.appendChild(resultElement);
		}catch(DOMException DOME){
			throw new XMLException(DOME.getMessage());
		}
		return resultElement;
	}	
	
	/**Genera un Documento Vacío*/
	public static Document newDocument()throws XMLException{
		Document document=null;
		try{
		    
		    DocumentBuilder documentBuilder=getDocumentBuilder();	
			document=documentBuilder.newDocument();
			
		}catch(ParserConfigurationException PCE){
			throw new XMLException(PCE.getMessage());
		}
		return document;
	}
	
	/**Get Children from a Node*/
	public static Vector<Node> getChildren(Node parent){
		Vector<Node> nodes = new Vector<Node>();
		if(parent!=null){
			Node node = getFirstChild(parent);
			while(node != null){
				nodes.add(node);
				node = node.getNextSibling();
			}
		}
		return nodes;
	}
	
	/**Get a Child*/
	public static Element getChildElement(Node parent,String name){
		Node childResult=null;
		Vector<Node> children=getChildren(parent);
		if(children!=null){
			for(Node child : children){
				if((getType(child)==Node.ELEMENT_NODE)&&(getName(child).equalsIgnoreCase(name))){
					childResult=child;
					break;
				}
			}
		}
		return (childResult!=null)?(Element)childResult:null;
		//return (Element)child;
	}
	
	/**Get Node Type*/
	public static int getType(Node node){
		return (node!=null)?node.getNodeType():0;
	}

	/**Get Node Name*/
	public static String getName(Node node){
		return (node!=null)?node.getNodeName():null;
	}

	/**Get Node Value*/
	public static String getValue(Node parentNode){
		String sResult="";
		NodeList nodeList=parentNode.getChildNodes();
		Node childNode=null;
		for(int iIndex=0;iIndex<nodeList.getLength();iIndex++){
			childNode=nodeList.item(iIndex);
			if(childNode.getNodeType()==Node.TEXT_NODE){
				sResult=childNode.getNodeValue();
				break;
			}
		}
		return sResult;
	}

	/**Get Elements from a parent*/
	public static Enumeration<Node> getElements(Node parentNode,String childElementName){
		Vector<Node> resultVector=new Vector<Node>();

		/*Obtiene todos los nodos hijos directos*/
		Vector<Node> nodeList=getChildren(parentNode);
		
		/*Recorre todos los nodos hijos directos*/
		for(Node childNode : nodeList){
			/*Si el nodo es un Elemento y coincide el nombre
			 * lo incluye en el vector resultado.*/
			if((childNode.getNodeType()==Node.ELEMENT_NODE)&&(childNode.getNodeName().equals(childElementName)))
				resultVector.add(childNode);
		}

		return resultVector.elements();
	}
	
	/**Get Attributes from a Node*/
	public static Enumeration<Attr> getAttributes(Node parent){
		NamedNodeMap attributes = (parent!=null)?parent.getAttributes():null;
		Vector<Attr> attrVector = new Vector<Attr>();
		if(attributes!=null){
			for(int index=0; index<attributes.getLength();index++ ){
				attrVector.add((Attr)attributes.item(index));
			}
		}
		return attrVector.elements();
	}
	
	/**Get Specific Attribute from a list of Attributes*/
	public static Attr getAttribute(Enumeration<Attr> attributes, String attrName){
		Attr attribute = null;
		while(attributes.hasMoreElements()){
			attribute = attributes.nextElement();
			if(attribute.getName() == attrName)
				break;
			else
				attribute = null;
		}
		return attribute;
	}
	
	/**Get the First Child from a Node*/
	protected static Node getFirstChild(Node parent){
		return (parent!=null)?parent.getFirstChild():null;
	}

	/**Read Document from File*/
	protected static Document readDocumentFromInputStream(InputStream inputStream)throws XMLException{
		Document resultDocument=null;
		
		try{
			
			Transformer transformer=getTransformer();
			
			StreamSource source=new StreamSource(inputStream);
			DOMResult result=new DOMResult();
			
			transformer.transform(source,result);
			
			resultDocument=(Document)result.getNode();
			
		}catch(TransformerConfigurationException TCE){
			throw new XMLException(TCE.getMessage());
		}catch(TransformerException TE){
			throw new XMLException(TE.getMessage());
		}
		

		return resultDocument;
	}

	/**Write Document to File
	 * @throws XMLException */
	protected static void writeDocumentToOutputStream(Document document, OutputStream outputStream) throws XMLException{
		
		
		try {
			String xml = convertDocumentToXML(document);
			outputStream.write(xml.getBytes());
			outputStream.flush();
		} catch (IOException IOE) {
			throw new XMLException(IOE.getMessage());
		}
		
	}
	
	/**Genera un Transformer*/
	protected static Transformer getTransformer()throws TransformerConfigurationException{
		TransformerFactory factory = TransformerFactory.newInstance();
		return factory.newTransformer();
	}
	
	/**Genera un DocumentBuilder*/
	protected static DocumentBuilder getDocumentBuilder()throws ParserConfigurationException{
		DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
		return documentBuilderFactory.newDocumentBuilder();	
	} 
	
}
