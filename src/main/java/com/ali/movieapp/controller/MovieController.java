package com.ali.movieapp.controller;

import com.ali.movieapp.model.Movie;
import com.ali.movieapp.service.MovieService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String listMovies(Model model) {
        // Get currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // Get movies for the current user
        model.addAttribute("movies", movieService.getAllMoviesByUsername(currentUsername));
        return "movie-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie-form";
    }

    @PostMapping("/save")
    public String saveMovie(@Valid @ModelAttribute("movie") Movie movie,
                           BindingResult bindingResult,
                           @RequestParam("imageFile") MultipartFile imageFile) {

        if (bindingResult.hasErrors()) {
            return "movie-form";
        }

        // Debugging information
        System.out.println("Received file: " + (imageFile != null && !imageFile.isEmpty() ? "not empty" : "empty"));
        
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Get original filename and create a unique name to prevent overwriting
                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                System.out.println("Original filename: " + originalFilename);
                
                // Create a unique filename using timestamp
                String fileExtension = "";
                if (originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String uniqueFilename = System.currentTimeMillis() + fileExtension;
                
                // Create external upload directory in user.home
                String uploadDirPath = System.getProperty("user.home") + "/movieapp-uploads";
                Path uploadDir = Paths.get(uploadDirPath);
                Files.createDirectories(uploadDir);
                
                Path filePath = uploadDir.resolve(uniqueFilename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                System.out.println("File saved to: " + filePath.toString());
                
                // Set the image path relative to the resource handler we'll configure
                movie.setImageName("/uploads/" + uniqueFilename);
                System.out.println("Image path set to: " + movie.getImageName());
                
            } catch (IOException e) {
                System.err.println("Error saving image: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Get currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        // Save movie with current username
        movieService.saveMovie(movie, currentUsername);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // Get currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + id));
        
        // Check if the movie belongs to the current user
        if (!currentUsername.equals(movie.getUsername())) {
            return "redirect:/movies?error=unauthorized";
        }
        
        model.addAttribute("movie", movie);
        return "movie-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        // Get currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + id));
        
        // Check if the movie belongs to the current user
        if (!currentUsername.equals(movie.getUsername())) {
            return "redirect:/movies?error=unauthorized";
        }
        
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

    @GetMapping("/{id}")
    public String viewMovieDetails(@PathVariable Long id, Model model) {
        // Get currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + id));
        
        // Check if the movie belongs to the current user
        if (!currentUsername.equals(movie.getUsername())) {
            return "redirect:/movies?error=unauthorized";
        }
        
        model.addAttribute("movie", movie);
        return "movie-details";  // Make sure this template exists in the correct location
    }
}