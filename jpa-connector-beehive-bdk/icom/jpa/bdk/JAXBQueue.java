package icom.jpa.bdk;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBQueue {
	
	static JAXBQueue singleton = new JAXBQueue();
	
	public static JAXBQueue getInstance() {
		return singleton;
	}
	
	private JAXBContext context;

	private Queue<Marshaller> marshallerQueue;
	private Queue<Unmarshaller> unmarshallerQueue;

	JAXBQueue()  {
		marshallerQueue = new ConcurrentLinkedQueue<Marshaller>();
		unmarshallerQueue =  new ConcurrentLinkedQueue<Unmarshaller>();
	}

	Marshaller getMarshaller() throws JAXBException {
		Marshaller m = marshallerQueue.poll();
		if (m == null) {
			m = getContext().createMarshaller();
		}
		return m;
	}

	Unmarshaller getUnmarshaller() throws JAXBException {
		Unmarshaller um = unmarshallerQueue.poll();
		if (um == null) {
			um = getContext().createUnmarshaller();
		}
		return um;
	}

	void putMarshaller(Marshaller m) {
		if (m != null) {
			marshallerQueue.add(m);
		}
	}

	void putUnmarshaller(Unmarshaller um) {
		if (um != null) {
			unmarshallerQueue.add(um);
		}
	}

	JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance("com.oracle.beehive:com.oracle.beehive.rest");
		}
		return context;
	}
	
}
