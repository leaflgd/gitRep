package org.zixing.util.his.call;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 3.2.6
 * 2018-08-28T10:57:59.242+08:00
 * Generated source version: 3.2.6
 *
 */
@WebServiceClient(name = "WebServicePublishService",
                  wsdlLocation = org.zixing.util.his.call.WsZxBaseInfo.wsdlLocation,
                  targetNamespace = "http://common.ws.zxxy.com/")
public class WebServicePublishService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://common.ws.zxxy.com/", "WebServicePublishService");
    public final static QName WebServicePublishPort = new QName("http://common.ws.zxxy.com/", "WebServicePublishPort");
    static {
        URL url = null;
        try {
            url = new URL(org.zixing.util.his.call.WsZxBaseInfo.wsdlLocation);
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(WebServicePublishService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", WsZxBaseInfo.wsdlLocation);
        }
        WSDL_LOCATION = url;
    }

    public WebServicePublishService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public WebServicePublishService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WebServicePublishService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public WebServicePublishService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public WebServicePublishService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public WebServicePublishService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns WebServiceInterface
     */
    @WebEndpoint(name = "WebServicePublishPort")
    public org.zixing.util.his.call.WebServiceInterface getWebServicePublishPort() {
        return super.getPort(WebServicePublishPort, org.zixing.util.his.call.WebServiceInterface.class);
    }

    /**
     *
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebServiceInterface
     */
    @WebEndpoint(name = "WebServicePublishPort")
    public org.zixing.util.his.call.WebServiceInterface getWebServicePublishPort(WebServiceFeature... features) {
        return super.getPort(WebServicePublishPort, WebServiceInterface.class, features);
    }

}
