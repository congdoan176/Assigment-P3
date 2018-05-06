/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import entity.Champion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author honghung
 */
public class ClientThread extends Thread {

    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public ClientThread(Socket socket) {
        System.out.println(socket.getInetAddress().getHostAddress() + " đã kết nối.");
        this.socket = socket;
        try {
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Champion b = (Champion) this.ois.readObject();
                System.out.println(b.toString());
                Server.publicObject(b);
            } catch (IOException e) {
                System.out.println(socket.getInetAddress().getHostAddress() + " đã thoát.");
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
