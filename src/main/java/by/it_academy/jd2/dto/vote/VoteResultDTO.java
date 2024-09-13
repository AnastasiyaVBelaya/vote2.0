package by.it_academy.jd2.dto.vote;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public class VoteResultDTO {
    private Long artistId;
    private List<Long> genreIds;
    private String about;
    private LocalDateTime dateCreated;

    public VoteResultDTO() {
    }

    public VoteResultDTO(Long artistId, List<Long> genreIds, String about, LocalDateTime createdDate) {
        this.artistId = artistId;
        this.genreIds = genreIds;
        this.about = about;
        this.dateCreated = createdDate;
    }


    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteResultDTO that = (VoteResultDTO) o;
        return Objects.equals(artistId, that.artistId) &&
                Objects.equals(genreIds, that.genreIds) &&
                Objects.equals(about, that.about) &&
                Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, genreIds, about, dateCreated);
    }

    @Override
    public String toString() {
        return "VoteResultDTO{" +
                "artistId=" + artistId +
                ", genreIds=" + genreIds +
                ", about='" + about + '\'' +
                ", createdDate=" + dateCreated +
                '}';
    }
}
