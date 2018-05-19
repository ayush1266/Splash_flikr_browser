package org.thakur.ayush.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends baseActivity {

    private static final String TAG = "PhotoDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: PHOTODETAILS START");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        activateToolbar(true);
        Intent intent = getIntent();
        Photo photo = (Photo)intent.getSerializableExtra(PHOTO_TRANSFER);
        if(photo!=null){
            TextView photoTitle = (TextView)findViewById(R.id.photo_title);
            photoTitle.setText("Title: " + photo.getTitle());
            TextView photoTags = (TextView)findViewById(R.id.photo_tags);
            photoTags.setText("Tags: " + photo.getTags());
            TextView photoAuthor = (TextView)findViewById(R.id.photo_author);
            photoAuthor.setText("Author: " + photo.getAuthor());

            ImageView imageView = (ImageView)findViewById(R.id.phot_image);
            Picasso.with(this).load(photo.getLink()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder)
                    .into(imageView);

        }
    }

}
