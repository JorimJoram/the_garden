package com.sesac.climb_mates.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ResourceConfig:WebMvcConfigurer {

    private val connectPath = "/img/**"
    private val resourcePath = "file:///D:/mat-zip/src/main/resources/static/img/"
        //"file:///Users/janghyolim/Desktop/code/climb-mates/src/main/resources/static/img/"
        //"file:///D:/mat-zip/src/main/resources/static/img/"

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler(connectPath).addResourceLocations(resourcePath)
    }

//    override fun addViewControllers(registry: ViewControllerRegistry) {
//        registry.addViewController("/").setViewName("redirect:/login")
//    }
}