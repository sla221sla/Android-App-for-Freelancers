package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

/**
 * Created by arjun on 29-06-2018.
 */


import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Update_Fragment extends Activity{

    String result_project = null;
    String date2;
    String part1, part2, part3;
    Button signup;


    public List<RecyclerViewAdapterClass> dataList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_page);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        TextView date1 = findViewById(R.id.enddate);
        date2 = year + "/" + (month + 1) + "/" + dayOfMonth;
        date1.setText(date2);

        new GetProjectDetails(Update_Fragment.this).execute();


        signup = findViewById(R.id.sbmt_upd);
        signup.setEnabled(false);


    }



    class WorkUpdate extends AsyncTask<String, String, String> {

        private String signup_url = "https://collabcloud.000webhostapp.com/update_new.php";

        Context context;
        AlertDialog alertDialog;

        WorkUpdate(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... args) {

            EditText ed = findViewById(R.id.hrs_spnd);
            String hrs = ed.getText().toString();

            ed = findViewById(R.id.details);
            String det = ed.getText().toString();

            String fname = Login_Fragment.cname;

            EditText ed1 = findViewById(R.id.percentage);
            String per = ed1.getText().toString();



            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =  URLEncoder.encode("freelancer", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8") + "&" + URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(part3, "UTF-8") + "&" + URLEncoder.encode("projname", "UTF-8") + "=" + URLEncoder.encode(part1, "UTF-8") + "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date2, "UTF-8") + "&" + URLEncoder.encode("hrs", "UTF-8") + "=" + URLEncoder.encode(hrs, "UTF-8") + "&" + URLEncoder.encode("details", "UTF-8") + "=" + URLEncoder.encode(det, "UTF-8") + "&" + URLEncoder.encode("percentage", "UTF-8") + "=" + URLEncoder.encode(per, "UTF-8");
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
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                alertDialog.setMessage(result);
                alertDialog.show();
            }catch(NullPointerException e){
                Toast.makeText(Update_Fragment.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }



    class GetProjectDetails extends AsyncTask<String, String, String> {

        private String signup_url = "https://collabcloud.000webhostapp.com/getpro_new.php";

        Context context;
        AlertDialog alertDialog;

        GetProjectDetails(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... args) {

            String fname = Login_Fragment.cname;


            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                result_project="";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result_project += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result_project;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                alertDialog.setMessage(result);
                alertDialog.show();

                result_project = result;



                    JSONObject j = new JSONObject(result);
                    JSONArray array;
                    array = j.getJSONArray("data");



                    JSONObject c = array.getJSONObject(0);

                    part1 = c.getString("ProjectName");
                    part2 = c.getString("ProjectDesc");
                    part3 = c.getString("CompName");



                    EditText ed1 = findViewById(R.id.allot_proj);
                    EditText ed2 = findViewById(R.id.proj_det);
                    ed1.setText(part1);
                    ed2.setText(part2);




                    signup.setEnabled(true);
                signup.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (part1.compareTo("0") != 0)
                            new Update_Fragment.WorkUpdate(Update_Fragment.this).execute();
                    }
                });
            }catch(NullPointerException e){
                Toast.makeText(Update_Fragment.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            } catch( JSONException e){
                e.printStackTrace();
            }
        }
    }


    public void calenderview(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        TextView date = findViewById(R.id.enddate);
                        date2 = year + "/" + (month+1) + "/" + day;
                        date.setText(date2);


                    }
                }, year,month,dayOfMonth);
        datePickerDialog.show();
    }


 }



