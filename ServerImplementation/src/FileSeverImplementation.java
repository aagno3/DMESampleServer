import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileSeverImplementation implements Runnable {

	private DataInputStream in = null;
	private DataOutputStream dout = null;

	public void initialize(Socket socket) {
		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dout = new DataOutputStream(socket.getOutputStream());

		} catch (Exception e) {

		}
	}

	public void stopServer() {
		try {
			in.close();
			dout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("New thread started for a client");
		while (true) {
			BufferedWriter out = null;
			try {
				String l = in.readUTF();
				Scanner scan = new Scanner(l);
				String command = scan.next();
				

				if (command.equals("post")) {
					String line = scan.nextLine().trim();
					if (line != null && !line.isEmpty()) {
						FileWriter fstream = new FileWriter("filename.txt", true);
						out = new BufferedWriter(fstream);
						out.write(line);
						out.write("\n");
						out.close();
						System.out.println("write to file success");
						line = null;
					}
				} else if (command.equals("view")) {
					byte[] bytes = Files.readAllBytes(Paths.get("filename.txt"));
					String text = new String(bytes, StandardCharsets.UTF_8);
					dout.writeUTF(text);
					dout.flush();
					System.out.println("view from file success");
				}

			} catch (Exception i) {
				System.out.println(i);
			}
		}
	}
}
