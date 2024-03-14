package sockets;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ClientHandler extends Thread{
    private Socket clientSocket;
    private static ArrayList<PrintWriter> clientOutputStreams = new ArrayList<>();

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
    }

    private void handleCommand(String command){
        String[] parts = command.split(" ");
        String keyword = parts[0].toLowerCase(); 
        // Obtenez le mot clé de la commande (par exemple, /r)
        
        switch(keyword){
            case "/r":
                // Exécuter une action spécifique pour la commande /r
                if(parts.length >= 2){
                    int parameter = Integer.parseInt(parts[1]); 
                    // Récupérez le paramètre (par exemple, 10)
                    
                    // Faites quelque chose avec le paramètre (par exemple, changer une configuration)
                }
                break;
            // Ajoutez d'autres cas pour d'autres commandes si nécessaire
            default:
                // Gérer les commandes inconnues ou non prises en charge
                break;
        }
    }

    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Ajouter le flux de sortie du client à la liste
            clientOutputStreams.add(out);

            String inputLine;
            while((inputLine = in.readLine()) != null){
                // Traitez le message reçu
                if(inputLine.startsWith("/")){
                    // Si le message commence par "/", considérez-le comme une commande
                    handleCommand(inputLine);
                }
                else{
                    // Envoyez le message aux autres clients
                    for(PrintWriter writer : clientOutputStreams){
                        writer.println("\{pseudo}:" + inputLine);
                    }
                }
            }

            // clientSocket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}