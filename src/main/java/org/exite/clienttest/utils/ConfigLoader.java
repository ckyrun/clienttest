package org.exite.clienttest.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;

public class ConfigLoader {
    public static Properties getProperties(String path) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("properties", Properties.class);
        xstream.autodetectAnnotations(true);
        return (Properties) xstream.fromXML(new File(path));
    }
}
