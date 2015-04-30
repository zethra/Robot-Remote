package com.zethratech.robotremote;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Zethra on 12/21/2014.
 */
public class Network {
    private Socket socket;
    private PrintWriter out;
    public String ip, message;
    public int port;

    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    out = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void send() {
        out.write(message);
    }
}
