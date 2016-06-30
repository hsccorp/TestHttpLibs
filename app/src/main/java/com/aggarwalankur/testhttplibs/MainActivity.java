package com.aggarwalankur.testhttplibs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
    }
}
