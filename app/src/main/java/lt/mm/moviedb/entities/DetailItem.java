package lt.mm.moviedb.entities;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Entity that holds search item
 */
public class DetailItem implements Serializable {
    @JsonProperty(value = "adult")
    boolean adult;
    @JsonProperty(value = "backdrop_path")
    String backdropPath;
//    @JsonProperty(value = "belongs_to_collection")
//    String belongsToCollection;
    @JsonProperty(value = "budget")
    String budget;
    @JsonProperty(value = "genres")
    ArrayList<Link> genres;
    @JsonProperty(value = "homepage")
    String homepage;
    @JsonProperty(value = "id")
    int id;
    @JsonProperty(value = "imdb_id")
    String imdbId;
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
    @JsonProperty(value = "production_companies")
    ArrayList<Link> productionCompanies;
    @JsonProperty(value = "release_date")
    String releaseDate;
    @JsonProperty(value = "revenue")
    long revenue;
    @JsonProperty(value = "runtime")
    long runtime;
    @JsonProperty(value = "status")
    String status;
    @JsonProperty(value = "tagline")
    String tagline;
    @JsonProperty(value = "title")
    String title;
    @JsonProperty(value = "video")
    boolean video;
    @JsonProperty(value = "vote_average")
    double voteAverage;
    @JsonProperty(value = "vote_count")
    long voteCount;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

//    public String getBelongsToCollection() {
//        return belongsToCollection;
//    }

    public String getBudget() {
        return budget;
    }

    public ArrayList<Link> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
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

    public ArrayList<Link> getProductionCompanies() {
        return productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public long getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public static class Link {
        @JsonProperty(value = "id")
        int id;
        @JsonProperty(value = "name")
        String name;

    }

}
