package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

/**
 * Created by arjun on 29-06-2018.
 */

import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class Register_Fragment extends Activity {

    Button signup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        //TextView t = findViewById(R.id.taskbar_text);
        //t.setText("Register");

        signup = findViewById(R.id.sgnup);
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                signup.setEnabled(false);
                // creating new product in background thread
                new CreateNewProduct(Register_Fragment.this).execute();
            }
        });

    }

    class CreateNewProduct extends AsyncTask<String, String, String> {

        private String signup_url = "https://collabcloud.000webhostapp.com/registercompany_new.php";

        Context context;
        AlertDialog alertDialog;
        CreateNewProduct (Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... args) {

            EditText username = findViewById(R.id.usrreg);
            String usrnme = username.getText().toString();

            EditText password = findViewById(R.id.pswreg);
            String pswrd = password.getText().toString();



            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usrnme, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pswrd, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            alertDialog=new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");

        }

        @Override
        protected void onPostExecute(String  result) {
            signup.setEnabled(true);
            try {

                if (result.compareTo("1") != 0) {
                    alertDialog.setMessage("Username is already taken. /n Try Again");
                    alertDialog.show();
                } else {
                    alertDialog.setMessage("Registation Successful");
                    alertDialog.show();
                }
            }catch(NullPointerException e){
                Toast.makeText(Register_Fragment.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }

    }


}