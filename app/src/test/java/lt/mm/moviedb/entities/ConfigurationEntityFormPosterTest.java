package lt.mm.moviedb.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ConfigurationEntityFormPosterTest {

    private ConfigurationEntity configurationEntity;

    @Before
    public void setUp() throws Exception {
        configurationEntity = new ConfigurationEntity();
    }

    @Test
    public void testNullPostfix() throws Exception {
        assertNull(configurationEntity.formPosterImageUrl(null, 400));
    }

    @Test
    public void testEmptyBase() throws Exception {
        configurationEntity.images.baseUrl = "";
        configurationEntity.images.profileSizes = new ArrayList<String>() {{
            add("w92");
            add("w154");
            add("w185");
            add("w342");
            add("w500");
            add("w780");
            add("original");
        }};
        assertEquals(null, configurationEntity.formPosterImageUrl("/asdf", 400));
    }

    @Test
    public void testEmptyProfileSizes() throws Exception {
        configurationEntity.images.baseUrl = "http://image.tmdb.org/t/p/";
        configurationEntity.images.profileSizes = new ArrayList<String>();
        assertEquals(null, configurationEntity.formPosterImageUrl("/asdf", 400));
    }

    @Test
    public void testValidPostfix() throws Exception {
        configurationEntity.images.baseUrl = "http://image.tmdb.org/t/p/";
        configurationEntity.images.profileSizes = new ArrayList<String>() {{
            add("w92");
            add("w154");
            add("w185");
            add("w342");
            add("w500");
            add("w780");
            add("original");
        }};
        assertEquals("http://image.tmdb.org/t/p/w342/asdf", configurationEntity.formPosterImageUrl("/asdf", 400));
    }
}