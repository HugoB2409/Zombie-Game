import engine.Buffer;
import engine.GameEntity;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map extends GameEntity {
    Camera camera;
    private ArrayList<mapObject> objets;

    public ArrayList<mapObject> getObjets() {
        return objets;
    }

    public Map(Camera camera) {
        this.camera = camera;
        objets = new ArrayList<>();
    }

    public void getColisionPos() {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("C:\\Users\\hugo_\\Desktop\\TP3\\src\\main\\resources\\Map\\mapjson.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jo = (JSONObject) obj;
        JSONArray layers = (JSONArray) jo.get("layers");
        JSONObject layer = (JSONObject) layers.get(1);
        JSONArray test = (JSONArray) layer.get("data");

        int i;
        int temp;
        int x,y;
        for (i = 0 ; i < test.size(); i++) {
            Object obje = test.get(i);
            if(!obje.toString().equals("0")) {
                temp = i / 100;
                y = temp * 32;
                x = (i - (temp * 100)) * 32;
                objets.add(new mapObject(x, y));

            }
        }
    }

    public void draw(Buffer buffer){}
}



