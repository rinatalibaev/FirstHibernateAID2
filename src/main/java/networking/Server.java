package networking;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private final static int SOCKET_PORT = 13267;
	private static String FILE_TO_RECEIVED = "c:/temp/";
	private static String FILE_NAME = null;
	private static int FILE_SIZE = 2097152; // file size temporary hard
	// coded
	// should bigger than the
	// file to be downloaded

	public static void main(String[] args) {
	}

	public static void receive() throws IOException {
		FILE_TO_RECEIVED = FILE_TO_RECEIVED + FILE_NAME;
		int bytesRead;
		int current = 0;
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		ServerSocket servsock = null;
		Socket sock = null;
		try {
			servsock = new ServerSocket(SOCKET_PORT);
			System.out.println("Waiting for connection...");
			sock = servsock.accept();
			System.out.println("Connected");
			// receive file
			byte[] mybytearray = new byte[FILE_SIZE];
			InputStream inputStream = sock.getInputStream();
			fileOutputStream = new FileOutputStream(FILE_TO_RECEIVED);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;

			do {
				bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			} while (bytesRead > -1);

			bufferedOutputStream.write(mybytearray, 0, current);
			bufferedOutputStream.flush();

		} finally {
			if (fileOutputStream != null)
				fileOutputStream.close();
			if (bufferedOutputStream != null)
				bufferedOutputStream.close();
			if (sock != null)
				sock.close();
			if (servsock != null)
				servsock.close();
		}
	}

	public int getSOCKET_PORT() {
		return SOCKET_PORT;
	}

	public String getFILE_TO_RECEIVED() {
		return FILE_TO_RECEIVED;
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public int getFILE_SIZE() {
		return FILE_SIZE;
	}

	public void setFILE_TO_RECEIVED(String fILE_TO_RECEIVED) {
		FILE_TO_RECEIVED = fILE_TO_RECEIVED;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	@Override
	public void run() {
		try {
			receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}