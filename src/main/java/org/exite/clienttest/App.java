package org.exite.clienttest;

import org.apache.log4j.Logger;

public class App {

    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        if (args != null && args.length == 1) {
            Config.loadConfig(args[0]);
        new Connector().startProcessing();
        }
        else {
            log.info("One argument expected: path to config");
            System.exit(0);
        }
    }

}
