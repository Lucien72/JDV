package sockets;

import java.io.*;
import java.net.*;

public class Server{
public static void main(String[] args) throws IOException{
        try(ServerSocket serverSocket = new ServerSocket(12345)){
            while(true){
                // Attend une connexion
                Socket clientSocket = serverSocket.accept();
                System.out.println("Utilisateur connecté");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        }
    }
}