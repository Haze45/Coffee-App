package com.example.coffee.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coffee.data.local.dao.CoffeeDao
import com.example.coffee.data.local.entity.CoffeeEntity

@Database(entities = [CoffeeEntity::class], version = 2)
abstract class CoffeeDatabase : RoomDatabase() {
    abstract val dao: CoffeeDao
}
