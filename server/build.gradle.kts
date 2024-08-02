plugins {
    jacoco
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.springBoot) apply true
//    alias(libs.plugins.jib)
}

dependencies {
    annotationProcessor(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation(project(":common"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation(libs.mockk)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}