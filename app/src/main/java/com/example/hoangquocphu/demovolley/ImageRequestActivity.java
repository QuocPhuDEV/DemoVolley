package com.example.hoangquocphu.demovolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hoangquocphu.demovolley.utils.Const;

public class ImageRequestActivity extends AppCompatActivity {

    private static final String TAG = ImageRequestActivity.class.getSimpleName();
    private Button btnImageRequest;
    private NetworkImageView imgNetworkView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_request);

        btnImageRequest = (Button)findViewById(R.id.button_Image_Request);
        imgNetworkView = (NetworkImageView)findViewById(R.id.imgNetwork);
        imageView = (ImageView)findViewById(R.id.imgView);

        btnImageRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeImageRequest();
            }
        });
    }

    private void makeImageRequest(){
        ImageLoader imageLoader = ApplicationController.getInstance().getImageLoader();

        // If you are using NetworkImageView
        imgNetworkView.setImageUrl(Const.URL_IMAGE_REQUEST, imageLoader);

        // If you are using normal ImageView
        imageLoader.get(Const.URL_IMAGE_REQUEST, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error:"+ error.getMessage());

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean) {
                // Load image into ImageView
                imageView.setImageBitmap(paramImageContainer.getBitmap());
            }
        });

		/*
		// Loading image with placeholder and error image
		imageLoader.get(Const.URL_IMAGE_REQUEST, ImageLoader.getImageListener(imageView, R.drawable.ico_loading, R.drawable.ico_error));
		Cache cache = ApplicationController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(Const.URL_IMAGE_REQUEST);
		if (entry != null) {
			try {
				String data = new String(entry.data, "UTF-8");
				//handle data, like converting it to xml, json, bitmap etc...
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// cached response doesn't exists. Make a network call here
		}

		*/
    }
}
