import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

class Auditor {
    final static String IPADDRESS = "239.255.22.5";
    final static int UDP_PORT = 9904;
    final static int TCP_PORT = 2205;
    final static int TIMEOUT = 5000;

    private static List<Musician> getActiveMusicians(Map<String, Musician> musicians) {
        List<Musician> activeMusicians = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        if (musicians==null||musicians.size()==0) return activeMusicians;
        musicians.forEach((uuid, musician) -> {
            if (currentTime - musician.getLastActivity() <= TIMEOUT) {
                activeMusicians.add(musician);
            }
        });
        return activeMusicians;
    }

    public static void main(String[] args) {
        Map<String, Musician> musicians = new HashMap<>();
        new Thread(() -> {
            try (MulticastSocket socket = new MulticastSocket(UDP_PORT)) {
                InetSocketAddress groupAddress = new InetSocketAddress(IPADDRESS, UDP_PORT);
                NetworkInterface netif = NetworkInterface.getByName("eth0");
                socket.joinGroup(groupAddress, netif);

                byte[] buffer = new byte[1024];

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String data = new String(packet.getData(), 0, packet.getLength());

                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(data, JsonObject.class);

                    String uuid = jsonObject.get("uuid").getAsString();
                    String sound = jsonObject.get("sound").getAsString();

                    musicians.put(uuid, new Musician(uuid,sound,System.currentTimeMillis()));
                   }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    Gson gson = new Gson();
                    String jsonPayload = gson.toJson(getActiveMusicians(musicians));

                    OutputStream outputStream = clientSocket.getOutputStream();
                    outputStream.write(jsonPayload.getBytes());

                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}