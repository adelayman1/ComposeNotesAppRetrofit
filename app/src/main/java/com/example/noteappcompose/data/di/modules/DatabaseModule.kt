package com.example.noteappcompose.data.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.noteappcompose.data.utilities.Constants.USER_SHARED_PREFERENCES_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }
}