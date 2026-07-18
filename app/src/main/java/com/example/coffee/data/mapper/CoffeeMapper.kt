package com.example.coffee.data.mapper

import com.example.coffee.data.local.entity.CoffeeEntity
import com.example.coffee.domain.model.Product

fun CoffeeEntity.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        price = price,
        imageRes = imageRes,
        isFavorite = isFavorite,
        quantity = quantity
    )
}

fun Product.toCoffeeEntity(): CoffeeEntity {
    return CoffeeEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        imageRes = imageRes,
        isFavorite = isFavorite,
        quantity = quantity
    )
}
