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

/**
 * Created by Andres on 01/08/2015.
 */
public class MyOrderItemAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private ArrayList<Item> items;

    public MyOrderItemAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.order_item_view, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.order_item_view, parent, false);
        TextView item_name = (TextView) rowView.findViewById(R.id.firstLine);
        TextView quantity = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

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

        item_name.setText(items.get(position).item_name);
        quantity.setText("Cantidad: "+Integer.toString(items.get(position).quantity));
        return rowView;
    }
}