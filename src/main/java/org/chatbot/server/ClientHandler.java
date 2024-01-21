package org.chatbot.server;

import org.chatbot.database.DatabaseConnection;
import org.chatbot.logic.ChatbotLogic;
import org.chatbot.response.Response;
import org.chatbot.response.ResponseType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ChatbotLogic chatbotLogic;

    public ClientHandler(Socket socket) throws IOException, SQLException {
        this.clientSocket = socket;
        this.chatbotLogic = new ChatbotLogic(new DatabaseConnection("jdbc:mysql://localhost:3306/chatbot_db", "root", "QpmzwonX"));
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            Response welcomeResponse = chatbotLogic.processInput("");
            out.println(welcomeResponse.getMessage());

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Response response = chatbotLogic.processInput(inputLine);
                out.println(response.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatbotLogic.exit();
        }
    }
}
