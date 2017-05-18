package icom.jpa.bdk;

import icom.ContentStreamTrait;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.persistence.PersistenceException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.oracle.beehive.RestFault;
import com.oracle.beehive.rest.Failure;

public class BdkHttpUtil {
	
	static BdkHttpUtil singleton = new BdkHttpUtil();
	
	public static BdkHttpUtil getInstance() {
		return singleton;
	}

	static final Header contentType = new Header();
	static final Header accept = new Header();

	static {
		accept.setName("Accept");
		accept.setValue("application/xml");
		contentType.setName("Content-Type");
		contentType.setValue("application/xml");
	}
	
	public Object execute(Class<?> clazz, EntityEnclosingMethod method,	Object objToMarshal, HttpClient httpClient) throws Exception {
		//addHeader(method);
		JAXBQueue jaxbPool = JAXBQueue.getInstance();
		StringWriter sw = new StringWriter();
		Marshaller m = null;
		try {
			m = jaxbPool.getMarshaller();
			m.marshal(objToMarshal, sw);
		} finally {
			jaxbPool.putMarshaller(m);
		}
		RequestEntity requestEntity = new ByteArrayRequestEntity(sw.toString().getBytes());
		method.setRequestEntity(requestEntity);
		return execute(clazz, method, httpClient);
	}

	public Object execute(Class<?> clazz, HttpMethodBase method, HttpClient httpClient) throws Exception {
		addHeader(method);
		try {
			httpClient.executeMethod(method);
			InputStream in = method.getResponseBodyAsStream();
			if (in != null) {
				return unmarshall(clazz, in);
			} else {
				return null;
			}
		} finally {
			method.releaseConnection();
		}
	}
	
	public void execute(OutputStream outputStream, HttpMethodBase method, HttpClient httpClient) throws Exception {
		addHeader(method);
		try {
			httpClient.executeMethod(method);
			InputStream inputStream = method.getResponseBodyAsStream();
			int readDataLength = 0;
			byte[] data = new byte[ContentStreamTrait.dataSize];
			do {
		        readDataLength = inputStream.read(data);
				if (readDataLength > 0) {
					outputStream.write(data, 0, readDataLength);
				}
	        } while (readDataLength > 0);
			inputStream.close();
			outputStream.flush();
			outputStream.close();
		} finally {
			method.releaseConnection();
		}
	}
	
	public void execute(HttpMethodBase method, HttpClient httpClient) throws Exception {
		addHeader(method);
		try {
			httpClient.executeMethod(method);
		} finally {
			method.releaseConnection();
		}
	}
	
	public void execute(EntityEnclosingMethod method, Object objToMarshal, HttpClient httpClient) throws Exception {
		addHeader(method);
		JAXBQueue jaxbPool = JAXBQueue.getInstance();
		StringWriter sw = new StringWriter();
		Marshaller m = null;
		try {
			m = jaxbPool.getMarshaller();
			m.marshal(objToMarshal, sw);
		} finally {
			jaxbPool.putMarshaller(m);
		}
		RequestEntity requestEntity = new ByteArrayRequestEntity(sw.toString().getBytes());
		method.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(method);
		} finally {
			method.releaseConnection();
		}
	}
	
	public void upLoadContent(PostMethod postMethod, HttpClient httpClient) throws Exception {
		postMethod.addRequestHeader(accept);
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_CREATED) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
				throw new Exception("Upload Failed");
			}
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	Object unmarshall(Class<?> clazz, InputStream in) throws Exception {
		JAXBQueue jaxbPool = JAXBQueue.getInstance();
		Unmarshaller um = null;
		Object uncheckedobj = null;
		try {
			um = jaxbPool.getUnmarshaller();
			uncheckedobj = um.unmarshal(in);
		} catch (Exception ex) {
		    throw ex;
		} finally {
			jaxbPool.putUnmarshaller(um);
		}

		// if unmarshalled object is JAXBElement, get the value.
		if (uncheckedobj.getClass().equals(JAXBElement.class)) {
			uncheckedobj = ((JAXBElement<?>) uncheckedobj).getValue();
		}
		
		if (uncheckedobj instanceof RestFault) {
			System.out.print(uncheckedobj);
			StringBuffer buffer = new StringBuffer();
			buffer.append(((RestFault)uncheckedobj).getFault().getCause() + "\n");
			buffer.append(((RestFault)uncheckedobj).getFault().getEffect() + "\n");
			buffer.append(((RestFault)uncheckedobj).getFault().getAction());
			String message = buffer.toString();
			System.out.print(message);
			throw new PersistenceException(message);
		}
		
		if (uncheckedobj instanceof Failure) {
			System.out.print(uncheckedobj);
			System.out.print(((Failure)uncheckedobj).getMessage());
			throw new PersistenceException(((Failure)uncheckedobj).getMessage());
		}

		// Check if the object can be cast to clazz.
		try {
			clazz.cast(uncheckedobj);
			return uncheckedobj;
		} catch (ClassCastException ex) {
			System.out.print(uncheckedobj);
			ex.printStackTrace();
			throw new PersistenceException(ex);
		}
	}
	
	void addHeader(HttpMethodBase method) {
		if (!method.getClass().equals(GetMethod.class)) {
			method.addRequestHeader(contentType);
		}
		method.addRequestHeader(accept);
	}
	
}
