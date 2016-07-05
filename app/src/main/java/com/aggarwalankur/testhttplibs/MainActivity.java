package com.aggarwalankur.testhttplibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aggarwalankur.testhttplibs.okhttp.OkHttpActivity;
import com.aggarwalankur.testhttplibs.retrofit.RetrofitActivity;
import com.aggarwalankur.testhttplibs.volley.VolleyActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private Button mVolleyButton, mRetrofitButton, mOkHttpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVolleyButton = (Button) findViewById(R.id.button_volley);
        mRetrofitButton = (Button) findViewById(R.id.button_retrofit);
        mOkHttpButton = (Button) findViewById(R.id.button_okhttp);

        mVolleyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volleyIntent = new Intent(MainActivity.this, VolleyActivity.class);
                startActivity(volleyIntent);
            }
        });

        mRetrofitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retrofitIntent = new Intent(MainActivity.this, RetrofitActivity.class);
                startActivity(retrofitIntent);
            }
        });

        mOkHttpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent okHttpIntent = new Intent(MainActivity.this, OkHttpActivity.class);
                startActivity(okHttpIntent);
            }
        });
    }
}
