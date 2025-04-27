package com.ali.movieapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import com.ali.movieapp.validation.MovieTitleNotTest;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{movie.title.notblank}")
    @MovieTitleNotTest
    @Size(min = 1, max = 100)
    private String title;

    @NotNull(message = "{movie.releaseDate.notnull}")
    @PastOrPresent(message = "{movie.releaseDate.pastorpresent}")
    private LocalDate releaseDate;

    @NotNull(message = "{movie.rating.notnull}")
    @Min(value = 1, message = "{movie.rating.min}")
    @Max(value = 10, message = "{movie.rating.max}")
    private Double rating;

    @ElementCollection
    @NotEmpty(message = "{movie.genres.notempty}")
    private List<String> genres;

    @Size(min = 15, message = "{movie.description.size}")
    private String description;

    @Column(name = "image_name")
    private String imageName;
    
    // 👇 Add this field for associating movies with users
    private String username;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    
    // 👇 Username getter/setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}