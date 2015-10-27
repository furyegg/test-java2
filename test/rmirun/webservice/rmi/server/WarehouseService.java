
package rmi.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "WarehouseService", targetNamespace = "http://rmi/", wsdlLocation = "http://localhost:8080/WebServices/warehouse?wsdl")
public class WarehouseService
    extends Service
{

    private final static URL WAREHOUSESERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(rmi.server.WarehouseService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = rmi.server.WarehouseService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/WebServices/warehouse?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/WebServices/warehouse?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        WAREHOUSESERVICE_WSDL_LOCATION = url;
    }

    public WarehouseService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WarehouseService() {
        super(WAREHOUSESERVICE_WSDL_LOCATION, new QName("http://rmi/", "WarehouseService"));
    }

    /**
     * 
     * @return
     *     returns Warehouse
     */
    @WebEndpoint(name = "WarehousePort")
    public Warehouse getWarehousePort() {
        return super.getPort(new QName("http://rmi/", "WarehousePort"), Warehouse.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Warehouse
     */
    @WebEndpoint(name = "WarehousePort")
    public Warehouse getWarehousePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://rmi/", "WarehousePort"), Warehouse.class, features);
    }

}
