package com.example.coffee.di

import android.app.Application
import androidx.room.Room
import com.example.coffee.data.local.database.CoffeeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCoffeeDatabase(app: Application): CoffeeDatabase {
        return Room.databaseBuilder(
            app,
            CoffeeDatabase::class.java,
            "coffee_db"
        )
        .fallbackToDestructiveMigration() // Add this to handle schema changes
        .build()
    }

    @Provides
    @Singleton
    fun provideCoffeeDao(db: CoffeeDatabase) = db.dao
}
