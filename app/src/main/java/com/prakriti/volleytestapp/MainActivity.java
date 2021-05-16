package com.prakriti.volleytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue myQueue;
    TextView setuptxt, punchtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setuptxt = findViewById(R.id.setuptxt);
        punchtxt = findViewById(R.id.punchtxt);

        // create new request queue, separate thread
        myQueue = Volley.newRequestQueue(this);

        // create json object request
        JsonObjectRequest myjson = new JsonObjectRequest(Request.Method.GET, "https://official-joke-api.appspot.com/random_joke",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int id = response.getInt("id");
                    String type = response.getString("type");
                    String setup = response.getString("setup");
                    String punch = response.getString("punchline");

                    Log.i("RES","JOKE ID: "+id);
                    Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();
                    setuptxt.setText(setup);
                    punchtxt.setText(punch);
                }
                catch(JSONException e) {
                    Toast.makeText(getApplicationContext(),"Error occured. Please try again", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", error.getMessage());
            }
        });

        // pass json object to queue to run in background
        myQueue.add(myjson);

    }
}