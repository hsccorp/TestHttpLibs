package com.aggarwalankur.testhttplibs.httpurlconxn;

import android.app.Activity;
import android.app.Fragment;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.aggarwalankur.testhttplibs.MovieDataItem;
import com.aggarwalankur.testhttplibs.MovieResults;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ankur on 7/18/2016.
 */

public class MovieFetchWithHttpUrlConxnFragment extends Fragment {

    public interface FetchCallbacks{
        void onListFetchCompleted(List<MovieDataItem> movieDataItems);
    }

    private static final String TAG = MovieFetchWithHttpUrlConxnFragment.class.getSimpleName();

    private List<MovieDataItem> mMovieList;

    private MovieListFetchTask mMovieListFetchTask;
    private FetchCallbacks mCallbackListener;

    private String listJson ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);

        try {
            File httpCacheDir = new File(getActivity().getCacheDir(), "http");
            long httpCacheSize = 1 * 1024 * 1024; // 1 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.i(TAG, "HTTP response cache installation failed:" + e);
        }

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
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setUseCaches(true);

                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                urlConnection.addRequestProperty("Cache-Control", "max-stale=" + maxStale);

                // Check the connection status
                if(urlConnection.getResponseCode() == 200)
                {

                    System.out.println("Cache enabled = " + urlConnection.getDefaultUseCaches());
                    // if response code = 200 ok
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    // Read the BufferedInputStream
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        sb.append(line);

                        //It is advisable to check isCancelled() so that we exit as soon as the user has cancelled
                        if(isCancelled()){
                            return null;
                        }
                    }
                    listJson = sb.toString();

                    // Disconnect the HttpURLConnection
                    urlConnection.disconnect();
                }
                else
                {
                    // Do something
                }


                if(isCancelled()){
                    return null;
                }

                Gson gson = new Gson();
                mMovieList = gson.fromJson(listJson, MovieResults.class).getResults();
                Log.d(TAG, "List Json = " + listJson);

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


    @Override
    public void onDestroy() {
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }

        super.onDestroy();
    }
}
