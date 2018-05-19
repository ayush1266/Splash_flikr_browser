package org.thakur.ayush.flickrbrowser;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by win on 18-05-2018.
 */

public class baseActivity extends AppCompatActivity{
    private static final String TAG = "baseActivity";
    static final String FLICKR_QUERY  = "FLICKR_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    void activateToolbar(boolean enableHome){
        Log.d(TAG, "activateToolbar: starts" );
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Log.d(TAG, "activateToolbar: value "+ actionBar);
//        actionBar.setTitle(R.string.title_activity_photo_detail);
//        actionBar.setHomeButtonEnabled(true);

        if(actionBar!=null){
            Log.d(TAG, "activateToolbar: 111");
//            Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
            if(toolbar!=null){
                Log.d(TAG, "activateToolbar: 222");
               // setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();

            }
        }
        if(actionBar!=null){
            Log.d(TAG, "activateToolbar: 333");
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
        //actionBar.show();
    }

}
