import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import constant.Const;

public class SAXTest {

	public static void main(String[] args) throws SAXException, FileNotFoundException, IOException {
		// 创建处理文档内容相关事件的处理器
		ContentHandler contentHandler = new MyContentHandler();
		// 创建处理错误事件处理器
		ErrorHandler errorHandler = new MyErrorHandler();
		// 创建处理DTD相关事件的处理器
		DTDHandler dtdHandler = new MyDTDHandler();
		// 创建实体解析器
		EntityResolver entityResolver = new MyEntityResolver();

		// 创建一个XML解析器（通过SAX方式读取解析XML）
		XMLReader reader = XMLReaderFactory.createXMLReader();
		
		/*
		 * 设置解析器的相关特性 http://xml.org/sax/features/validation = true 表示开启验证特性 http://xml.org/sax/features/namespaces =
		 * true 表示开启命名空间特性
		 */
		reader.setFeature("http://xml.org/sax/features/validation", true);
		reader.setFeature("http://xml.org/sax/features/namespaces", true);
		
		// 设置XML解析器的处理文档内容相关事件的处理器
		reader.setContentHandler(contentHandler);
		// 设置XML解析器的处理错误事件处理器
		reader.setErrorHandler(errorHandler);
		// 设置XML解析器的处理DTD相关事件的处理器
		reader.setDTDHandler(dtdHandler);
		// 设置XML解析器的实体解析器
		reader.setEntityResolver(entityResolver);
		// 解析books.xml文档
		reader.parse(new InputSource(new FileReader(Const.BOOK_XML)));
	}

}

class MyContentHandler implements ContentHandler {
	StringBuffer jsonStringBuffer;
	int frontBlankCount = 0;

	public MyContentHandler() {
		jsonStringBuffer = new StringBuffer();
	}

	/*
	 * 接收字符数据的通知。 在DOM中 ch[begin:end] 相当于Text节点的节点值（nodeValue）
	 */
	@Override
	public void characters(char[] ch, int begin, int length) throws SAXException {
		StringBuffer buffer = new StringBuffer();
		for (int i = begin; i < begin + length; i++) {
			switch (ch[i]) {
			case '\\':
				buffer.append("\\\\");
				break;
			case '\r':
				buffer.append("\\r");
				break;
			case '\n':
				buffer.append("\\n");
				break;
			case '\t':
				buffer.append("\\t");
				break;
			case '\"':
				buffer.append("\\\"");
				break;
			default:
				buffer.append(ch[i]);
			}
		}
		System.out.println(this.toBlankString(this.frontBlankCount) + ">>> characters(" + length + "): " + buffer.toString());
	}

	/*
	 * 接收文档的结尾的通知。
	 */
	@Override
	public void endDocument() throws SAXException {
		System.out.println(this.toBlankString(--this.frontBlankCount) + ">>> end document");
	}

