package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by arjun on 29-06-2018.
 */

public class Login_Register_Fragment extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_reg);

    }

    public void Login_logreg( View view){

        Intent intent = new Intent(this, Login_Fragment.class);
        startActivity(intent);
    }

    public void Register_logreg( View view){

        Intent intent = new Intent(this, Register_Fragment.class);
        startActivity(intent);
    }
}
