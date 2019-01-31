package com.example.slametsudarsono.popularmoviesstage2.data;

import android.provider.BaseColumns;

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns{

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "id";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_USERRATING = "vote_average";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
    }
}
