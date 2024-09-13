package by.it_academy.jd2.dto.artist;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArtistResultDTO {
    private Long id;
    private String name;
    private String genre;
    private String biography;
    private String status;
    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;

    public ArtistResultDTO() {
    }

    public ArtistResultDTO(Long id, String name, String genre, String biography,
                           String status, LocalDateTime dateCreated, LocalDateTime lastModified) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.biography = biography;
        this.status = status;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getStatus() {
        return status; // Геттер и сеттер для статуса
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "ArtistResultDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", biography='" + biography + '\'' +
                ", status='" + status + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastModified=" + lastModified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistResultDTO that = (ArtistResultDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(genre, that.genre) && Objects.equals(biography, that.biography)
                && Objects.equals(status, that.status) && Objects.equals(dateCreated, that.dateCreated)
                && Objects.equals(lastModified, that.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, biography, status, dateCreated, lastModified);
    }
}
