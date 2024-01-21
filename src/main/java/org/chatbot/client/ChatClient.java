package org.chatbot.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final BufferedReader userInputReader;

    public ChatClient(String address, int port) throws Exception {
        socket = new Socket(address, port);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        userInputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void send(String message) { out.println(message); }
    public String receive() throws Exception { return in.readLine(); }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
        userInputReader.close();
    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient("localhost", 1234);

        System.out.println("Connected to chatbot. Type your messages:");

        while (true) {
            String userInput = client.userInputReader.readLine();
            client.send(userInput);
            System.out.println("Chatbot says: " + client.receive());
            if (userInput.equalsIgnoreCase("exit")) {
                client.close();
                break;
            }
        }
    }
}
