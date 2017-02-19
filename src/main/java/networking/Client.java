package networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	private final static int SOCKET_PORT = 13267; // you may change this
	private final static String SERVER = "127.0.0.1"; // localhost
	private String FILE_TO_RECEIVED = "C:/Users/alibaev/Downloads";
	private String FILE_NAME;
	private int FILE_SIZE = 2097152;
	private File fileToSend = null;
	private File fileToReceive = null;

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

	public String receiveFile(String fileName) throws IOException {
		FILE_TO_RECEIVED = FILE_TO_RECEIVED + fileName;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		InputStream is = null;
		Socket sock = null;
		int bytesRead;
		int current = 0;
		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			byte[] mybytearray = new byte[FILE_SIZE];
			is = sock.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVED);
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;

			do {
				bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			} while (bytesRead > -1);

			bos.write(mybytearray, 0, current);
			bos.flush();

		} finally {
			FILE_NAME = null;
			if (fos != null)
				fos.close();
			if (bos != null)
				bos.close();
			if (sock != null)
				sock.close();
		}
		System.out.println(FILE_TO_RECEIVED);
		return FILE_TO_RECEIVED;
	}
	// public void receiveFile (File file) throws IOException {
	// FILE_TO_RECEIVED = FILE_TO_RECEIVED + FILE_NAME;
	// int bytesRead;
	// int current = 0;
	// FileOutputStream fileOutputStream = null;
	// BufferedOutputStream bufferedOutputStream = null;
	// ServerSocket servsock = null;
	// Socket sock = null;
	// try {
	// sock = new Socket(SERVER, SOCKET_PORT);
	// InputStream inputStream = sock.getInputStream();
	// fileOutputStream = new FileOutputStream(FILE_TO_RECEIVED);
	// bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
	// bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
	// current = bytesRead;
	//
	// do {
	// bytesRead = inputStream.read(mybytearray, current, (mybytearray.length -
	// current));
	// if (bytesRead >= 0)
	// current += bytesRead;
	// } while (bytesRead > -1);
	//
	// bufferedOutputStream.write(mybytearray, 0, current);
	// bufferedOutputStream.flush();
	// }
	//
	// }

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
