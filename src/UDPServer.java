import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPServer {
    private static final int PACKAGE_SIZE = 512;
    private static final int SOCKET_PORT = 9876;

    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(SOCKET_PORT);
        byte[] receiveData = new byte[PACKAGE_SIZE];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String receivedData = new String(receivePacket.getData()).trim();
            System.out.println("RECEIVED: " + receivedData.trim());
            String[] values = receivedData.split(" ");
            double x = Double.parseDouble(values[0]);
            double y = Double.parseDouble(values[1]);
            double z = Double.parseDouble(values[2]);
            double result = calculateExpression(x, y, z);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            sendData = String.valueOf(result).getBytes();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }

    private static double calculateExpression(double x, double y, double z) {
        double firstPart = (1 + Math.pow(Math.sin(x + y), 2)) * Math.pow(x, Math.abs(y));
        double abs = Math.abs(Math.exp(x) - 2 * y / (1 + Math.pow(x, 2) * Math.pow(y, 3)));
        double secondPart = Math.pow(Math.cos(Math.atan(1 / z)), 2);
        return firstPart / abs + secondPart;
    }
}
