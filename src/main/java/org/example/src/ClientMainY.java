package org.example.src;

import org.example.src.entity.Client;
import org.example.src.services.ClientService;
import org.example.src.thread.ListenerThread;

import java.io.IOException;
import java.util.Scanner;

public class ClientMainY {
    public static void main(String[] args) {
        try{
            Client client = new Client("Pedro", "localhost", 9002, "arquivo2", "/home/yuto/Documentos/arquivosClienteY/");

            ClientService clientService = new ClientService();

            new Thread(new ListenerThread(client.getClient_port())).start();

            while(true) {
                System.out.println("1-JOIN | 2-SEARCH | 3-DOWNLOAD | 4-CANCELL");
                Scanner scanner = new Scanner(System.in);

                int menu_choice = scanner.nextInt();

                if(menu_choice == 1){
                    clientService.join(client);
                }
                else if(menu_choice == 2){
                    System.out.println("SEARCH: ");
                    String fileRequest = scanner.next();

                    client.setFile_request(fileRequest);

                    clientService.search(client);
                }
                else if(menu_choice == 3){
                    System.out.println("DOWNLOAD: ");
                    int destiny_port = scanner.nextInt();
                    String fileRequest = scanner.next();

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
