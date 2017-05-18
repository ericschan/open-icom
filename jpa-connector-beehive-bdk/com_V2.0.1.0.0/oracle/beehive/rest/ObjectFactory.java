//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.27 at 04:52:46 PM PST 
//


package com.oracle.beehive.rest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.oracle.beehive.rest package. 
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

    private final static QName _InvalidHeaderFailure_QNAME = new QName("http://www.oracle.com/beehive/rest", "invalidHeaderFailure");
    private final static QName _InvalidPayloadFailure_QNAME = new QName("http://www.oracle.com/beehive/rest", "invalidPayloadFailure");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle.beehive.rest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InternalFailure }
     * 
     */
    public InternalFailure createInternalFailure() {
        return new InternalFailure();
    }

    /**
     * Create an instance of {@link DebugInfo }
     * 
     */
    public DebugInfo createDebugInfo() {
        return new DebugInfo();
    }

    /**
     * Create an instance of {@link NoSuchEntityFailure }
     * 
     */
    public NoSuchEntityFailure createNoSuchEntityFailure() {
        return new NoSuchEntityFailure();
    }

    /**
     * Create an instance of {@link DuplicateEntityFailure }
     * 
     */
    public DuplicateEntityFailure createDuplicateEntityFailure() {
        return new DuplicateEntityFailure();
    }

    /**
     * Create an instance of {@link ConnectionLimitReachedFailure }
     * 
     */
    public ConnectionLimitReachedFailure createConnectionLimitReachedFailure() {
        return new ConnectionLimitReachedFailure();
    }

    /**
     * Create an instance of {@link InvalidHeaderFailure }
     * 
     */
    public InvalidHeaderFailure createInvalidHeaderFailure() {
        return new InvalidHeaderFailure();
    }

    /**
     * Create an instance of {@link LockedEntityFailure }
     * 
     */
    public LockedEntityFailure createLockedEntityFailure() {
        return new LockedEntityFailure();
    }

    /**
     * Create an instance of {@link InvalidUploadScopeFailure }
     * 
     */
    public InvalidUploadScopeFailure createInvalidUploadScopeFailure() {
        return new InvalidUploadScopeFailure();
    }

    /**
     * Create an instance of {@link BatchSizeExceededFailure }
     * 
     */
    public BatchSizeExceededFailure createBatchSizeExceededFailure() {
        return new BatchSizeExceededFailure();
    }

    /**
     * Create an instance of {@link InvalidQueryParameterValueFailure }
     * 
     */
    public InvalidQueryParameterValueFailure createInvalidQueryParameterValueFailure() {
        return new InvalidQueryParameterValueFailure();
    }

    /**
     * Create an instance of {@link MissingPayloadFailure }
     * 
     */
    public MissingPayloadFailure createMissingPayloadFailure() {
        return new MissingPayloadFailure();
    }

    /**
     * Create an instance of {@link UnsupportedHTTPMethodFailure }
     * 
     */
    public UnsupportedHTTPMethodFailure createUnsupportedHTTPMethodFailure() {
        return new UnsupportedHTTPMethodFailure();
    }

    /**
     * Create an instance of {@link ServerConfig }
     * 
     */
    public ServerConfig createServerConfig() {
        return new ServerConfig();
    }

    /**
     * Create an instance of {@link InvalidPayloadFailure }
     * 
     */
    public InvalidPayloadFailure createInvalidPayloadFailure() {
        return new InvalidPayloadFailure();
    }

    /**
     * Create an instance of {@link InvalidIdFormatFailure }
     * 
     */
    public InvalidIdFormatFailure createInvalidIdFormatFailure() {
        return new InvalidIdFormatFailure();
    }

    /**
     * Create an instance of {@link DuplicateConnectionIdFailure }
     * 
     */
    public DuplicateConnectionIdFailure createDuplicateConnectionIdFailure() {
        return new DuplicateConnectionIdFailure();
    }

    /**
     * Create an instance of {@link NonWatchableEntityFailure }
     * 
     */
    public NonWatchableEntityFailure createNonWatchableEntityFailure() {
        return new NonWatchableEntityFailure();
    }

    /**
     * Create an instance of {@link BatchOperationPartialFailure }
     * 
     */
    public BatchOperationPartialFailure createBatchOperationPartialFailure() {
        return new BatchOperationPartialFailure();
    }

    /**
     * Create an instance of {@link FailedId }
     * 
     */
    public FailedId createFailedId() {
        return new FailedId();
    }

    /**
     * Create an instance of {@link MissingRequiredHeaderFailure }
     * 
     */
    public MissingRequiredHeaderFailure createMissingRequiredHeaderFailure() {
        return new MissingRequiredHeaderFailure();
    }

    /**
     * Create an instance of {@link UnsupportedAcceptHeaderFailure }
     * 
     */
    public UnsupportedAcceptHeaderFailure createUnsupportedAcceptHeaderFailure() {
        return new UnsupportedAcceptHeaderFailure();
    }

    /**
     * Create an instance of {@link AntiCSRF }
     * 
     */
    public AntiCSRF createAntiCSRF() {
        return new AntiCSRF();
    }

    /**
     * Create an instance of {@link InvalidUriFailure }
     * 
     */
    public InvalidUriFailure createInvalidUriFailure() {
        return new InvalidUriFailure();
    }

    /**
     * Create an instance of {@link FileUploadSuccess }
     * 
     */
    public FileUploadSuccess createFileUploadSuccess() {
        return new FileUploadSuccess();
    }

    /**
     * Create an instance of {@link AccessDeniedFailure }
     * 
     */
    public AccessDeniedFailure createAccessDeniedFailure() {
        return new AccessDeniedFailure();
    }

    /**
     * Create an instance of {@link JsonTypeMismatch }
     * 
     */
    public JsonTypeMismatch createJsonTypeMismatch() {
        return new JsonTypeMismatch();
    }

    /**
     * Create an instance of {@link UnsupportedContentTypeHeaderFailure }
     * 
     */
    public UnsupportedContentTypeHeaderFailure createUnsupportedContentTypeHeaderFailure() {
        return new UnsupportedContentTypeHeaderFailure();
    }

    /**
     * Create an instance of {@link NoEventSessionFailure }
     * 
     */
    public NoEventSessionFailure createNoEventSessionFailure() {
        return new NoEventSessionFailure();
    }

    /**
     * Create an instance of {@link ServerTooBusyFailure }
     * 
     */
    public ServerTooBusyFailure createServerTooBusyFailure() {
        return new ServerTooBusyFailure();
    }

    /**
     * Create an instance of {@link ImpersonationFailure }
     * 
     */
    public ImpersonationFailure createImpersonationFailure() {
        return new ImpersonationFailure();
    }

    /**
     * Create an instance of {@link MissingQueryParameterFailure }
     * 
     */
    public MissingQueryParameterFailure createMissingQueryParameterFailure() {
        return new MissingQueryParameterFailure();
    }

    /**
     * Create an instance of {@link InvalidPayloadTypeFailure }
     * 
     */
    public InvalidPayloadTypeFailure createInvalidPayloadTypeFailure() {
        return new InvalidPayloadTypeFailure();
    }

    /**
     * Create an instance of {@link UploadFailure }
     * 
     */
    public UploadFailure createUploadFailure() {
        return new UploadFailure();
    }

    /**
     * Create an instance of {@link InvalidIdTypeFailure }
     * 
     */
    public InvalidIdTypeFailure createInvalidIdTypeFailure() {
        return new InvalidIdTypeFailure();
    }

    /**
     * Create an instance of {@link NoSuchConnectionFailure }
     * 
     */
    public NoSuchConnectionFailure createNoSuchConnectionFailure() {
        return new NoSuchConnectionFailure();
    }

    /**
     * Create an instance of {@link InvalidContentIdFailure }
     * 
     */
    public InvalidContentIdFailure createInvalidContentIdFailure() {
        return new InvalidContentIdFailure();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidHeaderFailure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com/beehive/rest", name = "invalidHeaderFailure")
    public JAXBElement<InvalidHeaderFailure> createInvalidHeaderFailure(InvalidHeaderFailure value) {
        return new JAXBElement<InvalidHeaderFailure>(_InvalidHeaderFailure_QNAME, InvalidHeaderFailure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidPayloadFailure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com/beehive/rest", name = "invalidPayloadFailure")
    public JAXBElement<InvalidPayloadFailure> createInvalidPayloadFailure(InvalidPayloadFailure value) {
        return new JAXBElement<InvalidPayloadFailure>(_InvalidPayloadFailure_QNAME, InvalidPayloadFailure.class, null, value);
    }

}
