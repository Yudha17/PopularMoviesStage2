package com.example.slametsudarsono.popularmoviesstage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    // Favorite Dao
    @Query("SELECT * FROM favoritetable")
    LiveData<List<FavoriteEntry>> loadAllFavorite();

    @Query("SELECT * FROM favoritetable WHERE title = :title")
    List<FavoriteEntry> loadTitle(String title);

    @Insert
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteFavorite(FavoriteEntry favoriteEntry);

    @Query("DELETE FROM favoritetable WHERE movieid = :movie_id")
    void deleteFavoriteWithId(int movie_id);

    @Query("SELECT * FROM favoritetable WHERE id = :id")
    LiveData<FavoriteEntry> loadFavoriteById(int id);



}
