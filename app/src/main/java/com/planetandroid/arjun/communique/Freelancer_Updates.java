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
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.planetandroid.arjun.communique.Login_Fragment.cname;

/**
 * Created by arjun on 21-07-2019.
 */

public class Freelancer_Updates extends Activity {

    public List<RecyclerViewShowClass> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewShowAdapter mAdapter;

    int frequency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freelancer_updates);

        recyclerView = (RecyclerView) findViewById(R.id.freelancer_upd);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mAdapter = new RecyclerViewShowAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        new Freelancer_Updates.UpdatesFreelancer(Freelancer_Updates.this).execute();

    }


    private void prepareReportsData(JSONObject j) {


        try {
            // Getting Array of Contacts
            JSONArray array;
            array = j.getJSONArray("data");

            // looping through All Contacts
            for (int i = 0; i < array.length(); i++) {
                JSONObject c = array.getJSONObject(i);

                // Storing each json item in variable
                String date = c.getString("date");
                String rep = c.getString("info");

                RecyclerViewShowClass movie = new RecyclerViewShowClass(date, rep);
                dataList.add(movie);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mAdapter.notifyDataSetChanged();
    }


class UpdatesFreelancer extends AsyncTask<String, String, String> {

    private String signup_url = "http://collabcloud.000webhostapp.com/reports.php";


    //type1 = radioButton.getText().toString();

    Context context;
    AlertDialog alertDialog;

    UpdatesFreelancer(Context ctx) {
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
            String post_data = URLEncoder.encode("freename", "UTF-8") + "=" + URLEncoder.encode(cname, "UTF-8");
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
            JSONObject j = new JSONObject(result);
             prepareReportsData(j);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch( NullPointerException e){
            Toast.makeText(Freelancer_Updates.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

}


}


