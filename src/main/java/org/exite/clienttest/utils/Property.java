package org.exite.clienttest.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("property")
public class Property {

    @XStreamAlias("name")
    @XStreamAsAttribute
    public String name;

    @XStreamAlias("value")
    @XStreamAsAttribute
    public String value;

    @XStreamImplicit(itemFieldName = "key")
    public List<Key> keys = new ArrayList<>();

    public Key get(String name) {
        for (Key key : keys) {
            if(key.name.equals(name))
                return key;
        }

        return null;
    }
    @Override
    public String toString() {
        return "Property [keys=" + keys + ", name=" + name + ", value="
                + value + "]";
    }

}
