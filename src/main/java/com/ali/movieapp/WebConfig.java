package com.ali.movieapp;

import com.ali.movieapp.converter.StringToListConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Web MVC configuration for the application.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Add custom formatters and converters.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToListConverter());
    }

    /**
     * Configure the locale resolver to determine the locale.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    /**
     * Configure the locale change interceptor to switch locales.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Register the interceptors.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Add a model attribute to expose the current locale.
     */
    @ModelAttribute("currentLocale")
    public String getCurrentLocale(Locale locale) {
        System.out.println("Current locale: " + locale);
        return locale.toString();
    }

    /**
     * Configure resource handlers for static resources and uploaded files.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure handler for uploaded movie images
        String uploadDir = System.getProperty("user.home") + "/movieapp-uploads";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/")
                .setCachePeriod(3600); // Cache for 1 hour

        // Log the upload directory path for debugging
        System.out.println("Configured upload directory: " + uploadDir);
    }
    
    /**
     * Configure view controllers for direct template mapping.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect root path to movies page
        registry.addRedirectViewController("/", "/movies");
    }
}