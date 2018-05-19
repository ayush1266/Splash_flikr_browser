package org.thakur.ayush.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win on 16-05-2018.
 */

class GetFlickrjsonData extends AsyncTask<String,Void,List<Photo>> implements GetRawDATA.OnDownloadComplete {
    private static final String TAG = "GetFlickrjsonData";
    private List<Photo> mPhotolist= null;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll;
    private final OnDataAvailable mcallback;
    private boolean runningOnSamethread = false;
    public GetFlickrjsonData(String mBaseUrl, String mLanguage, boolean mMatchAll, OnDataAvailable mcallback) {
        this.mBaseUrl = mBaseUrl;
        this.mLanguage = mLanguage;
        this.mMatchAll = mMatchAll;
        this.mcallback = mcallback;
    }

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);

    }
    void executeOnSameThread(String searchCriteria){
        Log.d(TAG, "executeOnSameThread: starts");
        runningOnSamethread = true;
        String destinationUrl = createUrl(searchCriteria,mLanguage,mMatchAll);

        GetRawDATA getRawDATA = new GetRawDATA(this);
        getRawDATA.execute(destinationUrl);
        
        Log.d(TAG, "executeOnSameThread: ends");
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {

        Log.d(TAG, "onPostExecute: starts");
        if(mcallback!=null){
            mcallback.onDataAvailable(mPhotolist,DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: Ends");
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starst");
        String destinationUrl = createUrl(params[0],mLanguage,mMatchAll);
        GetRawDATA getRawDATA = new GetRawDATA(this);
        getRawDATA.runInSameThread(destinationUrl);
        Log.d(TAG, "doInBackground: ends");
        return mPhotolist;
    }

    private String createUrl(String searchCriteria, String lang, boolean matchAll){


        return Uri.parse(mBaseUrl).buildUpon().appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",matchAll?"ALL":"ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: status = "+ status);
        if(status == DownloadStatus.OK){
            mPhotolist = new ArrayList<>();
            try{
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");
                for(int i=0;i<itemsArray.length();i++){
                    JSONObject jsonPhoto =itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");
                    String link = photoUrl.replaceFirst("_m.","_b.");

                    Photo photoObject = new Photo(title,authorId,author,link,tags,photoUrl);
                    mPhotolist.add(photoObject);

                    Log.d(TAG, "onDownloadComplete: "+ photoObject.toString());
                }
            }catch(JSONException e){
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing JSON data"  + e.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if(mcallback!=null && runningOnSamethread){
            //now inform the calller the processing is done
            Log.d(TAG, "onDownloadComplete: sab mast hai" + status);
            mcallback.onDataAvailable(mPhotolist,status);


        }
    }
}
