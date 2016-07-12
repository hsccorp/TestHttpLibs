package com.aggarwalankur.testhttplibs.volley;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aggarwalankur.testhttplibs.MovieDataItem;
import com.aggarwalankur.testhttplibs.R;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Ankur on 6/30/2016.
 */
public class VolleyAdapter extends RecyclerView.Adapter<VolleyAdapter.ViewHolder> {

    private static final String IMAGE_FETCH_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private List<MovieDataItem> mResultList;

    private ImageLoader mImageLoader;

    private Context mContext;

    public VolleyAdapter(List<MovieDataItem> questionsList, Context context){
        mResultList = questionsList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stack_list_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(mResultList != null && !mResultList.isEmpty() && mResultList.size() > position){
            MovieDataItem currentItem = mResultList.get(position);
            viewHolder.title.setText(currentItem.getMovieTitle());

            String imageUrl = IMAGE_FETCH_BASE_URL + currentItem.getPosterPath();

            // Get the ImageLoader through your singleton class.
            mImageLoader = VolleyHelper.getInstance(mContext).getImageLoader();

            // Set the URL of the image that should be loaded into this view, and
            // specify the ImageLoader that will be used to make the request.
            viewHolder.imgAvatar.setImageUrl(imageUrl, mImageLoader);

            viewHolder.imgAvatar.setErrorImageResId(R.drawable.image_error);
        }

    }

    @Override
    public int getItemCount() {
        return (mResultList == null) ? 0 : mResultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public NetworkImageView imgAvatar;

        public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                title = (TextView) itemLayoutView.findViewById(R.id.title);
                imgAvatar = (NetworkImageView) itemLayoutView.findViewById(R.id.avatar);
        }
    }

}
