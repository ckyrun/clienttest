package org.exite.clienttest.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("key")
public class Key {

    @XStreamAlias("name")
    @XStreamAsAttribute
    public String name;

    @XStreamAlias("value")
    @XStreamAsAttribute
    public String value;

    @Override
    public String toString() {
        return "Key [name=" + name + ", value=" + value
                + "]";
    }

}
