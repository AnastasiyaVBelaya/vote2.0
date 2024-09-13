package by.it_academy.jd2.dto.genre;

import java.time.LocalDateTime;
import java.util.Objects;

public class GenreResultDTO {

    private Long id;
    private String name;
    private String description;
    private String status; // Новое поле для статуса
    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;

    public GenreResultDTO() {
    }

    public GenreResultDTO(Long id, String name, String description,
                          String status, LocalDateTime createdDate, LocalDateTime lastModified) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.dateCreated = createdDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "GenreResultDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdDate=" + dateCreated +
                ", lastModified=" + lastModified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreResultDTO that = (GenreResultDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(status, that.status) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(lastModified, that.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, dateCreated, lastModified);
    }
}
