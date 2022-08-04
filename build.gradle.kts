plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.5.1"
    // id("io.micronaut.graalvm") version "3.5.1"
    id("io.micronaut.aot") version "3.1.1"
}

version = "0.1"
group = "com.example"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("software.amazon.awssdk:dynamodb"){
        exclude(group = "software.amazon.awssdk", module = "apache-client")
        exclude(group = "software.amazon.awssdk", module = "netty-nio-client")
    }
    implementation("software.amazon.awssdk:url-connection-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    compileOnly("org.graalvm.nativeimage:svm")

    implementation("io.micronaut:micronaut-validation")

    implementation("io.micronaut.aws:micronaut-function-aws-custom-runtime")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

}


application {
    // mainClass.set("io.micronaut.function.aws.runtime.MicronautLambdaRuntime")
    mainClass.set("com.example.DemoAppKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
// graalvmNative {
//     binaries {
//         named("main") {
//             buildArgs.addAll(listOf(
//                 "-J--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core.configure=ALL-UNNAMED",
//                 "-J--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core.jdk=ALL-UNNAMED",
//             ))
//         }
//     }
// }

micronaut {
    runtime("lambda_provided")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
    aot {
        version.set("1.1.0")
        cacheEnvironment.set(true)
        optimizeServiceLoading.set(true)
        optimizeClassLoading.set(true)
        convertYamlToJava.set(true)
        precomputeOperations.set(true)
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    args(
        "-XX:MaximumHeapSizePercent=80",
        "-Dio.netty.allocator.numDirectArenas=0",
        "-Dio.netty.noPreferDirect=true"
    )
}



