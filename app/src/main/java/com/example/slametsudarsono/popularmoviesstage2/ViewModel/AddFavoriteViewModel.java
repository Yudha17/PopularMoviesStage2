package com.example.slametsudarsono.popularmoviesstage2.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.slametsudarsono.popularmoviesstage2.database.AppDatabase;
import com.example.slametsudarsono.popularmoviesstage2.database.FavoriteEntry;

public class AddFavoriteViewModel extends ViewModel {

    private LiveData<FavoriteEntry> favorite;


    public AddFavoriteViewModel(AppDatabase database, int favoriteId){
        favorite = database.favoriteDao().loadFavoriteById(favoriteId);
    }


    public LiveData<FavoriteEntry> getFavorite() {return favorite;}
}
