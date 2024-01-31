public class Instrument {

    private String sound = "";
    
    public Instrument(String name){
        switch (name){
            case "piano": sound = "ti-ta-ti"; break;
            case "trumpet": sound = "pouet"; break;
            case "flute": sound = "trulu"; break;
            case "violin": sound = "gzi-gzi"; break;
            case "drum": sound = "boom-boom"; break;
            default: throw new RuntimeException("Invalid instrument name!");
        }
    }

    public String getSound(){
        return sound;
    }
}
