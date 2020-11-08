import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    public Server() {
        try {
            System.out.println("Server is starting up...");
            serverSocket = new ServerSocket(8888);

            System.out.println("Server is waiting for a connection...");
            clientSocket = serverSocket.accept();

            System.out.println("Client connected: " + clientSocket);

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            new Thread(() -> {
                try {
                    while(true) {
                        String incomingMessage = in.readUTF();
                        if (incomingMessage.contains("-exit")) {
                            out.writeUTF("cmd Exit");
                            break;
                        }

                        System.out.println("Client: " + incomingMessage);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }).start();


            Scanner scanner = new Scanner(System.in);
            while (true) {



                System.out.println("Please type in a message");
                out.writeUTF(scanner.nextLine());

                }






        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Server server = new Server();


    }
}
