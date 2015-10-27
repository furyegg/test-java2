package xpath;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;

public class Dom4jXPathTest {

	public static void main(String[] args) throws Exception {
		dom4j();
	}
	
	private static void dom4j() throws Exception {
		File xmlFile = new File("src/main/java/xpath/met.xsd");

		SAXReader reader = new SAXReader();
		Document doc = reader.read(xmlFile);

		Map<String, String> map = new HashMap<String, String>();
		map.put("model", "http://www.eurofiling.info/xbrl/ext/model");

		XPath xpath = new Dom4jXPath("//*[@model:fromDate]");
		xpath.setNamespaceContext(new SimpleNamespaceContext(map));

		List<Node> selectNodes = (List<Node>) xpath.selectNodes(doc);
		for (Node node : selectNodes) {
			Element e = (Element) node;
			
			System.out.println(e.attributeValue("fromDate"));
			
			doc.remove(node);
			node.detach();
		}

		XMLWriter output = new XMLWriter(new FileWriter("src/main/java/xpath/met-new.xsd"));
		output.write(doc);
		output.close();
	}

	private static void dom4j2() throws Exception {
		File xmlFile = new File("src/main/java/xpath/appContext-jms.xml");

		SAXReader reader = new SAXReader();
		Document doc = reader.read(xmlFile);

		Map map = new HashMap();
		map.put("amq", "http://activemq.apache.org/schema/core");

		XPath xpath = new Dom4jXPath("//amq:transportConnector/@uri");
		xpath.setNamespaceContext(new SimpleNamespaceContext(map));

		System.out.println(xpath.selectSingleNode(doc));

		Attribute attr = (Attribute) xpath.selectSingleNode(doc);
		attr.setValue("123213123213213");

		XMLWriter output = new XMLWriter(new FileWriter("src/main/java/xpath/appContext-jms_output.xml"));
		output.write(doc);
		output.close();
	}

}