	/*
	 * 接收文档的结尾的通知。 参数意义如下： uri ：元素的命名空间 localName ：元素的本地名称（不带前缀） qName ：元素的限定名（带前缀）
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println(this.toBlankString(--this.frontBlankCount) + ">>> end element : " + qName + "(" + uri + ")");
	}

	/*
	 * 结束前缀 URI 范围的映射。
	 */
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		System.out.println(this.toBlankString(--this.frontBlankCount) + ">>> end prefix_mapping : " + prefix);
	}

	/*
	 * 接收元素内容中可忽略的空白的通知。 参数意义如下： ch : 来自 XML 文档的字符 start : 数组中的开始位置 length : 从数组中读取的字符的个数
	 */
	@Override
	public void ignorableWhitespace(char[] ch, int begin, int length) throws SAXException {
		StringBuffer buffer = new StringBuffer();
		for (int i = begin; i < begin + length; i++) {
			switch (ch[i]) {
			case '\\':
				buffer.append("\\\\");
				break;
			case '\r':
				buffer.append("\\r");
				break;
			case '\n':
				buffer.append("\\n");
				break;
			case '\t':
				buffer.append("\\t");
				break;
			case '\"':
				buffer.append("\\\"");
				break;
			default:
				buffer.append(ch[i]);
			}
		}
		System.out.println(this.toBlankString(this.frontBlankCount) + ">>> ignorable whitespace(" + length + "): " + buffer.toString());
	}

	/*
	 * 接收处理指令的通知。 参数意义如下： target : 处理指令目标 data : 处理指令数据，如果未提供，则为 null。
	 */
	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		System.out.println(this.toBlankString(this.frontBlankCount) + ">>> process instruction : (target = \"" + target + "\",data = \"" + data
				+ "\")");
	}

	/*
	 * 接收用来查找 SAX 文档事件起源的对象。 参数意义如下： locator : 可以返回任何 SAX 文档事件位置的对象
	 */
	@Override
	public void setDocumentLocator(Locator locator) {
		System.out.println(this.toBlankString(this.frontBlankCount) + ">>> set document_locator : (lineNumber = " + locator.getLineNumber()
				+ ",columnNumber = " + locator.getColumnNumber() + ",systemId = " + locator.getSystemId() + ",publicId = " + locator.getPublicId()
				+ ")");

	}

	/*
	 * 接收跳过的实体的通知。 参数意义如下： name : 所跳过的实体的名称。如果它是参数实体，则名称将以 '%' 开头， 如果它是外部 DTD 子集，则将是字符串 "[dtd]"
	 */
	@Override
	public void skippedEntity(String name) throws SAXException {
		System.out.println(this.toBlankString(this.frontBlankCount) + ">>> skipped_entity : " + name);
	}

	/*
	 * 接收文档的开始的通知。
	 */
	@Override
	public void startDocument() throws SAXException {
		System.out.println(this.toBlankString(this.frontBlankCount++) + ">>> start document ");
	}

	/*
	 * 接收元素开始的通知。 参数意义如下： uri ：元素的命名空间 localName ：元素的本地名称（不带前缀） qName ：元素的限定名（带前缀） atts ：元素的属性集合
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		System.out.println(this.toBlankString(this.frontBlankCount++) + ">>> start element : " + qName + "(" + uri + ")");
	}

	/*
	 * 开始前缀 URI 名称空间范围映射。 此事件的信息对于常规的命名空间处理并非必需： 当 http://xml.org/sax/features/namespaces 功能为 true（默认）时， SAX XML
	 * 读取器将自动替换元素和属性名称的前缀。 参数意义如下： prefix ：前缀 uri ：命名空间
	 */
	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		System.out.println(this.toBlankString(this.frontBlankCount++) + ">>> start prefix_mapping : xmlns:" + prefix + " = " + "\"" + uri + "\"");

	}

	private String toBlankString(int count) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < count; i++)
			buffer.append("    ");
		return buffer.toString();
	}

}

class MyDTDHandler implements DTDHandler {

	/*
	 * 接收注释声明事件的通知。 参数意义如下： name - 注释名称。 publicId - 注释的公共标识符，如果未提供，则为 null。 systemId - 注释的系统标识符，如果未提供，则为 null。
	 */
	@Override
	public void notationDecl(String name, String publicId, String systemId) throws SAXException {
		System.out.println(">>> notation declare : (name = " + name + ",systemId = " + publicId + ",publicId = " + systemId + ")");
	}

	/*
	 * 接收未解析的实体声明事件的通知。 参数意义如下： name - 未解析的实体的名称。 publicId - 实体的公共标识符，如果未提供，则为 null。 systemId - 实体的系统标识符。 notationName -
	 * 相关注释的名称。
	 */
	@Override
	public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
		System.out.println(">>> unparsed entity declare : (name = " + name + ",systemId = " + publicId + ",publicId = " + systemId
				+ ",notationName = " + notationName + ")");
	}

}

class MyEntityResolver implements EntityResolver {

	/*
	 * 允许应用程序解析外部实体。 解析器将在打开任何外部实体（顶级文档实体除外）前调用此方法 参数意义如下： publicId ： 被引用的外部实体的公共标识符，如果未提供，则为 null。 systemId ：
	 * 被引用的外部实体的系统标识符。 返回： 一个描述新输入源的 InputSource 对象，或者返回 null， 以请求解析器打开到系统标识符的常规 URI 连接。
	 */
	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		return null;
	}

}

class MyErrorHandler implements ErrorHandler {

	/*
	 * 接收可恢复的错误的通知
	 */
	@Override
	public void error(SAXParseException e) throws SAXException {
		System.err.println("Error (" + e.getLineNumber() + "," + e.getColumnNumber() + ") : " + e.getMessage());
	}

	/*
	 * 接收不可恢复的错误的通知。
	 */
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		System.err.println("FatalError (" + e.getLineNumber() + "," + e.getColumnNumber() + ") : " + e.getMessage());
	}

	/*
	 * 接收不可恢复的错误的通知。
	 */
	@Override
	public void warning(SAXParseException e) throws SAXException {
		System.err.println("Warning (" + e.getLineNumber() + "," + e.getColumnNumber() + ") : " + e.getMessage());
	}

}
