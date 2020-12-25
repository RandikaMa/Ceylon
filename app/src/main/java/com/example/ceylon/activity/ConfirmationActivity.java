package com.example.ceylon.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;


import com.example.ceylon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class ConfirmationActivity extends AppCompatActivity {

    Button okButton,sub;

    TextView fN,eM,pH,post_response_text;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        fN = findViewById(R.id.nm);
        pH = findViewById(R.id.ph);
        eM = findViewById(R.id.em);

        post_response_text = findViewById(R.id.post);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fN.setText(documentSnapshot.getString("FullName"));
                pH.setText(documentSnapshot.getString("UserPhone"));
                eM.setText(documentSnapshot.getString("UserEmail"));
            }
        });


        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Ceylon Food");
        mToolbar.setTitleTextColor(0xFFFFFFFF);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationActivity.super.onBackPressed();
            }
        });


        okButton = findViewById(R.id.ok_button);
        sub = findViewById(R.id.sub);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.cartFoods.clear();
                Intent launchNextActivity = new Intent(ConfirmationActivity.this, MainActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
            }
        });


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequest();
            }
        });


    }


    private void postRequest() {
        RequestQueue requestQueue= Volley.newRequestQueue(ConfirmationActivity.this);
        String url="http://app.ceylonlinux.lk/jsonFileWrite/testapi.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    post_response_text.setText("Data 1 : " + jsonObject.getString("data_1_post")+"\n");
                    post_response_text.append("Data 2 : " + jsonObject.getString("data_2_post")+"\n");
                    post_response_text.append("Data 3 : " + jsonObject.getString("data_3_post")+"\n");
                    post_response_text.append("Data 4 : " + jsonObject.getString("data_4_post")+"\n");
                }
                catch (Exception e){
                    e.printStackTrace();
                    post_response_text.setText("POST DATA : unable to Parse Json");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                post_response_text.setText("Post Data : Response Failed");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("data_1_post","Value 1 Data");
                params.put("data_2_post","Value 2 Data");
                params.put("data_3_post","Value 3 Data");
                params.put("data_4_post","Value 4 Data");
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_final, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
