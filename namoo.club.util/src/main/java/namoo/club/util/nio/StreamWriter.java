package namoo.club.util.nio;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import namoo.club.util.exception.NamooClubException;

public class StreamWriter {
	//
	private static int WriteLengthLimit = (1024 * 1024 * 10); 

	private OutputStream os;

	//--------------------------------------------------------------------------
	private StreamWriter(Socket socket) {
		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StreamWriter getInstance(Socket socket) {
		return new StreamWriter(socket);
	}

	//--------------------------------------------------------------------------
	public void write(byte[] buffer) throws IOException {

		
		if (os == null || buffer == null) {
			throw new NamooClubException("스트림 또는  버퍼가 널입니다. ");
		}

		if (buffer.length > WriteLengthLimit) {
			throw new NamooClubException("Can't write more than 10MB -> " + buffer.length); 
		}

		os.write(buffer);
	}

	public void writeWithHeadTailChar(byte[] buffer, byte headChar, byte tailChar) throws IOException {

		if (buffer.length > WriteLengthLimit) {
			throw new NamooClubException("Can't write more than 10MB -> " + buffer.length); 
		}

		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));

		byte[] sendBuffer = new byte[buffer.length + 2];

		sendBuffer[0] = headChar;
		System.arraycopy(buffer, 0, sendBuffer, 1, buffer.length);
		sendBuffer[buffer.length + 1] = tailChar;

		dos.write(sendBuffer);
		dos.flush();
	}
}