package com.example.coffee.di

import com.example.coffee.data.repository.CoffeeRepositoryImpl
import com.example.coffee.domain.repository.CoffeeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoffeeRepository(
        coffeeRepositoryImpl: CoffeeRepositoryImpl
    ): CoffeeRepository
}