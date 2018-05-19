package org.thakur.ayush.flickrbrowser;

import android.os.AsyncTask;
import android.util.EventLogTags;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

/**
 * Created by win on 15-05-2018.
 */

class GetRawDATA extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawDATA";

    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallBack;
    interface OnDownloadComplete {
        void onDownloadComplete(String data,DownloadStatus status);
    }
    public GetRawDATA(OnDownloadComplete callBack) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        mCallBack = callBack;
    }
    void runInSameThread(String s){
        Log.d(TAG, "runInSameThread: starts");

        //onPostExecute(doInBackground(s));
        if(mCallBack!= null) {
//            String result = doInBackground(s);
//            mCallBack.onDownloadComplete(result,mDownloadStatus);
            mCallBack.onDownloadComplete(doInBackground(s),mDownloadStatus);
        }

        Log.d(TAG, "runInSameThread: ends");
    }
    @Override
    protected void onPostExecute(String s) {
      //  Log.d(TAG, "onPostExecute: parameter = " + s);
        if(mCallBack!=null)
        {
            mCallBack.onDownloadComplete(s,mDownloadStatus);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: recieved parameter is " + strings[0]);

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            Log.d(TAG, "doInBackground: recived parameter = null so byebye");
            return null;
        }
        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "downloadXML: The responce code is " + response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while (null != (line = reader.readLine())) {
                result.append(line).append("\n");
            }
            mDownloadStatus = DownloadStatus.OK;
            // Log.d(TAG, "doInBackground: returnind" + result.toString());
            return result.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "downloadXML: INVALID URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "downloadXML: IO Exception reading data : " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "downloadXML: Security exception needs permision? " + e.getMessage());
            //e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();


            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: ERRPR CLOSING THE READER" + e.getMessage());
                }

            }
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }


}



