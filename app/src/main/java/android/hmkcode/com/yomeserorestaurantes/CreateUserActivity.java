package android.hmkcode.com.yomeserorestaurantes;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CreateUserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createUser(View view){
        EditText emailEditText = (EditText) findViewById(R.id.create_email);
        EditText passwordEditText = (EditText) findViewById(R.id.create_password);
        EditText password_confirmationEditText = (EditText) findViewById(R.id.create_password_confirmation);

        String email_validation = emailEditText.getText().toString();
        String password_validation = passwordEditText.getText().toString();
        String password_confirmation_validation = password_confirmationEditText.getText().toString();

        HttpAsyncTask task = new HttpAsyncTask();
        if (!email_validation.contains("@") || password_validation.length()<8 ||!password_validation.equals(password_confirmation_validation)){
            if (!email_validation.contains("@")) {
                emailEditText.setError("El correo no es correcto");
            }
            if (password_validation!=password_confirmation_validation) {
                password_confirmationEditText.setError("Las contrasenas no coinciden");
            }
            if(password_validation.length()<8){
                passwordEditText.setError("La contrasena es muy corta");
            }
        }else {
            task.execute();
        }
    }

    private class HttpAsyncTask extends AsyncTask<Void,Void,Boolean> {
        String email;
        String password;
        String password_confirmation;
        String id=SaveSharedPreference.getUserRest(CreateUserActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            EditText email_r = (EditText) findViewById(R.id.create_email);
            EditText password_r = (EditText) findViewById(R.id.create_password);
            EditText password_c_r = (EditText) findViewById(R.id.create_password_confirmation);
            email = email_r.getText().toString();
            password = password_r.getText().toString();
            password_confirmation = password_c_r.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... params){
            InputStream inputStream = null;
            String result="";
            String url = "https://yomeseroserver.herokuapp.com/create_user_json?email="+email+"&password="+password+"&password_confirmation="+password_confirmation+"&rest="+id;
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
                inputStream = httpResponse.getEntity().getContent();
            }catch(Exception e){
                return false;
            }

            try {
                result = convertInputStreamToString(inputStream);
            }catch (Exception e){
                result = "Did not work";
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success){
            Log.d("Esta en post execute: ", "Siii");
            if (success){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
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
}
