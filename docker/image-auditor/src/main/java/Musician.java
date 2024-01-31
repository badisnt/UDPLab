public class Musician {
    private String uuid, name, sound;
    private Long lastActivity;

    public Musician(String uuid, String sound, Long lastActivity){
        this.uuid = uuid;
        this.sound= sound;
        this.lastActivity = lastActivity;
        switch (sound){
            case "ti-ta-ti": name = "piano"; break;
            case "pouet": name = "trumpet"; break;
            case "trulu": name = "flute"; break;
            case "gzi-gzi": name = "violin"; break;
            case "boom-boom": name = "drum" ; break;
            default: throw new RuntimeException("Invalid instrument name!");
        }
    }

    public String getUUID() {
        return uuid;
    }

    public String getSound() {
        return sound;
    }

    public Long getLastActivity() {
        return lastActivity;
    }
}
