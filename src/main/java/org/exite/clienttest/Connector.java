package org.exite.clienttest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;

public class Connector {

    private static final Logger log = Logger.getLogger(Connector.class);
    private boolean executing = true;

    public Connector() {
        registerShutdownHook(Thread.currentThread());
    }

    public void startProcessing() throws InterruptedException {

        while (executing) {
            List<String> listOutFiles = new ArrayList<>();

            File dir = new File(Config.SOCKET_OUT_FOLDER);
            File[] listFiles = dir.listFiles();

            if (listFiles != null) {
                for (File item : listFiles) {
                    String itemFile = item.getName();
                    listOutFiles.add(itemFile);
                }
            }

            if (!listOutFiles.isEmpty()) {
                serverConnect(listOutFiles);
            }
            listOutFiles.clear();
            Thread.sleep(Config.SLEEP_TIME * 1000);
        }

    }

    public void serverConnect(List<String> listOutFiles) {

        try(Socket socket = new Socket(Config.SOCKET_HOST, Config.SOCKET_PORT);
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()))
        {

            if(!socket.isOutputShutdown()) {

                for(String fileName : listOutFiles) {

                    String typeDoc = fileName.split("_")[0];

                    FileInputStream fin = new FileInputStream(Config.SOCKET_OUT_FOLDER + "\\" + fileName);
                    byte[] buffer = new byte[fin.available()];
                    fin.read(buffer, 0, fin.available());
                    fin.close();

                    String body = Base64.getEncoder().encodeToString(buffer);

                    Map<String, String> arFiles = new HashMap<>();
                    arFiles.put("typeDoc", typeDoc);
                    arFiles.put("fileName", fileName);
                    arFiles.put("bodyDoc", body);

                    Gson gson = new Gson();
                    String jString = gson.toJson(arFiles);

                    oos.writeUTF(jString);
                    log.info("Отправлено сообщение " + fileName);
                    oos.flush();

                    Thread.sleep(Config.SLEEP_TIME * 1000);

                    String in = ois.readUTF();
                    Type type = new TypeToken<Map<String, String>>(){}.getType();

                    Map<String, String> request = gson.fromJson(in, type);
                    String code = request.get("code");
                    String message = request.get("message");
                    fileName = request.get("fileName");
                    if (code.equals("200")) {
                       log.info("Принято сообщение " + fileName + ". " + message);
                       File file = new File(Config.SOCKET_OUT_FOLDER + "\\" + fileName);
                       if(file.delete()) {
                          log.info("Удален файл " + fileName);
                       }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void registerShutdownHook(final Thread mainThread) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received EXIT_SIGNAL");
            try {
                executing = false;
                mainThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

}
