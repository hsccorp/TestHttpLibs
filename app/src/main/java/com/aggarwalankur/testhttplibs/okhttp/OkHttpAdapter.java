package com.aggarwalankur.testhttplibs.okhttp;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aggarwalankur.testhttplibs.MovieDataItem;
import com.aggarwalankur.testhttplibs.R;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ankur on 7/15/2016.
 */
public class OkHttpAdapter extends RecyclerView.Adapter<OkHttpAdapter.ViewHolder>{

    private Context context;
    private List<MovieDataItem> mItemList;

    private static final String IMAGE_FETCH_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public OkHttpAdapter(List<MovieDataItem> itemList, Context context) {
        this.mItemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stack_list_item, null);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieDataItem currentItem = mItemList.get(position);

        holder.title.setText(currentItem.getMovieTitle());
    }

    @Override
    public int getItemCount() {
        if(mItemList == null || mItemList.isEmpty()){
            return 0;
        }
        return mItemList.size();
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
