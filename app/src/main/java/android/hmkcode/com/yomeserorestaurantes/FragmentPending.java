package android.hmkcode.com.yomeserorestaurantes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FragmentPending extends Fragment {
    View rootView;
    Context context;
    ListView ordersListView;
    int current_rest;
    ArrayList<Order> orders;

    public FragmentPending(Context context){
        this.context = context;
    }

    private class HttpAsyncTask extends AsyncTask<String,Void,String> {
        private Context context;
        public HttpAsyncTask(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... urls){
            return GET(urls[0]);
        }

        protected void onPostExecute(String result){
            try {
                JSONArray json_orders = new JSONArray(result);
                orders = new ArrayList<>();
                Order aux;
                for (int i = 0; i < json_orders.length(); i++) {
                    aux = new Order();
                    aux.parseFromJson(json_orders.getJSONObject(i));
                    if (current_rest == aux.rest && aux.estado.equals("Pendiente")){
                        orders.add(aux);
                    }
                }
                OrdersAdapter ordersAdapter = new OrdersAdapter(context,orders);
                ordersListView.setAdapter(ordersAdapter);
                ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context, ShowOrderActivity.class);
                        intent.putExtra("order", orders.get(position));
                        startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream!=null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work";
        }catch (Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        String result = "";
        while ((line = bufferedReader.readLine())!=null)
            result+=line;
        inputStream.close();
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_entr, container, false);
        ordersListView = (ListView) rootView.findViewById(R.id.ordersListView);
        current_rest = Integer.parseInt(SaveSharedPreference.getUserRest(this.context));
        new HttpAsyncTask(context).execute("https://yomeseroserver.herokuapp.com/ordens.json");
        return rootView;
    }

}


