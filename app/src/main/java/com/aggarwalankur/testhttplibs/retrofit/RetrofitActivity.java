package com.aggarwalankur.testhttplibs.retrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aggarwalankur.testhttplibs.MovieDataItem;
import com.aggarwalankur.testhttplibs.MovieResults;
import com.aggarwalankur.testhttplibs.R;
import com.aggarwalankur.testhttplibs.RecyclerItemDecoration;
import com.aggarwalankur.testhttplibs.volley.VolleyActivity;
import com.aggarwalankur.testhttplibs.volley.VolleyAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aggarwalankur.testhttplibs.BuildConfig.MOVIE_DB_API_KEY;

public class RetrofitActivity extends AppCompatActivity implements Callback {

    private static final String TAG = RetrofitActivity.class.getSimpleName();

    private static final String URL_SCHEME = "https";
    private static final String BASE_URL = "api.themoviedb.org";

    private static final String API_KEY_PARAMETER = "api_key";

    private LinearLayoutManager mManager;

    private RetrofitAdapter mAdapter;
    private List<MovieDataItem> mDataItems;

    private long requestStartTime = 0, requestEndTime = 0;

    private Call<MovieResults> mMoviesCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Layout tasks
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView rView = (RecyclerView)findViewById(R.id.stack_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(mManager);
        mDataItems = new ArrayList<>();
        mAdapter = new RetrofitAdapter(mDataItems, this);
        rView.setAdapter(mAdapter);
        RecyclerItemDecoration itemDecoration = new RecyclerItemDecoration(this, R.dimen.item_offset);
        rView.addItemDecoration(itemDecoration);


        //Build custom client for retrofit

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(this.getCacheDir(), 1 * 1024 * 1024))//1MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAMETER, MOVIE_DB_API_KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_SCHEME+ "://" + BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RequestInterface moviesInterface = retrofit.create(RequestInterface.class);

        mMoviesCall = moviesInterface.getMovieResults();

        //Get a request queue and make a retrofit request
        requestStartTime = System.currentTimeMillis();

        mMoviesCall.enqueue(this);

    }

    @Override
    public void onResponse(Call call, Response response) {
        if(call.isCanceled()){
            Toast.makeText(this, "Cancelled Request", Toast.LENGTH_SHORT).show();
            return;
        }

        requestEndTime = System.currentTimeMillis();

        Log.d(TAG, "Retrofit Response = " + response.toString());

        Toast.makeText(RetrofitActivity.this, "Request took "+ (requestEndTime - requestStartTime) + " ms", Toast.LENGTH_SHORT).show();

        MovieResults movieResults = (MovieResults) response.body();

        mDataItems.clear();

        mDataItems.addAll(movieResults.getResults());

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        if(call.isCanceled()){
            Toast.makeText(this, "Cancelled Request", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Network Error ! Check connection ...", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Retrofit Error = " + t.toString());
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");

        //Request cancellation.
        // Although this works but we do not want to cancel our requests on device rotation. So, commenting this
        mMoviesCall.cancel();

        super.onDestroy();
    }
}
