package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

/**
 * Created by arjun on 21-07-2019.
 */

public class DeallocateList extends Activity {

    public List<RecyclerViewAdapterClass> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewFreelancerAdapter mAdapter;

    public static String dfree = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freelancer_deallocate);


        recyclerView = (RecyclerView) findViewById(R.id.list_deallocate);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mAdapter = new RecyclerViewFreelancerAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        new DeallocateList.Deallocate(DeallocateList.this).execute();


        recyclerView.addOnItemTouchListener(new RecyclerViewOnclick(getApplicationContext(), recyclerView, new RecyclerViewOnclick.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        RecyclerViewAdapterClass data = dataList.get(position);
                        dfree = data.getTitle();
                        new DeallocateList.Deallocate2(DeallocateList.this).execute();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );
    }

    private void prepareProjectData(JSONObject j) {


        try {
            // Getting Array of Contacts
            JSONArray array;
            array = j.getJSONArray("data");

            // looping through All Contacts
            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString("FreeName");
                String name = c.getString("ProjectName");
                String email = c.getString("TaskID");

                RecyclerViewAdapterClass movie = new RecyclerViewAdapterClass(id, name, email);
                dataList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        mAdapter.notifyDataSetChanged();
    }

    class Deallocate extends AsyncTask<String, String, String> {

        private String signup_url = "https://collabcloud.000webhostapp.com/get_pro.php";



        Context context;
        AlertDialog alertDialog;


        Deallocate(Context ctx) {
            context = ctx;
        }


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
                String post_data = URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(cname, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
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


            try{
                JSONObject j = new JSONObject(result);
                prepareProjectData(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }



    class Deallocate2 extends AsyncTask<String, String, String> {

        private String signup_url = "https://collabcloud.000webhostapp.com/deallocate.php";



        Context context;
        AlertDialog alertDialog;


        Deallocate2(Context ctx) {
            context = ctx;
        }


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
                String post_data = URLEncoder.encode("freelancer", "UTF-8") + "=" + URLEncoder.encode(dfree, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
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
                Toast.makeText(DeallocateList.this, result, Toast.LENGTH_SHORT).show();
                dataList.clear();
                mAdapter.notifyDataSetChanged();
                new DeallocateList.Deallocate(DeallocateList.this).execute();
            }catch(NullPointerException e){
                Toast.makeText(DeallocateList.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }
}
