import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SocketClient {

	// 안드로이드 서버 접속 정보
	private static final int SERVER_PORT = 9500;
	private static final String SERVER_IP = "localhost";

	// 클라이언트 소켓
	private Socket mClientSocket = null;
	
	// 통신용 스트림 
	BufferedWriter mClientWriter = null;
	BufferedReader mMessageToServerReader = null;
	BufferedReader mMessageFromServerReader = null;

	// 예약 내역 저장 변수
	volatile Integer number = new Integer(0);
	HashMap<Integer, String> waitingList = new HashMap<Integer, String>();

	/**
	 * @method Client Socket 생성자
	 * @aim 소켓 생성 및 메시지 수신 처리
	 * */
	public SocketClient(String ip, int port) {
		try {
			
			//소켓 생성
			mClientSocket = new Socket(ip, port);

			// 입/출력용 스트림 준비
			InputStream is = mClientSocket.getInputStream();
			OutputStream os = mClientSocket.getOutputStream();

			// 안드로이드로부터 수신된 데이터
			String msgToServer = null;

			// 입/출력용 스트림 연결
			mMessageToServerReader = new BufferedReader(new InputStreamReader(System.in));
			mMessageFromServerReader = new BufferedReader(new InputStreamReader(is));
			mClientWriter = new BufferedWriter(new OutputStreamWriter(os));

			// 안드로이드로 부터 오는 메시지 수신부는 별도의 스레드로 분리
			new Thread(new MessageReader()).start();

			do {
				// 관리메뉴출력
				printWaitingList();
				msgToServer = mMessageToServerReader.readLine();

				// 예약 목록에 있는 번호일 경우 안드로이드에 정보 보내고 목록에서 삭제
				Integer thisKey = Integer.parseInt(msgToServer);
				if (waitingList.containsKey(thisKey)) {
					mClientWriter.write(waitingList.get(thisKey) + "\n");
					mClientWriter.flush();
					waitingList.remove(thisKey);
				}

			} while (!(msgToServer.equals("exit")));

		} catch (Exception e) {
			System.out.println("Connection fail");
		}
		try {
			// 종료시 소켓 닫기
			mClientWriter.close();
			mMessageToServerReader.close();
			mClientSocket.close();
		} catch (Exception e) {
		}

	}

	/**
	 * @aim 예약자 목록 출력
	 * */
	private void printWaitingList() {
		System.out.println("\n\n***************************************************");
		System.out.println("   Waitings...");
		System.out.println("***************************************************");
		Set<Integer> list = waitingList.keySet();
		for (Iterator<Integer> i = list.iterator(); i.hasNext();) {
			Integer key = i.next();
			System.out.println(" * " + key + "  :  " + waitingList.get(key));
		}
		System.out.println("***************************************************");
		System.out.print("Select : ");
	}

	/**
	 * @method 프로그램 메인 함수
	 * */
	public static void main(String[] args) {
		SocketClient ob = new SocketClient(SERVER_IP, SERVER_PORT);
	}

	/**
	 * @aim 안드로이드로부터 들어오는 메시지 처리
	 * */
	private class MessageReader implements Runnable {

		@Override
		public void run() {
			String msgFromServer = null;
			try {
				while (true) {
					msgFromServer = mMessageFromServerReader.readLine();
					if ("REVC_OK".equals(msgFromServer)) {
						System.out.println("\n\n[Device_Message] RECV_OK\n\n");
					} else if (msgFromServer != null) {
						waitingList.put(++number, msgFromServer);
						printWaitingList();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}