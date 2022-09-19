package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static final Logger logger = Logger.getLogger("Server");
    private static final Server server = new Server();

    public static void main(String[] args) throws IOException {
        File file = new File("texts.txt");

        if (!file.exists()) {
            if (file.createNewFile()) {
                logger.info("File created. Please, fill it and run the program.");
                System.exit(0);
                return;
            } else {
                throw new IOException("Cannot create a new file");
            }
        }

        if (!file.isFile())
            logger.severe("File doesnt exist");

        List<String> strings = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine())
            strings.add(scanner.nextLine());

        if (strings.isEmpty()) {
            logger.severe("File is empty, please fill it");
            System.exit(1);
            return;
        }

        Scanner scanner1 = new Scanner(System.in);

        System.out.println("Enter server port: ");
        int port = scanner1.nextInt();

        System.out.println("Enter time value (seconds): ");
        int time = scanner1.nextInt();

        server.start(strings, port, time);
    }

}