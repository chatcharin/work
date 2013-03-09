/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailverifier;

/**
 *
 * @author Aek
 */

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.axiom.om.OMAttribute;

/**
 * This is a Client progam that accesses 'MyService' web service in Axis2 samples
 */
public class CallService {

    private static String toEpr = "http://localhost:8080/test/services/MStoMY";
   
    public String synRunJob() throws AxisFault {

        Options options = new Options();
        options.setTo(new EndpointReference(toEpr));
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);
        options.setAction("runJob");
        ServiceClient sender = new ServiceClient();
       // sender.engageModule(Constants.MODULE_ADDRESSING);
        sender.setOptions(options);
      //  System.out.println(createPayLoad(fulltext).toString());
        OMElement result = sender.sendReceive(createPayLoad());

        try {
            XMLStreamWriter writer = XMLOutputFactory.newInstance()
                    .createXMLStreamWriter(System.out);
            result.serialize(writer);
           
            writer.flush();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        }
        return "sucsess";
    }
    static  OMFactory fac = OMAbstractFactory.getOMFactory();
    static  OMNamespace omNs = fac.createOMNamespace(
                "http://talend.org", "ns1");
    static  OMFactory fac2 = OMAbstractFactory.getOMFactory();
    static  OMNamespace omNs2 = fac.createOMNamespace(
                "http://schemas.xmlsoap.org/soap/envelope/", "soapenv");

   private  OMElement createPayLoad() {
      
      OMElement soap = fac.createOMElement("Envelope", omNs2);
      OMElement body = fac.createOMElement("Body", omNs2);  
      soap.addChild(body);
      OMElement findPartiesById = fac.createOMElement("runJob", omNs);
      OMElement mapMap = fac.createOMElement("map-Map", null);

      findPartiesById.addChild(mapMap);

     // mapMap.addChild(createMapEntry("fulltext", fulltext));
     // mapMap.addChild(createMapEntry("login.username", "admin"));
     // mapMap.addChild(createMapEntry("login.password", "ofbiz"));
      body.addChild(findPartiesById);
      return soap;
   }

   private  OMElement createMapEntry(String key, String val) {

      OMElement mapEntry = fac.createOMElement("map-Entry",null);

      // create the key
      OMElement mapKey = fac.createOMElement("map-Key",null);
      OMElement keyElement = fac.createOMElement("std-String",null);
      OMAttribute keyAttribute = fac.createOMAttribute("value", null, key);

      mapKey.addChild(keyElement);
      keyElement.addAttribute(keyAttribute);

      // create the value
      OMElement mapValue = fac.createOMElement("map-Value", null);
      OMElement valElement = fac.createOMElement("std-String", null);
      OMAttribute valAttribute = fac.createOMAttribute("value", null, val);

      mapValue.addChild(valElement);
      valElement.addAttribute(valAttribute);

      // attach to map-Entry
      mapEntry.addChild(mapKey);
      mapEntry.addChild(mapValue);

      return mapEntry;
   }//salesChannel
}
