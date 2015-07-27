package android.hmkcode.com.yomeserorestaurantes;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class DisplayItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(ItemFormActivity.EXTRA_MESSAGE);
        message = message.replaceAll(" ","%20");
        message = message.replaceAll("\n", "");
        new HttpAsyncTask().execute(message);

        Intent intent1 = new Intent(this,MainActivity.class);
        startActivity(intent1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            SaveSharedPreference.setUserId(DisplayItemActivity.this,"");
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class HttpAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls){
            HttpResponse response = null;
            try{
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(urls[0]));
                response = client.execute(request);
            }catch (URISyntaxException e){
                e.printStackTrace();
            }catch (ClientProtocolException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result){
            System.out.println(result);
        }
    }

}
