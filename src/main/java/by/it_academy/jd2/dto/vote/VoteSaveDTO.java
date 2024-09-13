package by.it_academy.jd2.dto.vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class VoteSaveDTO {
    private Long artistId;
    private List<Long> genreIds;
    private String about;

    public VoteSaveDTO() {
    }

    public VoteSaveDTO(Long artistId, List<Long> genreIds, String about) {
        this.artistId = artistId;
        this.genreIds = genreIds;
        this.about = about;
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


    @Override
    public String toString() {
        return "VoteSaveDTO{" +
                "artistId=" + artistId +
                ", genreIds=" + genreIds +
                ", about='" + about+'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteSaveDTO that = (VoteSaveDTO) o;
        return Objects.equals(artistId, that.artistId) &&
                Objects.equals(genreIds, that.genreIds) &&
                Objects.equals(about, that.about);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, genreIds, about);
    }
}
