package com.aggarwalankur.testhttplibs.okhttp;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.aggarwalankur.testhttplibs.MovieDataItem;
import com.aggarwalankur.testhttplibs.MovieResults;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ankur on 7/15/2016.
 */

public class MovieFetchFragment extends Fragment {

    public interface FetchCallbacks{
        void onListFetchCompleted(List<MovieDataItem> movieDataItems);
    }

    private static final String TAG = MovieFetchFragment.class.getSimpleName();

    private List<MovieDataItem> mMovieList;

    private MovieListFetchTask mMovieListFetchTask;
    private FetchCallbacks mCallbackListener;

    private String listJson ;

    private CacheControl cache;
    private OkHttpClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);


        //cache = new CacheControl.Builder().maxStale(21600, TimeUnit.SECONDS).build();
        File cacheDir = new File(getActivity().getCacheDir(), UUID.randomUUID().toString());
        Cache cache = new Cache(cacheDir, 1* 1024 * 1024);
        client = new OkHttpClient.Builder().cache(cache).build();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallbackListener = (FetchCallbacks) activity;
    }

    public void fetchListFromUrl(String url){
        if(mMovieListFetchTask != null){
            mMovieListFetchTask.cancel(true);
        }
        mMovieListFetchTask = new MovieListFetchTask();
        mMovieListFetchTask.execute(new String[]{url});
    }

    public String getListJson(){
        return listJson;
    }

    public List<MovieDataItem> getMovieList(){
        return mMovieList;
    }

    private class MovieListFetchTask extends AsyncTask<String, Void, List<MovieDataItem>>{



        @Override
        protected List<MovieDataItem> doInBackground(String... params) {
            if(params == null || params[0] == null || params[0].isEmpty()){
                return null;
            }


            try {
                Request request = new Request.Builder()
                        //.cacheControl(cache)
                        .url(params[0])
                        .build();
                Response response = client.newCall(request).execute();

                if(isCancelled()){
                    return null;
                }

                String responseJson = response.body().string();

                listJson = responseJson;

                Gson gson = new Gson();

                mMovieList = gson.fromJson(responseJson, MovieResults.class).getResults();

                Log.d(TAG, "List Json = " + responseJson);

                return mMovieList;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieDataItem> movieDataItems) {
            super.onPostExecute(movieDataItems);

            if(isCancelled()){
                return;
            }

            if(mCallbackListener != null){
                mCallbackListener.onListFetchCompleted(movieDataItems);
            }
        }
    }

}
