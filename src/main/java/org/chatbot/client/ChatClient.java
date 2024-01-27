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
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        System.out.println("Chatbot says: " + client.receive());
        System.out.println("Connected to chatbot. Type your messages:");
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String userInput;
            while (true) {
                System.out.print("You: ");
                userInput = userInputReader.readLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                client.send(userInput);
                String response = client.receive();

                if (response == null) {
                    System.out.println("Connection closed by server.");
                    break;
                }
                System.out.println("Chatbot says: " + response);
            }
        } finally {
            client.close();
        }
    }

}
