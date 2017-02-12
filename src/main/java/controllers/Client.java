package controllers;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public final static int SOCKET_PORT = 13267; // you may change this
	public final static String SERVER = "127.0.0.1"; // localhost
	public final static String FILE_TO_SEND = "c:/Users/alibaev/workspace/socketArtifactID/src/main/java/documents/Aikin.pdf";

	public static void main(String[] args) throws IOException {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		Socket sock = null;
		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connecting...");

			// send file
			File myFile = new File(FILE_TO_SEND);
			byte[] mybytearray = new byte[(int) myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
			os = sock.getOutputStream();
			System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
			System.out.println("File " + FILE_TO_SEND + " uploaded (" + mybytearray.length + " bytes)");
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();
			System.out.println("Done.");
		} finally {
			if (bis != null)
				bis.close();
			if (os != null)
				os.close();
			if (sock != null)
				sock.close();
		}
	}
}
