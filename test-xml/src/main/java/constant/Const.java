package constant;

public abstract class Const {
	public static final String TEST_XML = "src/main/java/test.xml";
	public static final String BOOK_XML = "src/main/java/books.xml";
	
	public static final String PATH_SRC = "src/main/java/";
	public static final String PATH_XML = "d:/jaxp/xml/";
	
	public static final String XML_BIG = "d:/java/jaxp/xml/big.xml";
	public static final String XML_SMALL = "d:/java/jaxp/xml/small.xml";
	public static final String XML_VAL = PATH_XML + "books.xml";
	public static final String XML_XBRL = PATH_SRC + "comparation/corep_conpru_c_00.01_and_c_05.02.xbrl";
	
	public static final int NODE_COUNTS_S = 100;
	public static final int NODE_COUNTS_M = 1000000;
	public static final int NODE_COUNTS_L = 10000000;
	
	public static final String NSURI_BOOK = "http://www.test.com/book";
	public static final String NSURI_AUTHER = "http://www.test.com/author";
	public static final String NSURI_XSI = "http://www.w3.org/2001/XMLSchema-instance";
	
	public static final String XSD_BOOK = PATH_SRC + "comparation/book.xsd";
	public static final String XSD_XBRL = "D:/xbrl/xsd/eu/fr/xbrl/crr/fws/corep/its-2013-02/2013-12-01/mod/corep_con.xsd";
}