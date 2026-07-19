package com.example.coffee.di

import com.example.coffee.domain.repository.CoffeeRepository
import com.example.coffee.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCoffeeUseCases(repository: CoffeeRepository): CoffeeUseCases {
        return CoffeeUseCases(
            getProducts = GetProductsUseCase(repository),
            getProductById = GetProductByIdUseCase(repository),
            toggleFavorite = ToggleFavoriteUseCase(repository),
            insertProducts = InsertProductsUseCase(repository),
            getCartItems = GetCartItemsUseCase(repository),
            updateCartStatus = UpdateCartStatusUseCase(repository),
            updateQuantity = UpdateQuantityUseCase(repository),
            getFavouriteProducts = GetFavouriteProductsUseCase(repository)
        )
    }
}
