package com.nonvoid.blackeye.MVP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nonvoid.blackeye.MainActivity;
import com.nonvoid.blackeye.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPass;
    //location of the web service
    private static final String serviceUrl = "http://www.flex.nonvoid.com/inciteful/android.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        etPass = (EditText) findViewById(R.id.editTextLoginPassword);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonLogin :
                login(etEmail.getText().toString(), etPass.getText().toString());
                break;
            case R.id.buttonSignup :

                break;
        }
    }

    private void login(String email, String pass)  {
        try{
            JSONObject credentials = new JSONObject();
            credentials.put("email", email);
            credentials.put("password",pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // check the status of the network connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // if we have a live connection, use it, otherwise, show an error
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
           // new DownloadSomeValueTask().execute(serviceUrl,JSONtoPost);
        } else {
            // display error
            Toast.makeText(getApplicationContext(),
                    "Sorry -- No Network Connection Available", Toast.LENGTH_LONG)
                    .show();
        }
    }

    // Given a URL, establishes an HttpUrlConnection, posts the JSON, and retrieves
    // the response content via a BufferedReader, which it returns as
    // a string.
    private String downloadUrl(String myurl, String JSONforPosting) throws IOException {
        BufferedReader br = null; // need this to enable buffered input
        StringBuilder sb = new StringBuilder(); // need this to catch buffered input
        int responseCode = 0;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Starts the query
            conn.connect();
            final OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(JSONforPosting);
            osw.close();

            responseCode = conn.getResponseCode();
            // note: under "normal" circumstance, we'd comment out this next line
            //       because we would not want to log any "sensitive" data; however,
            //       it's helpful to leave in during debugging/testing
            Log.d("debug", "The response is: " + responseCode);

            switch (responseCode) {
                case 200:
                case 201:
                    //is = conn.getInputStream();
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
            }

            // note: under "normal" circumstance, we'd comment out this next line
            //       because we would not want to log any "sensitive" data; however,
            //       it's helpful to leave in during debugging/testing
            Log.d(CreateHintActivity.TAG, "The response string is: " + sb.toString());

            return sb.toString(); //contentAsString;

        } catch (MalformedURLException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            if (br != null) {
                br.close();
            }
        }
        if(sb.toString().length()>0) {
            return sb.toString(); //contentAsString;
        } else {
            return "***COMM ERROR: "+ String.valueOf(responseCode) +" ***";
        }
    } // end downloadUrl function

    private class LoginAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            // params comes from the execute() call: params[1] is the JSON.
            try {
                return downloadUrl(params[0], params[1]);
            } catch (IOException e) {
                return "Unable to retrieve response. URL or JSON may be invalid.";
            }
        }
    }
}
