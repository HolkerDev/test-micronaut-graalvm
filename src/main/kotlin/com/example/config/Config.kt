package com.example.config

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

@Factory
class Config {
    @Singleton
    fun client(): DynamoDbClient {
        val client = DynamoDbClient.create()
        client.putItem { builder ->
            builder.tableName("graalvm")
                .item(mapOf("pk" to AttributeValue.fromS("test"), "sk" to AttributeValue.fromS("none")))
        }
        return client
    }
}