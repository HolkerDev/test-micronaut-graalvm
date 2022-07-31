package com.example

import com.example.services.HomeService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import jakarta.inject.Inject
import org.slf4j.LoggerFactory

@Controller
open class HomeController(@Inject private val homeService: HomeService) {

    private val logger = LoggerFactory.getLogger(HomeController::class.java)

    @Get("/")
    fun get(): HttpResponse<*> {
        logger.info("Yep! I'm actually logging stuff despite all this graalvm limitations!")
        return HttpResponse.badRequest(mapOf("message" to "Hello world! ${homeService.giveFive()}"))
    }
}