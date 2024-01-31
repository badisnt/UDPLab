import java.util.UUID;

public class Datagram {
    private UUID uuid;
    private String sound;

    public Datagram(UUID uuid, String sound){
        this.uuid = uuid;
        this.sound = sound;
    }
}
