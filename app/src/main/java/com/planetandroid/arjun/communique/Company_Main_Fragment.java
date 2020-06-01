package com.planetandroid.arjun.communique;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * Created by arjun on 29-06-2018.
 */

public class Company_Main_Fragment extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_main);

    }

    public void checkupdates(View view){
        Intent intent = new Intent(this, Select_Project_Fragment.class);
        startActivity(intent);
    }

    public void allocateuser(View view){
        Intent intent = new Intent(this, New_Freelancer.class);
        startActivity(intent);
    }

    public void newproject(View view){
        Intent intent = new Intent(this, Work_Allocation_Fragment.class);
        startActivity(intent);
    }

    public void deallocate(View view){
        Intent intent = new Intent(this, DeallocateList.class);
        startActivity(intent);
    }
}