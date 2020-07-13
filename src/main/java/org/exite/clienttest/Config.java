package org.exite.clienttest;

import org.exite.clienttest.utils.ConfigLoader;
import org.exite.clienttest.utils.Properties;
import org.exite.clienttest.utils.Property;

public class Config {

    public static String SOCKET_HOST;
    public static int SOCKET_PORT;
    public static String SOCKET_OUT_FOLDER;
    public static int SLEEP_TIME;

    public static void loadConfig(final String path) {
        Properties props = ConfigLoader.getProperties(path);
        for (Property property : props.properties) {
            SOCKET_HOST = property.get("host").value;
            SOCKET_PORT = Integer.parseInt(property.get("port").value);
            SOCKET_OUT_FOLDER = property.get("out_folder").value;
            SLEEP_TIME = Integer.parseInt(property.get("sleep_time").value);
            }
        }

}
