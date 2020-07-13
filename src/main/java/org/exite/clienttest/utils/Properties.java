package org.exite.clienttest.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("properties")
public class Properties {

    @XStreamAlias("property")
    @XStreamImplicit
    public List<Property> properties = new ArrayList<>();

}
