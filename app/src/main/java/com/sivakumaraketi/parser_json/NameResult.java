package com.sivakumaraketi.parser_json;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 26-08-2018.
 */



public class NameResult  extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    public static final String KEY_NAME = "height";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namedetail);

        Intent intent = getIntent();

        String jsonString = intent.getStringExtra("jsonObject");
        Log.e(TAG, " jsonString: " + jsonString);
    }
}
