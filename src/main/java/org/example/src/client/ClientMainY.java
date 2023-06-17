package org.example.src.client;

import org.example.src.services.ClientService;
import org.example.src.thread.ListenerThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientMainY {
    public static void main(String[] args) {
        try{
            List<String> files = new ArrayList<>();
            files.add("sistemas.mp3");
            files.add("cinema.mp4");
            files.add("computacao.mp3");

            Client client = new Client("Giovanna", "127.0.0.1", 9004, files, "/home/yuto/Documentos/teste/arquivosGiovanna/");

            ClientService clientService = new ClientService();

            new Thread(new ListenerThread(client.getClient_port())).start();

            String fileRequest = null;

            while(true) {
                System.out.println("1-JOIN | 2-SEARCH | 3-DOWNLOAD | 4-CANCELL");
                Scanner scanner = new Scanner(System.in);

                int menu_choice = scanner.nextInt();

                if(menu_choice == 1){
                    clientService.join(client);
                }
                else if(menu_choice == 2){
                    System.out.println("SEARCH: ");
                    fileRequest = scanner.next();

                    client.setFile_request(fileRequest);

                    clientService.search(client);
                }
                else if(menu_choice == 3){
                    System.out.println("DOWNLOAD: ");
                    int destiny_port = scanner.nextInt();

                    client.setFile_request(fileRequest);
                    client.setDestiny_port(destiny_port);

                    clientService.download(client);
                }
                else {
                    System.exit(0);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
