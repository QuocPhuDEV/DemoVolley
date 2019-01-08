package com.example.hoangquocphu.demovolley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hoangquocphu.demovolley.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSonRequestActivity extends AppCompatActivity  implements View.OnClickListener {

    private String TAG = JSonRequestActivity.class.getSimpleName();
    private Button btnJsonObjRequest;
    private Button btnJsonArrRequest;
    private TextView msgResponse;
    private ProgressDialog mProgressDialog;

    // These tags will be used to cancel the requests.
    private String TAG_JSONOBJ_REQUEST = "jsonobject_request";
    private String TAG_JSONARR_REQUEST = "jsonarrayobject_request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_request);

        btnJsonObjRequest = (Button)findViewById(R.id.button_JSonObj_Request);
        btnJsonArrRequest = (Button)findViewById(R.id.button_JSonArray_Request);
        msgResponse       = (TextView)findViewById(R.id.msgResponse);

        mProgressDialog = new ProgressDialog(JSonRequestActivity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);

        btnJsonObjRequest.setOnClickListener(this);
        btnJsonArrRequest.setOnClickListener(this);
    }

    private void showProgressDialog(){
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog(){
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Making json object request
     */

    private void makeJsonObjectRequest(){
        showProgressDialog();
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_OBJECT_REQUREST, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,  "Error:" +error.getMessage() );
            }
        }){
            /* Passing some request headers*/
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("pass", "password123");
                return  params;
            }
        } ;

        // Adding request to request queue
        ApplicationController.getInstance().addToRequestQueue(jsonObjRequest, TAG_JSONOBJ_REQUEST);

        //Canceling request
//		ApplicationController.getInstance().getRequestQueue().cancelAll(TAG_JSONOBJ_REQUEST);

    }

    /*Making son array request
     * */
    private void makeJsonArrayRequest(){
        showProgressDialog();
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(Const.URL_JSON_ARRAY_REQUEST,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error:" + error.getMessage());
                hideProgressDialog();
            }
        });
        // Adding request to request queue
        ApplicationController.getInstance().addToRequestQueue(jsonArrRequest, TAG_JSONARR_REQUEST);

        //Canceling request
//		ApplicationController.getInstance().getRequestQueue().cancelAll(TAG_JSONARR_REQUEST);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_JSonObj_Request:
                makeJsonObjectRequest();
                break;
            case R.id.button_JSonArray_Request:
                makeJsonArrayRequest();
                break;

            default:
                break;
        }
    }
}
