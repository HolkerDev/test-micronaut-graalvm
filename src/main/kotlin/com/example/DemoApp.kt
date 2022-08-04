package com.example

import io.micronaut.function.aws.runtime.MicronautLambdaRuntime
import io.micronaut.runtime.Micronaut

fun main(args: Array<String>) {
    Micronaut.build(*args)
        .eagerInitSingletons(true)
        .mainClass(MicronautLambdaRuntime::class.java)
        .start()
}