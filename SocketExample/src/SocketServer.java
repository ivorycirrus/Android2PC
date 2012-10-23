import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class SocketServer {

	private static final int SERVER_PORT = 9500; 

	private ServerSocket mSocketServer = null;
	BufferedWriter mServerWriter = null;
	BufferedReader mReaderFromClient = null;

	public SocketServer() {

		try {
			
			mSocketServer = new ServerSocket(SERVER_PORT);
			System.out.println("connecting...");
			Socket client = mSocketServer.accept();

			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();

			mReaderFromClient = new BufferedReader(new InputStreamReader(is));
			mServerWriter = new BufferedWriter(new OutputStreamWriter(os));
			String msg = "";
			String msg1 = "Server > ";

			while (true) {

				msg = mReaderFromClient.readLine();

				if (msg.equals("exit")) {
					break;
				} else {
					System.out.println(client.getInetAddress() + " : " + msg
							+ "\n");
					mServerWriter.write(msg1
							+ DateFormat.getTimeInstance().format(
									Calendar.getInstance(Locale.KOREAN)
											.getTime()) + "\n");
					mServerWriter.flush();
				}
			}
		} catch (Exception e) {
			System.out.println("Fail to create socket..");
		}
		try {
			mServerWriter.close();
			mReaderFromClient.close();
			mSocketServer.close();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		SocketServer ob = new SocketServer();		
	}
}
