package com.aggarwalankur.testhttplibs.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aggarwalankur.testhttplibs.MovieDataItem;
import com.aggarwalankur.testhttplibs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ankur on 7/12/2016.
 */
public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.ViewHolder> {

    private static final String IMAGE_FETCH_BASE_URL = "http://image.tmdb.org/t/p/w500";

    private List<MovieDataItem> mResultList;

    private Context mContext;

    public RetrofitAdapter(List<MovieDataItem> questionsList, Context context){
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

            Picasso.with(mContext)
                    .load(imageUrl)
                    .error(R.drawable.image_error)
                    .into(viewHolder.imgAvatar);

        }

    }

    @Override
    public int getItemCount() {
        return (mResultList == null) ? 0 : mResultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView imgAvatar;

        public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                title = (TextView) itemLayoutView.findViewById(R.id.title);
                imgAvatar = (ImageView) itemLayoutView.findViewById(R.id.avatar);
        }
    }

}
