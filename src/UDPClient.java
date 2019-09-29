import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPClient {

    private static final int PACKAGE_SIZE = 512;
    private static final int SOCKET_PORT = 9876;

    public static void main(String args[]) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите x:");
        String x = reader.readLine();
        System.out.println("Введите y:");
        String y = reader.readLine();
        System.out.println("Введите z:");
        String z = reader.readLine();
        String message = String.format("%s %s %s", x, y, z);

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[PACKAGE_SIZE];
        sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, SOCKET_PORT);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("Result:" + modifiedSentence.trim());
        clientSocket.close();
    }

}