package dom4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.spi.XmlWriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;
import org.dom4j.io.XMLWriter;
import org.jaxen.SimpleNamespaceContext;

import constant.Const;

public class Dom4jTest {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream(Const.PATH_SRC + "dom4j/oracle_jpa-persistence.xml");

		SAXReader reader = new SAXReader();
		Document document = reader.read(in);

		Map<String, String> nsMap = new HashMap<String, String>();
		nsMap.put("p", "http://java.sun.com/xml/ns/persistence");

		XPath xPath = document.createXPath("//p:persistence-unit/p:properties");
		SimpleNamespaceContext namespaceContext = new SimpleNamespaceContext(nsMap);
		xPath.setNamespaceContext(namespaceContext);

		Namespace namespace = new Namespace("", "http://java.sun.com/xml/ns/persistence");
		QName qName = new QName("property", namespace);

		Element propertiesElement = (Element) xPath.selectSingleNode(document);
		Element newElement = propertiesElement.addElement(qName);
		newElement.addAttribute("name", "hibernate.hbm2ddl.default_schema");
		newElement.addAttribute("value", "TZ_RAC_SYS");

		FileOutputStream fout = new FileOutputStream(Const.PATH_SRC + "dom4j/res.xml");
		OutputFormat format = new OutputFormat("", false, "UTF-8");
		XMLWriter writer = new XMLWriter(fout, format);
		writer.write(document);
		writer.close();
	}

}
