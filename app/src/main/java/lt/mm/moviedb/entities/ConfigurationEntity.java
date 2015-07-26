package lt.mm.moviedb.entities;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mariusmerkevicius on 7/26/15.
 */
public class ConfigurationEntity implements Serializable {
    @JsonProperty(value = "images")
    Images images;

    @JsonProperty(value = "change_keys")
    ArrayList<String> changeKeys;

    public static class Images implements Serializable {
        @JsonProperty(value = "base_url")
        String baseUrl;
        @JsonProperty(value = "secure_base_url")
        String secureBaseUrl;

        @JsonProperty(value = "backdrop_sizes")
        ArrayList<String> backdropSizes;
        @JsonProperty(value = "logo_sizes")
        ArrayList<String> logoSizes;
        @JsonProperty(value = "profile_sizes")
        ArrayList<String> profileSizes;
        @JsonProperty(value = "still_sizes")
        ArrayList<String> stillSizes;
    }
}
