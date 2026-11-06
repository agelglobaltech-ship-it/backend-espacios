plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}

application {
    mainClass.set("com.upt.espacios.ApplicationKt")
}

dependencies {
    // Ktor Server Core y Netty Engine
    implementation("io.ktor:ktor-server-core-jvm:2.3.7")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.7")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.7")

    // Autenticación JWT y CORS
    implementation("io.ktor:ktor-server-auth-jvm:2.3.7")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.3.7")
    implementation("io.ktor:ktor-server-cors-jvm:2.3.7")

    // Base de Datos: Exposed ORM + Driver MySQL
    implementation("org.jetbrains.exposed:exposed-core:0.46.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.46.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.46.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.46.0")
    implementation("mysql:mysql-connector-java:8.0.33")

    // Logs
    implementation("ch.qos.logback:logback-classic:1.4.14")
}
tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.upt.espacios.ApplicationKt"
    }
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}