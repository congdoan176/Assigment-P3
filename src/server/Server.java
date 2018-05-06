/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import entity.Champion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author honghung
 */
public class Server {

    private static ArrayList<ClientThread> listClient;

    public static void main(String[] args) throws IOException {
        listClient = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(6000);
        System.out.println("Server đã mở, chờ client kết nối.");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client với ip " + socket.getInetAddress().getHostAddress() + " đã kết nối.");
            ClientThread ct = new ClientThread(socket);
            ct.start();
            listClient.add(ct);
        }
    }
    
    public static void publicObject(Champion bird) {
        try {
            for (ClientThread clientThread : listClient) {
                if (clientThread.getSocket().isConnected()) {
                    clientThread.getOos().writeObject(bird);
                    clientThread.getOos().flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
