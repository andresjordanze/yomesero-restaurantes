package android.hmkcode.com.yomeserorestaurantes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyArrayAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private ArrayList<Item> items;

    public MyArrayAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.item_list, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            names.add(items.get(i).item_name);
            descriptions.add("Precio: Bs. "+items.get(i).item_price);
        }
        if (items.get(position).item_image.equals("no+image")){
            if (items.get(position).item_type.equals("Comida")){
                if(items.get(position).item_price>20){
                    imageView.setImageResource(R.mipmap.food1);
                }
                else{
                    imageView.setImageResource(R.mipmap.food);
                }
            }
            if (items.get(position).item_type.equals("Bebida")){
                if(items.get(position).item_price>20){
                    imageView.setImageResource(R.mipmap.drink1);
                }
                else{
                    imageView.setImageResource(R.mipmap.drink);
                }
            }
            if (items.get(position).item_type.equals("Postre")){
                if(items.get(position).item_price>10){
                    imageView.setImageResource(R.mipmap.dessert1);
                }
                else{
                    imageView.setImageResource(R.mipmap.dessert);
                }
            }
        }
        else{
            byte[] decodedByte = Base64.decode(items.get(position).item_image, 0);
            Bitmap bm = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            Drawable img_drawable = new BitmapDrawable(context.getResources(), bm);

            imageView.setImageDrawable(img_drawable);
        }

        textView.setText(names.get(position));
        textView2.setText(descriptions.get(position));
        return rowView;
    }
}
