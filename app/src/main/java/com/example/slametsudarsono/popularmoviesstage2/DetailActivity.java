package com.example.slametsudarsono.popularmoviesstage2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.slametsudarsono.popularmoviesstage2.ViewModel.AppExecutors;
import com.example.slametsudarsono.popularmoviesstage2.adapter.ReviewAdapter;
import com.example.slametsudarsono.popularmoviesstage2.adapter.TrailerAdapter;
import com.example.slametsudarsono.popularmoviesstage2.api.Client;
import com.example.slametsudarsono.popularmoviesstage2.api.Service;
import com.example.slametsudarsono.popularmoviesstage2.data.FavoriteContract;
import com.example.slametsudarsono.popularmoviesstage2.data.FavoriteDbHelper;
import com.example.slametsudarsono.popularmoviesstage2.database.AppDatabase;
import com.example.slametsudarsono.popularmoviesstage2.database.FavoriteEntry;
import com.example.slametsudarsono.popularmoviesstage2.model.Movie;
import com.example.slametsudarsono.popularmoviesstage2.model.Review;
import com.example.slametsudarsono.popularmoviesstage2.model.ReviewResponse;
import com.example.slametsudarsono.popularmoviesstage2.model.Trailer;
import com.example.slametsudarsono.popularmoviesstage2.model.TrailerResponse;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageView;
    String thumbnail, movieName, synopsis, rating, dateOfRelease;
    private RecyclerView rv;
    private MultiSnapRecyclerView mrv;
    private TrailerAdapter adapter;
    private List<Trailer> trailerList;
    private FavoriteDbHelper favoriteDbHelper;
    private Movie favorite;
    private final AppCompatActivity activity = DetailActivity.this;
    private AppDatabase mDb;
    List<FavoriteEntry> entries = new ArrayList<>();
    boolean exists;
    int movie_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
        mDb = AppDatabase.getInstance(getApplicationContext());


        imageView = (ImageView) findViewById(R.id.thumbnail_image_header);
        nameOfMovie = (TextView) findViewById(R.id.title);
        plotSynopsis = (TextView) findViewById(R.id.plotsynopsis);
        userRating = (TextView) findViewById(R.id.userrating);
        releaseDate = (TextView) findViewById(R.id.releasedate);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("original_title")) {

            thumbnail = getIntent().getExtras().getString("poster_path");
            movieName = getIntent().getExtras().getString("original_title");
            synopsis = getIntent().getExtras().getString("overview");
            rating = getIntent().getExtras().getString("vote_average");
            dateOfRelease = getIntent().getExtras().getString("release_date");
            movie_id = getIntent().getExtras().getInt("id");

            String poster = "https://image.tmdb.org/t/p/w500" + thumbnail;

            Picasso.with(this)
                    .load(poster)
                    .placeholder(R.drawable.loading)
                    .into(imageView);


            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(dateOfRelease);

        } else {
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
        }

        checkStatus(movieName);
        initViews();

    }

    @SuppressLint("StaticFieldLeak")
    private void checkStatus(final String movieName) {

        final MaterialFavoriteButton materialFavoriteButtonNice = (MaterialFavoriteButton) findViewById(R.id.buttonfavourite);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                entries.clear();
                entries = mDb.favoriteDao().loadTitle(movieName);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (entries.size() > 0) {
                    materialFavoriteButtonNice.setFavorite(true);
                    materialFavoriteButtonNice.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite == true) {
                                        saveFavorite();
                                        Snackbar.make(buttonView, "Added to Favorite",
                                                Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        deleteFavorite(movie_id);
                                        Snackbar.make(buttonView, "Removed from Favorite",
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });


                } else {
                    materialFavoriteButtonNice.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite == true) {
                                        saveFavorite();
                                        Snackbar.make(buttonView, "Added to Favorite",
                                                Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        int movie_id = getIntent().getExtras().getInt("id");
                                        deleteFavorite(movie_id);
                                        Snackbar.make(buttonView, "Removed from Favorite",
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }

            }


        }.execute();
    }

    public void saveFavorite() {

        final FavoriteEntry favoriteEntry = new FavoriteEntry(movie_id, movieName, (Double.parseDouble(rating)), thumbnail, dateOfRelease, synopsis);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().insertFavorite(favoriteEntry);
            }
        });

    }

    private void deleteFavorite(final int movie_id) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().deleteFavoriteWithId(movie_id);
            }
        });
    }

    private void initViews() {
        trailerList = new ArrayList<>();
        adapter = new TrailerAdapter(this, trailerList);

        rv = (RecyclerView) findViewById(R.id.recycler_view2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
        loadReview();
    }

    private void loadJSON() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Client Client = new Client();
                Service apiService = Client.getClient().create(Service.class);
                Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
                call.enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                        List<Trailer> trailer = response.body().getResults();
                        rv.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
                        rv.smoothScrollToPosition(0);
                    }

                    @Override
                    public void onFailure(Call<TrailerResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                        Toast.makeText(DetailActivity.this, "Error fetching trailer data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadReview() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Client Client = new Client();
                Service apiService = Client.getClient().create(Service.class);
                Call<ReviewResponse> call = apiService.getReview(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
                call.enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        List<Review> reviews = response.body().getResults();
                        mrv = (MultiSnapRecyclerView) findViewById(R.id.recycler_view3);
                        LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        mrv.setLayoutManager(firstManager);
                        mrv.setAdapter(new ReviewAdapter(getApplicationContext(), reviews));
                        mrv.smoothScrollToPosition(0);
                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {

                    }
                });
            }
        }catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, "unable to fetch data", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                shareContent();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareContent() {

        Bitmap bitmap = getBitmapFromView(imageView);
        try {
            File file = new File(this.getExternalCacheDir(), "logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, movieName);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }


}

