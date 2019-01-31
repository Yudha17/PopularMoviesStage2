package com.example.slametsudarsono.popularmoviesstage2.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favoritetable")
public class FavoriteEntry {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "movieid")
    private int movieid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "userrating")
    private Double userrating;

    @ColumnInfo(name = "posterpath")
    private String posterpath;

    @ColumnInfo(name = "releasedate")
    private String releasedate;

    @ColumnInfo(name = "overview")
    private String overview;

    @Ignore
    public FavoriteEntry(int movieid, String title, Double userrating, String posterpath, String releasedate, String overview) {
        this.movieid = movieid;
        this.title = title;
        this.userrating = userrating;
        this.posterpath = posterpath;
        this.releasedate = releasedate;
        this.overview = overview;
    }

    public FavoriteEntry(int id, int movieid, String title, Double userrating, String posterpath, String releasedate, String overview) {
        this.id = id;
        this.movieid = movieid;
        this.title = title;
        this.userrating = userrating;
        this.posterpath = posterpath;
        this.releasedate = releasedate;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getUserrating() {
        return userrating;
    }

    public void setUserrating(Double userrating) {
        this.userrating = userrating;
    }

    public void setPosterpath(String posterpath){ this.posterpath = posterpath; }

    public String getPosterpath() { return posterpath; }

    public String getReleasedate() { return releasedate; }

    public void setReleasedate(String releasedate) { this.releasedate = releasedate; }

    public void setOverview(String image) { this.overview = overview; }

    public String getOverview() { return overview; }
}
