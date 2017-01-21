package namoo.club.util.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import namoo.club.util.exception.NamooClubException;

public class StreamReader {
	//
	private static int ReadLengthLimit = (1024 * 1024 * 10); 
	private InputStream is;

	//--------------------------------------------------------------------------
	private StreamReader(Socket socket) {
		try {
			this.is = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StreamReader getInstance(Socket socket) {
		if (socket == null) {
			throw new NamooClubException("The socket object is null.");
		}
		return new StreamReader(socket);
	}

	//--------------------------------------------------------------------------
	/**
	 * Read bytes with targetLen and move the pointer forward 
	 * 
	 * @param targetLen
	 * @return
	 * @throws Exception
	 */
	public byte[] read(int targetLen) throws IOException {
		
		if (targetLen > ReadLengthLimit) {
			throw new NamooClubException("Can't read more than 10MB -> " + targetLen); 
		}
		
		int readCount = 0; 
		int retryCount = 0;
		int allReadCount = 0;
		byte[] readBuffer = new byte[targetLen];
		
		while (allReadCount < targetLen) {
			readCount = is.read(readBuffer, allReadCount, targetLen - allReadCount);
			if (readCount > 0) {
				allReadCount += readCount;
			} else if (readCount == -1) {
				throw new NamooClubException("Read EOF.") ;
			} else if (readCount == 0 && ++retryCount == 20) {
				throw new NamooClubException("Retry more than 20 times.") ;
			}
		}
		return readBuffer;
	}

	public String readWithHeadTailChar(byte headChar, byte tailChar) throws  SocketException,IOException {
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		StringBuilder strBuilder = new StringBuilder();

		char receivedChar = (char) bufferedReader.read();
		if (receivedChar != headChar) {
			throw new NamooClubException("It's Not Header Character!");
		}

		while(true) {
			receivedChar = (char) bufferedReader.read();
			if((receivedChar == tailChar)) {
				break;
			}
			strBuilder.append(receivedChar);
		}
		return strBuilder.toString();
	}
}