package com.sesac.climb_mates.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig:WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/v2/**")
            .allowedOrigins("www/googleapis.com/**")
            .allowedMethods("GET", "POST","PUT","DELETE")
            .allowCredentials(true)
    }
}