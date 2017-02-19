package networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server implements Runnable {

	private final static int SOCKET_PORT = 13267;
	private static String FILE_TO_RECEIVED = "c:/temp/";
	private static String FILE_NAME = null;
	private static int FILE_SIZE = 2097152; // file size temporary hard
	private String FileToSend = null;
	private String serverMethod = null;
	private String FileToDelete = null;
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
			FILE_TO_RECEIVED = "c:/temp/";
			FILE_NAME = null;
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
			if (serverMethod.equals("receive")) {
				receive();
			}
			if (serverMethod.equals("sendFile") && FileToSend != null) {
				sendFile(new File(FileToSend));
			}
			if (serverMethod.equals("deleteFile")) {
				deleteFile();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileToSend() {
		return FileToSend;
	}

	public String getServerMethod() {
		return serverMethod;
	}

	public void setFileToSend(String fileToSend) {
		FileToSend = fileToSend;
	}

	public void setServerMethod(String serverMethod) {
		this.serverMethod = serverMethod;
	}

	public void sendFile(File file) {
		ServerSocket servsock = null;
		Socket sock = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			servsock = new ServerSocket(SOCKET_PORT);
			System.out.println("Waiting for connection...");
			sock = servsock.accept();
			// send file
			// File myFile = new File(fileToSend);
			byte[] mybytearray = new byte[(int) file.length()];
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
			os = sock.getOutputStream();
			System.out.println("Sending " + file + "(" + mybytearray.length + " bytes)");
			System.out.println("File " + file + " uploaded (" + mybytearray.length + " bytes)");
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();
			System.out.println("Done.");
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (os != null)
					os.close();
				if (sock != null)
					sock.close();
				if (servsock != null)
					servsock.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	private void deleteFile() {
		try {
			Files.deleteIfExists(Paths.get(FileToDelete));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileToDelete() {
		return FileToDelete;
	}

	public void setFileToDelete(String fileToDelete) {
		FileToDelete = fileToDelete;
	}
}