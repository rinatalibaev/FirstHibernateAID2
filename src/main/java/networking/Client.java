package networking;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	private final static int SOCKET_PORT = 13267; // you may change this
	private final static String SERVER = "127.0.0.1"; // localhost
	private File fileToSend = null;

	public void sendFile(File file) throws IOException {
		fileToSend = file;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		Socket sock = null;
		System.out.println("Client socket is null");
		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Client socket initialized");

			// send file
			// File myFile = new File(fileToSend);
			byte[] mybytearray = new byte[(int) fileToSend.length()];
			fis = new FileInputStream(fileToSend);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
			os = sock.getOutputStream();
			System.out.println("Sending " + fileToSend + "(" + mybytearray.length + " bytes)");
			System.out.println("File " + fileToSend + " uploaded (" + mybytearray.length + " bytes)");
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

	public int getSocketPort() {
		return SOCKET_PORT;
	}

	public String getServer() {
		return SERVER;
	}

	public void setFileToSend(File file) {
		fileToSend = file;
	}

	public File getFileToSend() {
		return fileToSend;
	}
}
