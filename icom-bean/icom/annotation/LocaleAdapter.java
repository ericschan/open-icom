package icom.annotation;

import java.util.Locale;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@XmlTransient
public class LocaleAdapter extends XmlAdapter<String, Locale> {

    public String marshal(Locale arg) throws Exception {
        return arg.getDisplayName();
    }

    public Locale unmarshal(String arg) throws Exception {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (locale.getDisplayName().equals(arg)) {
                return locale;
            }
        }
        for (Locale locale : locales) {
            if (arg.startsWith(locale.getDisplayName())) {
                return locale;
            }
        }
        return Locale.getDefault();
    }

}
