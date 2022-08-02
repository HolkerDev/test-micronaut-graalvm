package com.example

import com.example.services.HomeService
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

@Introspected
data class Message(val message: String)

@Controller
open class HomeController(@Inject private val homeService: HomeService) {

    private val logger = LoggerFactory.getLogger(HomeController::class.java)

    @Get("/")
    fun get(): HttpResponse<*> {
        logger.info("Yep! I'm actually logging stuff despite all this graalvm limitations!")
        return HttpResponse.badRequest(mapOf("message" to "Hello world! ${homeService.giveFive()}"))
    }

    @Get("/test")
    fun getTest(): HttpResponse<*> {
        logger.info("Yep! I'm actually logging stuff despite all this graalvm limitations!")
        val client = DynamoDbClient.create()
        client.putItem { builder ->
            builder.tableName("graalvm").item(mapOf("pk" to AttributeValue.fromS("test")))
        }
        return HttpResponse.badRequest(Message("Test message to show in json response!"))
    }
}