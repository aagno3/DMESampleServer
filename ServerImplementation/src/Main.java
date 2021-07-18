import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	
	public static int port;
	static Thread thread;
	static FileSeverImplementation fileSeverImplementation = new FileSeverImplementation();
	private static ServerSocket server;
	private static Socket socket;

	public static void main(String[] args) {
		port = Integer.parseInt(args[0]);
		try {
			File myObj = new File("filename.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();

        }
		
        // to accept multiple client
        while (true) {
            try {
                socket = server.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            FileSeverImplementation f = new FileSeverImplementation();
            f.initialize(socket);
            new Thread(f).start();
        }
	}

}
