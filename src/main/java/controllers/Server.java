package controllers;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public final static int SOCKET_PORT = 13267;
	public final static String FILE_TO_RECEIVED = "c:/temp/newSource2.pdf";
	public final static int FILE_SIZE = 2097152; // file size temporary hard
													// coded
													// should bigger than the
													// file to be downloaded

	public static void main(String[] args) throws IOException {

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
		}
	}

}