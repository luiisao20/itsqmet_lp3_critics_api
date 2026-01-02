package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debes agregar un título")
    private String title;

    @NotBlank(message = "Este campo no puede estar vacío")
    @Column(columnDefinition = "LONGTEXT")
    private String overview;

    @NotBlank(message = "Este campo no puede estar vacío")
    private String imageLink;

    @NotNull(message = "Este campo no puede estar vacío")
    private LocalDate releaseDate;

    @NotNull(message = "Selecciona una opción")
    private String status;

    @NotNull(message = "Agrega una duración")
    private int runTime;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_has_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings;

    public Movie(String title, String overview, String imageLink, LocalDate releaseDate, String status, int runTime) {
        this.title = title;
        this.overview = overview;
        this.imageLink = imageLink;
        this.releaseDate = releaseDate;
        this.status = status;
        this.runTime = runTime;
    }
}
