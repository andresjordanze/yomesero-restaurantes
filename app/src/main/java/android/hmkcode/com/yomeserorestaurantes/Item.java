package android.hmkcode.com.yomeserorestaurantes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alex on 4/11/15.
 */
public class Item {
    public String item_name;
    public String item_description;
    public String item_type;
    public int item_time;
    public float item_price;
    public String item_image;
    public int restaurant_id;

    public String toJSON(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("item_name", item_name);
            jsonObject.put("item_description", item_description);
            jsonObject.put("item_type", item_type);
            jsonObject.put("item_time", item_time);
            jsonObject.put("item_price", item_price);
            jsonObject.put("restaurant_id", restaurant_id);
            jsonObject.put("item_image", item_image);
            return jsonObject.toString();
        }catch(Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return null;
    }
    public String fromJSON(String data){
        try{
            JSONObject jsonObject = new JSONObject(data);
            item_name = jsonObject.getString("item_name");
            item_description = jsonObject.getString("item_description");
            item_type = jsonObject.getString("item_type");
            item_time = Integer.parseInt(jsonObject.getString("item_time"));
            item_price = Float.parseFloat(jsonObject.getString("item_price"));
            restaurant_id = Integer.parseInt(jsonObject.getString("restaurant_id"));
            item_image = jsonObject.getString("item_image");
            return show();
        }catch(Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }
    }

    public void parseFromJson(JSONObject json){
        try {
            item_name = json.getString("item_name");
            item_description = json.getString("item_description");
            item_type = json.getString("item_type");
            item_time = Integer.parseInt(json.getString("item_time"));
            item_price = Float.parseFloat(json.getString("item_price"));
            restaurant_id = Integer.parseInt(json.getString("restaurant_id"));
            item_image = json.getString("item_image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String show(){
        String result="";
        result += item_name;
        result += "\n";
        result += item_description;
        result += item_image;
        return result;
    }

    @Override
    public String toString(){
        return "Name: "+item_name+"\n"+"Type: "+item_type+"\n"+"Time: "+Integer.toString(item_time)+"\n"+"Price: "+Float.toString(item_price);
    }
}
