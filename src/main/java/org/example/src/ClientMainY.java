package org.example.src;

import org.example.src.client.Client;
import org.example.src.services.ClientService;
import org.example.src.thread.ListenerThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientMainY {
    public static void main(String[] args) {
        try{
            String local_path = "/home/yuto/Documentos/teste/arquivosGiovanna/";

            List<String> files = new ArrayList<>();
            files.add("arquivo.jpg");

            Client client = new Client("Gabriel", "127.0.0.1", 9001, files, local_path);

            ClientService clientService = new ClientService();

            new Thread(new ListenerThread(client.getClient_port())).start();

            String fileRequest = null;
            List<Client> listClients = null;

            while(true) {
                System.out.println("1-JOIN | 2-SEARCH | 3-DOWNLOAD");
                Scanner scanner = new Scanner(System.in);

                int menu_choice = scanner.nextInt();

                if(menu_choice == 1){
                    System.out.println("JOIN:");
                    clientService.join(client);
                }
                else if(menu_choice == 2){
                    System.out.println("SEARCH");
                    System.out.println("Digite o arquivo que deseja buscar: ");
                    fileRequest = scanner.next();

                    client.setFile_request(fileRequest);

                    listClients = clientService.search(client);
                }
                else if(menu_choice == 3){
                    System.out.println("DOWNLOAD:");
                    System.out.println("Digite a porta que deseja solicitar o arquivo: ");
                    String finalFileRequest = fileRequest;
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
