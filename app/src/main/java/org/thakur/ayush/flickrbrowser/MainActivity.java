package org.thakur.ayush.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends baseActivity implements GetFlickrjsonData.OnDataAvailable
,RecyclerItemClickListerner.OnRecyclerClickListener{

    private static final String TAG = "MainActivity";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: STARTS");
        super.onCreate(savedInstanceState);
        MainActivity.this.setContentView(R.layout.activity_main);



       activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListerner(this,recyclerView,this));

        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this,new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);
      Log.d(TAG, "onCreate: ENDS");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: Starts");
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryResult = sharedPreferences.getString(FLICKR_QUERY,"");

        if(queryResult.length()>0){
            GetFlickrjsonData getFlickrjsonData = new GetFlickrjsonData("https://api.flickr.com/services/feeds/photos_public.gne","en-us",true ,this);
            getFlickrjsonData.execute(queryResult);
        }
        // RUNNING IN BACKGROUND
        //getFlickrjsonData.executeOnSameThread("android,nougat");  //RUNNING ON SAME THREAD
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent intent  = new Intent(this,SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected() returned: " + true);

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        if(status==DownloadStatus.OK){
            mFlickrRecyclerViewAdapter.loadNewData(data);


        }
        else
        {
            Log.e(TAG, "onDataAvailable: failed with status " + status );
        }
    }

    @Override
    public void onItemClicked(View view, int position) {
        Log.d(TAG, "onItemClicked: starts");
        Toast.makeText(MainActivity.this, "Long tap for Photo Details ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: starts");
        Toast.makeText(MainActivity.this, "Opening Photo Details " , Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,PhotoDetailActivity.class);
       intent.putExtra(PHOTO_TRANSFER, mFlickrRecyclerViewAdapter.getPhoto(position));
        Log.d(TAG, "onItemLongClick: new activity starting");
        startActivity(intent);
        //overridePendingTransition( R.transition.slide_in_up, R.transition.slide_out_up );
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
