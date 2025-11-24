package com.proyecto.proyecto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:5173",  // Vite local
                    "http://localhost:3000",  // React local
                    "http://localhost:5174",  // Vite alternativo
                    "https://*.vercel.app",   // Si despliegas frontend en Vercel
                    "https://*.netlify.app"   // Si despliegas frontend en Netlify
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
