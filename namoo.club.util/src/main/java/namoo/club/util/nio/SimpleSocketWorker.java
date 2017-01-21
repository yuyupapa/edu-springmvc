package namoo.club.util.nio;

import java.io.IOException;
import java.net.Socket;

import namoo.club.util.ByteUtil;
import namoo.club.util.exception.NamooClubException;

public class SimpleSocketWorker {
	//
	private static final int HeaderSize = 4; 

	private StreamReader reader;
	private StreamWriter writer;
	private Socket socket;

	//--------------------------------------------------------------------------
	public SimpleSocketWorker(Socket socket) {
		this.socket = socket;
		this.reader = StreamReader.getInstance(socket);
		this.writer = StreamWriter.getInstance(socket);
	}

	public void close() {
		try {
			if (!this.socket.isClosed()) {
				this.socket.close();
			}
		} catch (IOException e) {
			throw new NamooClubException("Closing socket --> " + e.getMessage());
		}
	}

	public byte[] getReqeusterIp() {
		return socket.getInetAddress().getAddress();
	}
	
	public boolean available() {
		return this.socket.isConnected(); 
	}
	
	public synchronized byte[] readBytes() throws IOException {
		byte[] bodyLengthBytes = reader.read(HeaderSize);
		int bodyLength = ByteUtil.toInt(bodyLengthBytes);
		return reader.read(bodyLength);
	}

	public String readString() throws IOException {
		byte[] readBytes =  this.readBytes(); 
		if (readBytes == null) {
			return null; 
		}
		
		return (new String(readBytes)); 
	}

	public void writeString(String strMessage) throws IOException {
		if (strMessage == null) {
			return; 
		}
		writeBytes(strMessage.getBytes()); 
	}

	public synchronized void writeBytes(byte[] byteMessage) throws IOException {
		if (byteMessage == null) {
			return; 
		}
		
		this.writer.write(ByteUtil.toBytes(byteMessage.length));
		this.writer.write(byteMessage);
	}
}