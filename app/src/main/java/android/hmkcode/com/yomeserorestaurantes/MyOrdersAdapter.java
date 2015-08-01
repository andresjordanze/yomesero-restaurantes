package android.hmkcode.com.yomeserorestaurantes;

/**
 * Created by Andres on 27/07/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MyOrdersAdapter extends ArrayAdapter<Order> {
    private final Context context;
    private ArrayList<Order> orders;

    public MyOrdersAdapter(Context context, ArrayList<Order> orders) {
        super(context, R.layout.order_list, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.order_list, parent, false);
        TextView number = (TextView) rowView.findViewById(R.id.number);
        TextView textView = (TextView) rowView.findViewById(R.id.mesaView);
        TextView textView2 = (TextView) rowView.findViewById(R.id.stateView);
        TextView textView3 = (TextView) rowView.findViewById(R.id.totalView);

        number.setText(Integer.toString(orders.get(position).id));
        textView.setText(Float.toString(orders.get(position).consumo));
        textView2.setText(orders.get(position).estado);
        textView3.setText(Integer.toString(orders.get(position).mesa));
        return rowView;
    }
}