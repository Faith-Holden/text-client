package solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TextFileDirectoryClient {
    private static final int LISTENING_PORT = 3000;

    public static void main(String[] args){
        String hostName;
        Socket connection;

        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter computer name or IP address: ");
        hostName = userInput.nextLine();

        try {
            connection = new Socket(hostName, LISTENING_PORT);
            BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter outgoing = new PrintWriter(connection.getOutputStream());

            System.out.println("Connected.");

            while (true){
                System.out.println("\nPlease type a request(GET <filename> or INDEX), or type DONE if done");
                String userRequest = userInput.nextLine();
                if(userRequest.equals("DONE")){
                    break;
                }else if (userRequest.equals("INDEX")||userRequest.startsWith("GET ")){
                    outgoing.println(userRequest);
                    outgoing.flush();
                    String lineFromServer = incoming.readLine();
                    while(lineFromServer!=null){
                        System.out.println(lineFromServer);
                        lineFromServer = incoming.readLine();
                    }
                }else{
                    System.out.println("Sorry, " + userRequest + " is not a valid input.");
                }
            }
            incoming.close();
        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
