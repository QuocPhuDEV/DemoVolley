package com.example.hoangquocphu.demovolley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.hoangquocphu.demovolley.utils.Const;

public class StringRequestActivity extends AppCompatActivity {

    private String TAG = StringRequestActivity.class.getSimpleName();
    private Button btnStringRequest;
    private TextView msgResponse;
    private ProgressDialog mProgessDialog;

    //This tag will be used to cancel the request
    private String TAG_STRING_REQUEST = "String_Request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);

        btnStringRequest = (Button)findViewById(R.id.btn_StringRequest);
        msgResponse = (TextView)findViewById(R.id.msgResponse);

        mProgessDialog = new ProgressDialog(StringRequestActivity.this);
        mProgessDialog.setMessage("Loading...");
        mProgessDialog.setCancelable(false);

        btnStringRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeStringRequest();
            }
        });
    }

    private void showProgressDialog(){
        if (!mProgessDialog.isShowing()) {
            mProgessDialog.show();
        }
    }

    private void hideProgressDialog(){
        if (mProgessDialog.isShowing()) {
            mProgessDialog.dismiss();
        }
    }

    private void makeStringRequest(){
        showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_STRING_REQUEST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error:"+ error.getMessage());
                hideProgressDialog();

            }
        });

        //Adding request to request queue
        ApplicationController.getInstance().addToRequestQueue(stringRequest, TAG_STRING_REQUEST);
    }
}
