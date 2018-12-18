
package org.zixing.util.his.call;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the main.java.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Webservice_QNAME = new QName("http://common.ws.zxxy.com/", "webservice");
    private final static QName _WebserviceResponse_QNAME = new QName("http://common.ws.zxxy.com/", "webserviceResponse");
    private final static QName _WsException_QNAME = new QName("http://common.ws.zxxy.com/", "WsException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: main.java.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link org.zixing.util.his.call.Webservice }
     * 
     */
    public org.zixing.util.his.call.Webservice createWebservice() {
        return new org.zixing.util.his.call.Webservice();
    }

    /**
     * Create an instance of {@link WebserviceResponse }
     * 
     */
    public WebserviceResponse createWebserviceResponse() {
        return new WebserviceResponse();
    }

    /**
     * Create an instance of {@link org.zixing.util.his.call.WsException }
     * 
     */
    public org.zixing.util.his.call.WsException createWsException() {
        return new org.zixing.util.his.call.WsException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link org.zixing.util.his.call.Webservice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://common.ws.zxxy.com/", name = "webservice")
    public JAXBElement<org.zixing.util.his.call.Webservice> createWebservice(org.zixing.util.his.call.Webservice value) {
        return new JAXBElement<org.zixing.util.his.call.Webservice>(_Webservice_QNAME, Webservice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebserviceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://common.ws.zxxy.com/", name = "webserviceResponse")
    public JAXBElement<WebserviceResponse> createWebserviceResponse(WebserviceResponse value) {
        return new JAXBElement<WebserviceResponse>(_WebserviceResponse_QNAME, WebserviceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link org.zixing.util.his.call.WsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://common.ws.zxxy.com/", name = "WsException")
    public JAXBElement<org.zixing.util.his.call.WsException> createWsException(org.zixing.util.his.call.WsException value) {
        return new JAXBElement<org.zixing.util.his.call.WsException>(_WsException_QNAME, WsException.class, null, value);
    }

}
