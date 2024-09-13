package by.it_academy.jd2.dto.artist;

import java.util.Objects;

public class ArtistSaveDTO {
    private String name;
    private String genre;
    private String biography;

    public ArtistSaveDTO() {
    }

    public ArtistSaveDTO(String name, String genre, String biography) {
        this.name = name;
        this.genre = genre;
        this.biography = biography;
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

    @Override
    public String toString() {
        return "ArtistSaveDTO{" +
                "name='" + name + '\'' +
                ", genre=" + genre +
                ", biography='" + biography + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistSaveDTO that = (ArtistSaveDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(genre, that.genre)
                && Objects.equals(biography, that.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, biography);
    }
}
