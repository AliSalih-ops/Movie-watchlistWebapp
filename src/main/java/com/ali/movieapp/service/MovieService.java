package com.ali.movieapp.service;

import com.ali.movieapp.model.Movie;
import com.ali.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Get all movies for a specific user
    public List<Movie> getAllMoviesByUsername(String username) {
        return movieRepository.findByUsername(username);
    }

    // Get all movies (admin function)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Save a movie with username
    public Movie saveMovie(Movie movie, String username) {
        movie.setUsername(username);
        return movieRepository.save(movie);
    }

    // Save a movie (existing method)
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Get movie by ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    // Delete movie
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}