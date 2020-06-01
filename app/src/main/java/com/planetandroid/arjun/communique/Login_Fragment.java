package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

/**
 * Created by arjun on 29-06-2018.
 */

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Login_Fragment extends Activity {

     static public String cname = null;
     static public String projname = null;
     static public String taskname = null;
     static public String freename = null;

    Button signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //TextView t = findViewById(R.id.taskbar_text);
        //t.setText("Login");


        signup = findViewById(R.id.logbut);

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread

                signup.setEnabled(false);
                       new Login_Fragment.LoginSQL(Login_Fragment.this).execute();

            }
        });


    }

    class LoginSQL extends AsyncTask<String, String, String> {

        private String signup_url = "http://collabcloud.000webhostapp.com/login_new.php";

        String type1 = "Company";
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        //type1 = radioButton.getText().toString();

        Context context;
        AlertDialog alertDialog;
        LoginSQL (Context ctx) {
            context = ctx;

        }

        @Override
        protected String doInBackground(String... args) {

            EditText username = findViewById(R.id.usr);
            String username1 = username.getText().toString();

            EditText password = findViewById(R.id.psw);
            String password1 = password.getText().toString();

            type1 = radioButton.getText().toString();



            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username1, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password1, "UTF-8") + "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type1, "UTF-8");
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
            EditText username = findViewById(R.id.usr);
            String pas1 = username.getText().toString();
            signup.setEnabled(true);

            try {

                if (result.compareTo("Success")==0 && type1.compareTo("Company") == 0) {
                    cname = pas1;
                    Intent intent = new Intent(getApplicationContext(), Company_Main_Fragment.class);
                    startActivity(intent);
                }else if(result.compareTo("Allotted") == 0) {
                    cname = pas1;
                    Intent intent = new Intent(getApplicationContext(), Freelancer_Main.class);
                    startActivity(intent);
                }else if(result.compareTo("Success") == 0){
                    Toast.makeText(Login_Fragment.this, "No Projects Are Currently Allotted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Login_Fragment.this, "Wrong Username - Password.  Try again", Toast.LENGTH_LONG).show();
                    username.setText("");
                    EditText ed = findViewById(R.id.psw);
                    ed.setText("");
                }
            }catch(NullPointerException e){
                Toast.makeText(Login_Fragment.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }


        }

    }

}