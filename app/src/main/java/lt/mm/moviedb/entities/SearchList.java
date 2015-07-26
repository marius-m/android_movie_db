package lt.mm.moviedb.entities;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * An entity that holds a list of {@link lt.mm.moviedb.entities.SearchItem}
 * and other search information
 */
public class SearchList {
    @JsonProperty(value = "page")
    int page;
    @JsonProperty(value = "total_pages")
    int totalPages;
    @JsonProperty(value = "total_results")
    int totalResults;

    @JsonProperty(value = "results")
    ArrayList<SearchItem> results;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<SearchItem> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "SearchList{" +
                "page=" + page +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                ", results=" + results +
                '}';
    }
}
