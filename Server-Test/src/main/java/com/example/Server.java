package com.example;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server {

    public static final Logger logger = Main.logger;

    private final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start(List<String> strings, int port, int period) throws IOException {
        ServerSocket srvSocket = new ServerSocket(port);
        logger.info("Server started, ServerSocket awaiting connections...");

        scheduler.scheduleAtFixedRate(() -> {
            try {
                Socket socket = srvSocket.accept();
                logger.info("Connecting to " + socket + "!");

                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                String str = strings.get(random.nextInt(strings.size()));
                logger.info("Sending string to the 'Client', content: " + str);

                dataOutputStream.writeUTF(str);
                dataOutputStream.flush();
            } catch (IOException ex) {
                logger.severe("Program returned an error, most likely socket is closed");
            }
        }, 1, period, TimeUnit.SECONDS);
    }

}
