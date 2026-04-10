package com.petadoption.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * GRASP PRINCIPLE: Indirection
 *
 * WebConfig acts as an Indirection layer between incoming HTTP requests and
 * underlying resources (static files, CORS policy). Instead of scattering
 * these concerns across controllers, all cross-cutting web configuration is
 * centralised here.
 *
 * Responsibilities:
 *  1. CORS policy — allows the frontend SPA to call the REST API from any origin.
 *  2. Static resource handler — maps /css/**, /js/** to classpath:/static/.
 *
 * Route-to-template mapping is handled separately in ViewController.java,
 * maintaining proper separation of concerns (High Cohesion).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Register static resource handlers so that /css/** and /js/**
     * resolve from classpath:/static/ regardless of context path.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

    /**
     * Allow all origins for REST API endpoints consumed by the SPA.
     * MVC page endpoints (/, /login, /admin, etc.) are server-side
     * and do not require CORS headers.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
