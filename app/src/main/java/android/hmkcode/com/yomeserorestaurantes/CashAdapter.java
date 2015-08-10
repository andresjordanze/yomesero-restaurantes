package android.hmkcode.com.yomeserorestaurantes;

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

/**
 * Created by Andres on 10/08/2015.
 */
public class CashAdapter extends ArrayAdapter<Order> {
    private final Context context;
    private ArrayList<Order> orders;

    public CashAdapter(Context context, ArrayList<Order> orders) {
        super(context, R.layout.cash_list, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cash_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.mesaView);
        TextView textView3 = (TextView) rowView.findViewById(R.id.number);
        TextView nitView = (TextView) rowView.findViewById(R.id.nitView);
        TextView nameView = (TextView) rowView.findViewById(R.id.nameView);
        String state = orders.get(position).estado;
        Integer id = orders.get(position).id;

        Button plus = (Button) rowView.findViewById(R.id.checkbutton);
        Button minus = (Button) rowView.findViewById(R.id.deletebutton);
        ButtonClickListener listener = new ButtonClickListener(id, state);
        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);

        textView.setText(Float.toString(orders.get(position).consumo));
        textView3.setText(Integer.toString(orders.get(position).mesa));
        nitView.setText(Integer.toString(orders.get(position).nit));
        nameView.setText(orders.get(position).name);

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
                if(state.equals("Por cobrar")){
                    message="https://yomeseroserver.herokuapp.com/update_from_json?id="+id+"&state=Cobrado";
                    Log.d("Mensaje", message);
                    new HttpAsyncTask().execute(message);
                    Intent intent1 = new Intent(context, DisplayCashActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            }
            else{
               if(state.equals("Cobrado")){
                    message="https://yomeseroserver.herokuapp.com/update_from_json?id="+id+"&state=Por%20cobrar";
                    Log.d("Mensaje", message);
                    new HttpAsyncTask().execute(message);
                    Intent intent1 = new Intent(context, DisplayCashActivity.class);
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
