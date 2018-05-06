/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import entity.Champion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author honghung
 */
public class GameClient extends Application {
    
    private long clientId;
    private final String HOST = "localhost";
    private final int PORT = 6000;
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private GraphicsContext gc;
    private Image face;

    private int minX = 230;
    private int minY = 230;
    private int width = 80;
    private int height = 80;
    private final int step = 5;
    private final String imageUrl = "https://orig00.deviantart.net/fe53/f/2016/365/7/2/knockout_lee_sin_by_kawailemon-datijjm.png";

    @Override
    public void start(Stage theStage) throws Exception {
        
        this.clientId = System.currentTimeMillis();
        this.socket = new Socket(HOST, PORT);
        try {
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            GameClient.ChatClientReaderThread ccrt = new GameClient.ChatClientReaderThread();
            ccrt.start();
//            this.bw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(700, 350);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        face = new Image(
                imageUrl,
                this.width, this.height, false, false);
        gc.drawImage(face, this.minX, this.minY);
        Champion p = new Champion(this.clientId,
                imageUrl, this.minX, this.minY, this.width, this.height, this.minX, this.minY);
        this.oos.writeObject(p);
        this.oos.flush();

        theScene.setOnKeyPressed((event) -> {

            if (null != event.getCode()) {
                switch (event.getCode()) {
                    case RIGHT:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minX += this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    case LEFT:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minX -= this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    case UP:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minY -= this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    case DOWN:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minY += this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    default:
                        break;
                }
                try {
                    this.oos.writeObject(new Champion(this.clientId,
                            imageUrl, this.minX, this.minY, this.width, this.height, 10,10));
                    this.oos.flush();
                } catch (IOException e) {

                }
            }
        });
        theStage.show();
    }
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    class ChatClientReaderThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Champion b = (Champion) ois.readObject();
                    if (b.getId() == clientId) {
                        gc.clearRect(b.getOx(), b.getOy(), b.getW(), b.getH());
                        gc.drawImage(face, b.getX(), b.getY());
                    } else {
                        face = new Image(
                                b.getImg(),
                                b.getW(), b.getH(), false, false);
                        gc.clearRect(b.getOx(), b.getOy(), b.getW(), b.getH());
                        gc.drawImage(face, b.getX(), b.getY());
                    }
                } catch (IOException e) {
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
