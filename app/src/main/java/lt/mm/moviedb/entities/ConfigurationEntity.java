package lt.mm.moviedb.entities;

import android.text.TextUtils;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Data holder for configuration entity. This class should not initialize with null values by default
 */
public class ConfigurationEntity implements Serializable {
    @JsonProperty(value = "images")
    Images images;

    @JsonProperty(value = "change_keys")
    ArrayList<String> changeKeys;

    public ConfigurationEntity() {
        images = new Images();
        changeKeys = new ArrayList<>();
    }

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

        public Images() {
            baseUrl = "";
            secureBaseUrl = "";
            backdropSizes = new ArrayList<>();
            logoSizes = new ArrayList<>();
            profileSizes = new ArrayList<>();
            stillSizes = new ArrayList<>();
        }
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public ArrayList<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(ArrayList<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    //region Convenience

    public String formPosterImageUrl(String postfix, int screenWidth) {
        if (TextUtils.isEmpty(postfix))
            return null;
        if (images == null)
            return null;
        if (TextUtils.isEmpty(images.baseUrl))
            return null;
        if (images.profileSizes == null)
            return null;
        if (images.profileSizes.size() == 0)
            return null;
        ArrayList<Integer> sizes = parseWidthSizes(images.profileSizes);
        if (sizes.size() == 0)
            return null;
        return images.baseUrl + "w" + closestSize(sizes, screenWidth) + postfix;
    }

    public String formOriginalPosterImageUrl(String postfix) {
        if (TextUtils.isEmpty(postfix))
            return null;
        if (images == null)
            return null;
        if (TextUtils.isEmpty(images.baseUrl))
            return null;
        if (images.profileSizes == null)
            return null;
        if (images.profileSizes.size() == 0)
            return null;
        ArrayList<Integer> sizes = parseWidthSizes(images.profileSizes);
        if (sizes.size() == 0)
            return null;
        return images.baseUrl + "original" + postfix;
    }

    int closestSize(ArrayList<Integer> sizes, int screenWidth) {
        int nearest = -1;
        int bestDistanceFoundYet = Integer.MAX_VALUE;
        for (Integer size : sizes) {
            if (size == screenWidth) {
                return size;
            } else {
                int d = Math.abs(screenWidth - size);
                if (d < bestDistanceFoundYet) {
                    bestDistanceFoundYet = d;
                    nearest = size;
                }
            }
        }
        return nearest;
    }

    /**
     * Returns parsed out width sizes from the string reference
     * @param profileSizes provided profile sizes
     * @return
     */
    public ArrayList<Integer> parseWidthSizes(ArrayList<String> profileSizes) {
        ArrayList<Integer> parseSizes = new ArrayList<>();
        if (profileSizes == null)
            return parseSizes;
        if (profileSizes.size() == 0)
            return parseSizes;
        for (String profileSize : profileSizes) {
            try {
                parseSizes.add(parseSize(profileSize));
            } catch (IllegalArgumentException e) {}
        }
        return parseSizes;
    }

    /**
     * Parses out of string contains width number that could be parsed out and used when calculating screen
     * @param stringAsSize
     * @return
     * @throws IllegalArgumentException
     */
    int parseSize(String stringAsSize) throws IllegalArgumentException {
        if (TextUtils.isEmpty(stringAsSize))
            throw new IllegalArgumentException("Cannot convert to int!");
        try {
            return Integer.parseInt(stringAsSize.replaceAll("w", ""));
        } catch (NumberFormatException e) { }
        throw new IllegalArgumentException("Cannot convert to int!");
    }

    //endregion
}
