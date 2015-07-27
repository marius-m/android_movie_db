package lt.mm.moviedb.entities;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Entity that holds search item
 */
public class SearchItem implements Serializable {
    @JsonProperty(value = "adult")
    boolean adult;
    @JsonProperty(value = "backdrop_path")
    String backdropPath;
    @JsonProperty(value = "genre_ids")
    ArrayList<Integer> genreIds;
    @JsonProperty(value = "id")
    int id;
    @JsonProperty(value = "original_language")
    String originalLanguage;
    @JsonProperty(value = "original_title")
    String originalTitle;
    @JsonProperty(value = "overview")
    String overview;
    @JsonProperty(value = "popularity")
    double popularity;
    @JsonProperty(value = "poster_path")
    String posterPath;
    @JsonProperty(value = "release_date")
    String releaseDate;
    @JsonProperty(value = "title")
    String title;
    @JsonProperty(value = "video")
    boolean video;
    @JsonProperty(value = "vote_count")
    long voteCount;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public long getVoteCount() {
        return voteCount;
    }
}
