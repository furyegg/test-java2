package jaxp.validate;

// requires JAXP 1.2

import java.io.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

public class ValidateSAX
{
   public static String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                        XML_SCHEMA = "http://www.w3.org/2001/XMLSchema",
                        SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

   public final static void main(String[] args)
      throws IOException, SAXException, ParserConfigurationException
   {
      if(args.length < 2)
      {
         System.err.println("usage is:");
         System.err.println("   java -jar tips.jar -validatesax input.xml schema.xsd");
         return;
      }

      File input = new File(args[0]),
           schema = new File(args[1]);
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setValidating(true);
      SAXParser parser = factory.newSAXParser();
      try
      {
         parser.setProperty(SCHEMA_LANGUAGE,XML_SCHEMA);
         parser.setProperty(SCHEMA_SOURCE,schema);
      }
      catch(SAXNotRecognizedException x)
      {
         System.err.println("Your SAX parser is not JAXP 1.2 compliant.");
      }
      parser.parse(input,new ErrorPrinter());
   }
}