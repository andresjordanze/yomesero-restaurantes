package android.hmkcode.com.yomeserorestaurantes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class ShowOrderActivity extends ActionBarActivity {
    Order order;
    ListView ordenItemListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        order = (Order) getIntent().getSerializableExtra("name");
        new HttpAsyncTask(this).execute("https://yomeseroapi.herokuapp.com/getOrdenItems?id="+order.id);
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
                JSONArray json_items = new JSONArray(result);
                ArrayList<Item> items = new ArrayList<>();
                Item aux;
                for (int i = 0; i < json_items.length(); i++) {
                    aux = new Item();
                    aux.parseFromJson(json_items.getJSONObject(i));
                        items.add(aux);
                }
                MyArrayAdapter myArrayAdapter = new MyArrayAdapter(context,items);
                ordenItemListView.setAdapter(myArrayAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out){
            SaveSharedPreference.setUserId(ShowOrderActivity.this,"");
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_display_orders){
            Intent intent = new Intent(getApplicationContext(),DisplayOrdersActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}