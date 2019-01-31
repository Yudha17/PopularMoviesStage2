package com.example.slametsudarsono.popularmoviesstage2.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.slametsudarsono.popularmoviesstage2.database.AppDatabase;

public class AddFavoriteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mFavoriteId;

    public AddFavoriteViewModelFactory(AppDatabase database, int favoriteId){
        mDb = database;
        mFavoriteId = favoriteId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddFavoriteViewModel(mDb, mFavoriteId);
    }
}
