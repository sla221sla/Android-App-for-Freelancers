package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * Created by arjun on 29-06-2018.
 */

public class Freelancer_Main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freelancer_main);

    }

    public void newUpdate( View view){

        Intent intent = new Intent(this, Update_Fragment.class);
        startActivity(intent);
    }

    public void viewUpdates( View view){

        Intent intent = new Intent(this, Freelancer_Updates.class);
        startActivity(intent);
    }
}
