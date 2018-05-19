package org.thakur.ayush.flickrbrowser;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by win on 17-05-2018.
 */

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {

    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotoList;
    private Context mcontext;

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickImageViewHoler";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public FlickrRecyclerViewAdapter(Context mcontext,List<Photo> mPhotoList) {
        this.mPhotoList = mPhotoList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CALLED BY LAYOUT MANAGER WHEN ITS NEED A NEW VIEW
        Log.d(TAG, "onCreateViewHolder: new View requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse,parent,false);

        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        //CALLED WHEN WANT NEW DATA IN EXISITING ROW

        if(mPhotoList==null || mPhotoList.size()==0){
            holder.thumbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText("NO PHOTOS AVAILABLE FOR YOUR QUERY \n TRY OTHER QUERY");
        }else
        {
            Photo photoItem = mPhotoList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle()+"-->" + position);
            Picasso.with(mcontext).load(photoItem.getImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail);
            holder.title.setText((photoItem.getTitle()));
            if(position %2 == 1)
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#F8BBD0"));
                //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#fffafa"));
                //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
            }
        }

    }

    @Override
    public int getItemCount() {
        //Log.d(TAG, "getItemCount: called");
        return ((mPhotoList!=null) &&(mPhotoList.size()!=0) ? mPhotoList.size() : 0);

    }
    void loadNewData(List<Photo> newPhotos){
        mPhotoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
        return ((mPhotoList!=null) &&(mPhotoList.size()!=0) ? mPhotoList.get(position) : null);
    }
}
