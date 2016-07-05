package com.aggarwalankur.testhttplibs.volley;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aggarwalankur.testhttplibs.QuestionPOJO;
import com.aggarwalankur.testhttplibs.R;

import java.util.List;

/**
 * Created by ggne0497 on 6/30/2016.
 */
public class VolleyAdapter extends RecyclerView.Adapter<VolleyAdapter.ViewHolder> {

    List<QuestionPOJO> mQuestionsList;

    public VolleyAdapter(List<QuestionPOJO> questionsList){
        mQuestionsList = questionsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stack_list_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(mQuestionsList != null && !mQuestionsList.isEmpty() && mQuestionsList.size() > position){
            viewHolder.title.setText(mQuestionsList.get(position).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return 0;
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
