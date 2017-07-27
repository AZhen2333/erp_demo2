
package com.redsun.bos.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.redsun.bos.ws package. 
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

    private final static QName _AddWaybill_QNAME = new QName("http://ws.bos.redsun.com/", "addWaybill");
    private final static QName _WaybilldetailsList_QNAME = new QName("http://ws.bos.redsun.com/", "waybilldetailsList");
    private final static QName _AddWaybillResponse_QNAME = new QName("http://ws.bos.redsun.com/", "addWaybillResponse");
    private final static QName _WaybilldetailsListResponse_QNAME = new QName("http://ws.bos.redsun.com/", "waybilldetailsListResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.redsun.bos.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WaybilldetailsList }
     * 
     */
    public WaybilldetailsList createWaybilldetailsList() {
        return new WaybilldetailsList();
    }

    /**
     * Create an instance of {@link AddWaybill }
     * 
     */
    public AddWaybill createAddWaybill() {
        return new AddWaybill();
    }

    /**
     * Create an instance of {@link WaybilldetailsListResponse }
     * 
     */
    public WaybilldetailsListResponse createWaybilldetailsListResponse() {
        return new WaybilldetailsListResponse();
    }

    /**
     * Create an instance of {@link AddWaybillResponse }
     * 
     */
    public AddWaybillResponse createAddWaybillResponse() {
        return new AddWaybillResponse();
    }

    /**
     * Create an instance of {@link Waybilldetail }
     * 
     */
    public Waybilldetail createWaybilldetail() {
        return new Waybilldetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWaybill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "addWaybill")
    public JAXBElement<AddWaybill> createAddWaybill(AddWaybill value) {
        return new JAXBElement<AddWaybill>(_AddWaybill_QNAME, AddWaybill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WaybilldetailsList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "waybilldetailsList")
    public JAXBElement<WaybilldetailsList> createWaybilldetailsList(WaybilldetailsList value) {
        return new JAXBElement<WaybilldetailsList>(_WaybilldetailsList_QNAME, WaybilldetailsList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWaybillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "addWaybillResponse")
    public JAXBElement<AddWaybillResponse> createAddWaybillResponse(AddWaybillResponse value) {
        return new JAXBElement<AddWaybillResponse>(_AddWaybillResponse_QNAME, AddWaybillResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WaybilldetailsListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "waybilldetailsListResponse")
    public JAXBElement<WaybilldetailsListResponse> createWaybilldetailsListResponse(WaybilldetailsListResponse value) {
        return new JAXBElement<WaybilldetailsListResponse>(_WaybilldetailsListResponse_QNAME, WaybilldetailsListResponse.class, null, value);
    }

}
