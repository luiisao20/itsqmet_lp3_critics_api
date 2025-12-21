package com.itsqmet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String overview;

    @Size(min = 20)
    private String imageLink;

    private LocalDate releaseDate;

    @NotNull
    private String status;

    @NotNull
    private int runTime;

    public Movie(String title, String overview, String imageLink, LocalDate releaseDate, String status, int runTime) {
        this.title = title;
        this.overview = overview;
        this.imageLink = imageLink;
        this.releaseDate = releaseDate;
        this.status = status;
        this.runTime = runTime;
    }
}
