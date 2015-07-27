package android.hmkcode.com.yomeserorestaurantes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class ItemFormActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    Uri imageUri = Uri.parse("android.resource://android.hmkcode.com.yomeserorestaurantes/drawable/no_image.png");
    ImageView itemImageImgView;
    Bitmap bitmap;
    String encode_image="no_image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        itemImageImgView = (ImageView) findViewById(R.id.imgViewItemImage);

        itemImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen de item"), 1);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_form, menu);
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
            SaveSharedPreference.setUserId(ItemFormActivity.this,"");
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void createItem(View view){

        EditText item_name = (EditText) findViewById(R.id.item_name);
        EditText item_description = (EditText) findViewById(R.id.item_description);
        Spinner item_type = (Spinner) findViewById(R.id.item_type);
        EditText item_time = (EditText) findViewById(R.id.item_time);
        EditText item_price = (EditText) findViewById(R.id.item_price);

        String message="https://yomeseroapi.herokuapp.com/create_from_json?";
        message += "item_name=";
        message += item_name.getText().toString();
        message += "&item_description=";
        message += item_description.getText().toString();
        message += "&item_type=";
        message += item_type.getSelectedItem().toString();
        message += "&item_time=";
        message += item_time.getText().toString();
        message += "&item_price=";
        message += item_price.getText().toString();
        message += "&item_image=";
        message += encode_image;
        Log.d("Mensaje url", message);

        message = message.replaceAll(" ","%20");
        message = message.replaceAll("\n", "");
        new HttpAsyncTask().execute(message);

        Intent intent1 = new Intent(this,MainActivity.class);
        startActivity(intent1);
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

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (resCode == RESULT_OK)
        {
            imageUri = data.getData();
            itemImageImgView.setImageURI(data.getData());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.PNG, 100, bao);
                byte [] ba = bao.toByteArray();
                encode_image = Base64.encodeToString(ba,Base64.DEFAULT);
                encode_image = encode_image.replace("+","_");
                encode_image = encode_image.replace("\n", "");
                encode_image = encode_image.replace(" ", "");
                //Log.d("encodeimage", encode_image);
                //Log.d("Bitmap", "Positivo");
            } catch (IOException e) {
                //Log.d("ErrorBitmap", "Negativo");
                e.printStackTrace();
            }
        }
    }

}
