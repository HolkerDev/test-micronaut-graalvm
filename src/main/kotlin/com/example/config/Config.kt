package com.example.config

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Factory
class Config {
    @Singleton
    fun client() : DynamoDbClient = DynamoDbClient.create()
}