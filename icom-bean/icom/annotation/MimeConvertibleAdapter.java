package icom.annotation;

import icom.MimeConvertible;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@XmlTransient
public class MimeConvertibleAdapter extends XmlAdapter<Object, MimeConvertible> {

    public Object marshal(MimeConvertible arg) throws Exception {
        return arg;
    }

    public MimeConvertible unmarshal(Object arg) throws Exception {
        return (MimeConvertible) arg;
    }

}