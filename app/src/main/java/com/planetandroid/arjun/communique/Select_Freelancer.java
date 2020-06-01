package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by arjun on 29-06-2018.
 */


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
import java.util.List;

import static com.planetandroid.arjun.communique.Login_Fragment.cname;
import static com.planetandroid.arjun.communique.Login_Fragment.projname;
import static com.planetandroid.arjun.communique.Login_Fragment.taskname;


public class Select_Freelancer extends Activity {

    public List<RecyclerViewAdapterClass> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewReportAdapter mAdapter;


    int frequency;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_freelancer);


        recyclerView = (RecyclerView) findViewById(R.id.sel_freelncr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mAdapter = new RecyclerViewReportAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        new Select_Freelancer.ReturnUpdates(Select_Freelancer.this).execute();



        recyclerView.addOnItemTouchListener(new RecyclerViewOnclick(getApplicationContext(), recyclerView, new RecyclerViewOnclick.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        RecyclerViewAdapterClass data = dataList.get(position);
                        Toast.makeText(getApplicationContext(), data.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                        Float hrs = Float.valueOf(data.getHours());
                        Float perc = Float.valueOf(data.getPercentage());
                        if( hrs == 0 && perc == 0){
                            Toast.makeText(Select_Freelancer.this, "There are no updates.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Login_Fragment.freename = data.getTitle();
                            Intent intent = new Intent(getApplicationContext(), View_Updates.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );
    }



    private void prepareFreelancerData(JSONObject j) {


        try {
            // Getting Array of Contacts
            JSONArray array;
            array = j.getJSONArray("data");

            // looping through All Contacts
            for(int i = 0; i < array.length(); i++){
                JSONObject c = array.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString("Freelancer");
                String name = c.getString("Hours");
                String email = c.getString("PercentageDone");

                RecyclerViewAdapterClass movie = new RecyclerViewAdapterClass(id, name, email);
                dataList.add(movie);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mAdapter.notifyDataSetChanged();
    }



    class ReturnUpdates extends AsyncTask<String, String, String> {

        private String signup_url = "http://collabcloud.000webhostapp.com/freelancer.php";




        Context context;
        AlertDialog alertDialog;
        ReturnUpdates (Context ctx) {
            context = ctx;

        }


        /**
         * Before starting background thread Show Progress Dialog
         */

        /**
         * Creating product

         */
        @Override
        protected String doInBackground(String... args) {





            try {
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("project", "UTF-8") + "=" + URLEncoder.encode(projname, "UTF-8") + "&" + URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(cname, "UTF-8") + "&"+ URLEncoder.encode("task", "UTF-8") + "=" + URLEncoder.encode(taskname, "UTF-8");
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
            alertDialog.setTitle("Login Status");

        }

        @Override
        protected void onPostExecute(String  result) {

            try {
                JSONObject j = new JSONObject(result);

                prepareFreelancerData(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch(NullPointerException e){
                Toast.makeText(Select_Freelancer.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }


    }
}
