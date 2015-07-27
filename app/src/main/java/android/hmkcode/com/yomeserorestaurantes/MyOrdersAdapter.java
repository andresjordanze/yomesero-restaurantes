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
        super(context, R.layout.order_file, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.order_file, parent, false);
        TextView number = (TextView) rowView.findViewById(R.id.number);
        TextView textView = (TextView) rowView.findViewById(R.id.mesaView);
        TextView textView2 = (TextView) rowView.findViewById(R.id.stateView);
        TextView textView3 = (TextView) rowView.findViewById(R.id.totalView);

        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> totals = new ArrayList<>();
        ArrayList<String> states = new ArrayList<>();
        ArrayList<String> mesas = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {

            numbers.add(""+orders.get(i).id);
            totals.add("Total:"+orders.get(i).consumo);
            states.add("Estado:"+orders.get(i).estado);
            mesas.add("Mesa nº:"+orders.get(i).mesa);
        }

        number.setText(numbers.get(position));
        textView.setText(totals.get(position));
        textView2.setText(states.get(position));
        textView3.setText(mesas.get(position));
        return rowView;
    }
}