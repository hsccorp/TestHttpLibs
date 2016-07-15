package com.aggarwalankur.testhttplibs.okhttp;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aggarwalankur.testhttplibs.MovieDataItem;
import com.aggarwalankur.testhttplibs.R;
import com.aggarwalankur.testhttplibs.RecyclerItemDecoration;
import com.aggarwalankur.testhttplibs.volley.VolleyActivity;

import java.util.ArrayList;
import java.util.List;

import static com.aggarwalankur.testhttplibs.BuildConfig.MOVIE_DB_API_KEY;

public class OkHttpActivity extends AppCompatActivity implements MovieFetchFragment.FetchCallbacks{

    private static final String TAG = OkHttpActivity.class.getSimpleName();

    private static final String TAG_ASYNC_FRAGMENT = "async_fragment";

    // These are the parameters to build the Movie DB URL
    private static final String URL_SCHEME = "https";
    private static final String BASE_URL = "api.themoviedb.org";

    private static final String EXTRA_PATH_1 = "3";
    private static final String EXTRA_PATH_2 = "movie";
    private static final String TOP_RATED_PATH = "top_rated";

    private static final String API_KEY_PARAMETER = "api_key";

    private MovieFetchFragment mMovieFetchFragment;

    private LinearLayoutManager mManager;
    private OkHttpAdapter mAdapter;
    private List<MovieDataItem> mDataItems;

    private long requestStartTime = 0, requestEndTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getFragmentManager();
        mMovieFetchFragment = (MovieFetchFragment) fm.findFragmentByTag(TAG_ASYNC_FRAGMENT);


        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mMovieFetchFragment == null) {
            mMovieFetchFragment = new MovieFetchFragment();
            fm.beginTransaction().add(mMovieFetchFragment, TAG_ASYNC_FRAGMENT).commit();
            fm.executePendingTransactions();

            fetchTopRatedMovies();
        }else{
            fetchTopRatedMovies();
        }


        //Layout tasks
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView rView = (RecyclerView)findViewById(R.id.stack_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(mManager);
        mDataItems = new ArrayList<>();
        mAdapter = new OkHttpAdapter(mDataItems, this);
        rView.setAdapter(mAdapter);
        RecyclerItemDecoration itemDecoration = new RecyclerItemDecoration(this, R.dimen.item_offset);
        rView.addItemDecoration(itemDecoration);

        requestStartTime = System.currentTimeMillis();

    }

    @Override
    public void onListFetchCompleted(List<MovieDataItem> movieDataItems) {
        if(mDataItems == null){
            return;
        }

        if(movieDataItems == null){
            Toast.makeText(this, "Network Error ! Check connection ...", Toast.LENGTH_SHORT).show();
            return;
        }

        requestEndTime = System.currentTimeMillis();

        Toast.makeText(OkHttpActivity.this, "Request took "+ (requestEndTime - requestStartTime) + " ms", Toast.LENGTH_SHORT).show();

        mDataItems.clear();
        mDataItems.addAll(movieDataItems);

        mAdapter.notifyDataSetChanged();
    }

    private void fetchTopRatedMovies(){
        Uri.Builder uriBuilder = new Uri.Builder();

        String url = uriBuilder.scheme(URL_SCHEME)
                .authority(BASE_URL)
                .appendPath(EXTRA_PATH_1)
                .appendPath(EXTRA_PATH_2)
                .appendPath(TOP_RATED_PATH)
                .appendQueryParameter(API_KEY_PARAMETER, MOVIE_DB_API_KEY)
                .build().toString();

        mMovieFetchFragment.fetchListFromUrl(url);

    }

}
