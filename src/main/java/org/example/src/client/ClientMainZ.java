package org.example.src.client;

import org.example.src.services.ClientService;
import org.example.src.thread.ListenerThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientMainZ {
    public static void main(String[] args) {
        try{
            String local_path = "/home/yuto/Documentos/teste/arquivosPedro/";

            List<String> files = new ArrayList<>();
            files.add("figura.jpeg");

            Client client = new Client("Pedro", "127.0.0.1", 9003, files, local_path);

            ClientService clientService = new ClientService();

            new Thread(new ListenerThread(client.getClient_port())).start();

            String fileRequest = null;
            List<Client> listClients = null;

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

                    listClients = clientService.search(client);
                }
                else if(menu_choice == 3){
                    String finalFileRequest = fileRequest;
                    System.out.println("DOWNLOAD: ");
                    int destiny_port = scanner.nextInt();

                    client.setFile_request(fileRequest);
                    client.setDestiny_port(destiny_port);

                    List<Client> list =  listClients
                            .stream().filter(
                                    a -> a.getFile_required_return().equals(finalFileRequest)
                                            && a.getClient_port() == destiny_port).collect(Collectors.toList());

                    Client first = list.stream().findFirst().get();

                    client.setDestiny_path_files(first.getDestiny_path_files());

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
