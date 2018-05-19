package org.thakur.ayush.flickrbrowser;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by win on 17-05-2018.
 */

class RecyclerItemClickListerner extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickLister";

    interface OnRecyclerClickListener{
        void onItemClicked(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListerner(Context context, final RecyclerView recyclerView, final OnRecyclerClickListener mListener) {
        this.mListener = mListener;
        mGestureDetector = new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null && mListener!=null)
                {
                    Log.d(TAG, "onLongPress: calling listener on item long clicked");
                    mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                }
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG, "onSingleTapConfirmed: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null && mListener!=null)
                {
                    Log.d(TAG, "onSingleTapConfirmed: calling listener on item clicked");
                    mListener.onItemClicked(childView,recyclerView.getChildAdapterPosition(childView));

                }
                return true;
            }
        });

    }
    
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts" );
        if(mGestureDetector!=null){
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: retruned "+result);
            return result;

        }
        else {
            Log.d(TAG, "onInterceptTouchEvent: returned false");
            return false;
        }

    }
}
