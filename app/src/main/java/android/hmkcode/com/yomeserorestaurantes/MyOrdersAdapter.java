package android.hmkcode.com.yomeserorestaurantes;

/**
 * Created by Andres on 27/07/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;


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
        TextView textView3 = (TextView) rowView.findViewById(R.id.totalView);
        String state = orders.get(position).estado;
        Integer id = orders.get(position).id;

        Button plus = (Button) rowView.findViewById(R.id.checkbutton);
        Button minus = (Button) rowView.findViewById(R.id.deletebutton);
        ButtonClickListener listener = new ButtonClickListener(id, state);
        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);

        number.setText(Integer.toString(orders.get(position).id));
        textView.setText(Float.toString(orders.get(position).consumo));
        textView3.setText(Integer.toString(orders.get(position).mesa));


        return rowView;
    }

    private class ButtonClickListener implements View.OnClickListener {
        private int id;
        private String state;
        private String message;

        public ButtonClickListener(int id, String st) {
            this.id = id;
            state = st;
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.checkbutton) {
                if (state.equals("Pendiente")){
                    message="https://yomeseroserver.herokuapp.com/update_from_json?id="+id+"&state=En%20Proceso";
                    Log.d("Mensaje", message);
                    new HttpAsyncTask().execute(message);
                    Intent intent1 = new Intent(context, DisplayOrdersActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
                else if(state.equals("En Proceso")){
                    message="https://yomeseroserver.herokuapp.com/update_from_json?id="+id+"&state=Entregado";
                    Log.d("Mensaje", message);
                    new HttpAsyncTask().execute(message);
                    Intent intent1 = new Intent(context, DisplayOrdersActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            }
            else{
                if (state.equals("En Proceso")){
                    message="https://yomeseroserver.herokuapp.com/update_from_json?id="+id+"&state=Pendiente";
                    Log.d("Mensaje", message);
                    new HttpAsyncTask().execute(message);
                    Intent intent1 = new Intent(context, DisplayOrdersActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
                else if(state.equals("Entregado")){
                    message="https://yomeseroserver.herokuapp.com/update_from_json?id="+id+"&state=En%20Proceso";
                    Log.d("Mensaje", message);
                    new HttpAsyncTask().execute(message);
                    Intent intent1 = new Intent(context, DisplayOrdersActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            }

        }
    }

    private class HttpAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(urls[0]));
                response = client.execute(request);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}