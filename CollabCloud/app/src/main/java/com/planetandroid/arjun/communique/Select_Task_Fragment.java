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

public class Select_Task_Fragment extends Activity {

    public List<RecyclerViewAdapterClass> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewProjectAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_task);

        recyclerView = (RecyclerView) findViewById(R.id.sel_task);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mAdapter = new RecyclerViewProjectAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        new Select_Task_Fragment.ReturnTasks(Select_Task_Fragment.this).execute();





        recyclerView.addOnItemTouchListener(new RecyclerViewOnclick(getApplicationContext(), recyclerView, new RecyclerViewOnclick.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        RecyclerViewAdapterClass data = dataList.get(position);
                        Toast.makeText(getApplicationContext(), data.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                        int hrs = Integer.parseInt(data.getHours());
                        int perc = Integer.parseInt(data.getPercentage());
                        if( hrs == 0 && perc == 0){
                            Toast.makeText(Select_Task_Fragment.this, "There are no updates.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Login_Fragment.taskname = data.getTitle();
                            Intent intent = new Intent(getApplicationContext(), Select_Freelancer.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                })
        );





    }

    private void prepareTaskData(JSONObject j) {


        try {
            JSONArray array;
            array = j.getJSONArray("data");

            for(int i = 0; i < array.length(); i++){
                JSONObject c = array.getJSONObject(i);

                String id = c.getString("TaskID");
                String name = c.getString("Hours");
                String email = c.getString("Percentage");

                RecyclerViewAdapterClass movie = new RecyclerViewAdapterClass(id, name, email);
                dataList.add(movie);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mAdapter.notifyDataSetChanged();
    }



    class ReturnTasks extends AsyncTask<String, String, String> {

        private String signup_url = "http://collabcloud.000webhostapp.com/task_status.php";

        Context context;
        AlertDialog alertDialog;
        ReturnTasks (Context ctx) {
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
                String post_data = URLEncoder.encode("company", "UTF-8") + "=" + URLEncoder.encode(cname, "UTF-8") + "&" + URLEncoder.encode("project", "UTF-8") + "=" + URLEncoder.encode(projname, "UTF-8");
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

            try{
                JSONObject j = new JSONObject(result);
                prepareTaskData(j);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch ( NullPointerException e){
                Toast.makeText(Select_Task_Fragment.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }

        }

    }

}