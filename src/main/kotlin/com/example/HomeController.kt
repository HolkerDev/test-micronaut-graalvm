package com.example

import com.example.services.HomeService
import io.micronaut.context.ApplicationContextBuilder
import io.micronaut.context.annotation.Bean
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.runtime.ApplicationConfiguration
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

@Introspected
data class Message(val message: String)

@Controller
open class HomeController(@Inject private val homeService: HomeService, @Inject private val dynamoDbClient: DynamoDbClient) {

    private val logger = LoggerFactory.getLogger(HomeController::class.java)

    @Get("/")
    fun get(): HttpResponse<*> {
        logger.info("Yep! I'm actually logging stuff despite all this graalvm limitations!")
        return HttpResponse.badRequest(mapOf("message" to "Hello world! ${homeService.giveFive()}"))
    }

    @Get("/test")
    fun getTest(): HttpResponse<*> {
        logger.info("Yep! I'm actually logging stuff despite all this graalvm limitations!")
        dynamoDbClient.putItem { builder ->
            builder.tableName("graalvm").item(mapOf("pk" to AttributeValue.fromS("test"), "sk" to AttributeValue.fromS("none")))
        }
        return HttpResponse.badRequest(Message("Test message to show in json response!"))
    }
}