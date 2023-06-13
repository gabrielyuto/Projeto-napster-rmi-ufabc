package org.example.src.thread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ThreadAtendimento extends Thread{
    private Socket no = null;

    public ThreadAtendimento(Socket node) {
        no = node;
    }

    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(no.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = no.getOutputStream();
            DataOutputStream writer = new DataOutputStream(outputStream);

            String texto = reader.readLine();
            writer.writeBytes(texto.toUpperCase() +"\n");
        } catch (Exception e){

        }
    }
}
