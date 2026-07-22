package com.example.coffee.domain.use_case

data class CoffeeUseCases(
    val getProducts: GetProductsUseCase,
    val getProductById: GetProductByIdUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val insertProducts: InsertProductsUseCase,
    val getCartItems: GetCartItemsUseCase,
    val updateCartStatus: UpdateCartStatusUseCase,
    val updateQuantity: UpdateQuantityUseCase,
    val getFavouriteProducts: GetFavouriteProductsUseCase,
    val getLocation: GetLocationUseCase
)
