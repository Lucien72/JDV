package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame{
    private JTextField messageField;
    private JTextArea chatArea;
    private BufferedReader in;
    private PrintWriter out;

    public ChatClient(String serverAddress, String pseudo){
        super("Chat Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sendMessage(e.getActionCommand());
                messageField.setText("");
            }
        });
        panel.add(messageField, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        try{
            Socket socket = new Socket(serverAddress, 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Créez un thread pour lire les messages du serveur en arrière-plan
            new Thread(new Runnable(){
                public void run(){
                    try{
                        String line;
                        while((line = in.readLine()) != null){
                            appendMessage(line);
                        }
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void sendMessage(String message){
        out.println(message);
    }

    // Méthode pour ajouter un message à la zone de chat
    private void appendMessage(String message){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                chatArea.append(message + "\n");
            }
        });
    }
}
