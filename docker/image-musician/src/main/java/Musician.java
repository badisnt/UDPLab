import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.UUID;
import com.google.gson.Gson;

import static java.lang.Thread.sleep;
import static java.nio.charset.StandardCharsets.*;

class Musician {
    final static String IPADDRESS = "239.255.22.5";
    final static int PORT = 9904 ;
    final static int SLEEP = 1000;
    final static UUID uuid = UUID.randomUUID();

    public static void main(String[] args) {
        if (args.length != 1 || args[0]==null){
            throw new IllegalArgumentException();
        }
        Instrument instrument = new Instrument(args[0]);
        try (DatagramSocket socket = new DatagramSocket()) {

            String message = new Gson().toJson(new Datagram(uuid, instrument.getSound()));

            while(true){
                byte[] payload = message.getBytes(UTF_8);
                InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
                var packet = new DatagramPacket(payload, payload.length, dest_address);
                socket.send(packet);
                try{
                    sleep(SLEEP);
                }catch(InterruptedException ignored){

                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}