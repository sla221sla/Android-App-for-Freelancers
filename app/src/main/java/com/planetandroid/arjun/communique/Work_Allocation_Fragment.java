package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;

/**
 * Created by arjun on 29-06-2018.
 */

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

public class Work_Allocation_Fragment extends Activity {


    String startdate = null, enddate = null;
    Button signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_allocation);


        final Spinner dropdown = findViewById(R.id.taskID);
        String[] items = new String[]{"Coding","Debugging","Testing","Documentation","Discussion"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        signup = findViewById(R.id.alloc_but);
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signup.setEnabled(false);
                new WorkAllocate(Work_Allocation_Fragment.this).execute();
            }
        });
    }

    class WorkAllocate extends AsyncTask<String, String, String> {

        private String signup_url = "https://collabcloud.000webhostapp.com/work_new.php";

        Context context;
        AlertDialog alertDialog;

        WorkAllocate(Context ctx) {
            context = ctx;
        }


        @Override
        protected String doInBackground(String... args) {
            String cname = Login_Fragment.cname;


            EditText proj = findViewById(R.id.jobname);
            String projname = proj.getText().toString();

            Spinner mySpinner = (Spinner) findViewById(R.id.taskID);
            String specification = mySpinner.getSelectedItem().toString();

            proj = findViewById(R.id.esttime);
            String esttime = proj.getText().toString();

            proj = findViewById(R.id.allot_to);
            String alloted = proj.getText().toString();

            proj = findViewById(R.id.taskdet);
            String details = proj.getText().toString();

            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("cname", "UTF-8") + "=" + URLEncoder.encode(cname, "UTF-8") + "&" + URLEncoder.encode("projname", "UTF-8") + "=" + URLEncoder.encode(projname, "UTF-8") + "&" + URLEncoder.encode("startdate", "UTF-8") + "=" + URLEncoder.encode(startdate, "UTF-8") + "&" + URLEncoder.encode("enddate", "UTF-8") + "=" + URLEncoder.encode(enddate, "UTF-8") + "&" + URLEncoder.encode("esthours", "UTF-8") + "=" + URLEncoder.encode(esttime, "UTF-8") + "&" + URLEncoder.encode("allotto", "UTF-8") + "=" + URLEncoder.encode(alloted, "UTF-8") + "&" + URLEncoder.encode("details", "UTF-8") + "=" + URLEncoder.encode(details, "UTF-8") + "&" + URLEncoder.encode("taskid", "UTF-8") + "=" + URLEncoder.encode(specification, "UTF-8");
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
            }
             catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Work Allocation");

        }

        @Override
        protected void onPostExecute(String result) {
            signup.setEnabled(true);

            try {

                if (result.compareTo("1") ==0)
                    alertDialog.setMessage("Success");
                else
                    alertDialog.setMessage("Failed to allocate project");
                alertDialog.show();
            }catch ( NullPointerException e){
                Toast.makeText(Work_Allocation_Fragment.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void startdate_wrkall(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        TextView date = findViewById(R.id.startdate);
                        month += 1;
                        startdate =   year + "/" + month + "/" + day;
                        date.setText(startdate);
                    }
                }, year,month,dayOfMonth);
        datePickerDialog.show();
    }

    public void enddate_wrkall(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        TextView date = findViewById(R.id.enddate);
                        month += 1;

                        enddate =  year + "/" + month + "/" + day;

                        date.setText(enddate);


                    }
                }, year,month,dayOfMonth);
        datePickerDialog.show();
    }
}