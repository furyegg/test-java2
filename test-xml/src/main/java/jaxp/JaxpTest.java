package jaxp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import constant.Const;

public class JaxpTest {
	
	private static final String SRC_XML = Const.PATH_SRC + "jaxp/dsTemplate.xml";

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		Document document = builder.parse(SRC_XML);
		XPath xPath = XPathFactory.newInstance().newXPath();
		
//		Node dssNode = (Node) document.getElementsByTagName("datasources").item(0);
//		System.out.println(dssNode.getChildNodes().getLength());
		
		Node xadsNode = (Node) document.getElementsByTagName("xa-datasource").item(0);
		System.out.println(xadsNode.getAttributes().getNamedItem("jndi-name"));

//		Node securityNode = (Node) xPath.evaluate("security", xadsNode, XPathConstants.NODE);
//		System.out.println(securityNode.getChildNodes().getLength());
//		
//		Node securityDomainNode = (Node) xPath.evaluate("security-domain", securityNode, XPathConstants.NODE);
//		System.out.println(securityDomainNode.getTextContent());
//		
//		Node securityDomainNode2 = (Node) xPath.evaluate("//security-domain", xadsNode, XPathConstants.NODE);
//		System.out.println(securityDomainNode2.getTextContent());
		
		Node secDomainNode = (Node) xPath.evaluate("//security-domains/security-domain", document, XPathConstants.NODE);
		System.out.println(getNodeAttrValue(secDomainNode, "name"));
		
		Node userNode = (Node) xPath.evaluate("//module-option[@name='username']", secDomainNode, XPathConstants.NODE);
		System.out.println(getNodeAttrValue(userNode, "value"));
	}
	
	private static String getNodeAttrValue(Node node, String attrName) {
		NamedNodeMap attributes = node.getAttributes();
		return attributes.getNamedItem(attrName).getNodeValue();
	}
	
}
