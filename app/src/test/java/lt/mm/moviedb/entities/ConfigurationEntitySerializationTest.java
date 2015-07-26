package lt.mm.moviedb.entities;

import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.io.*;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ConfigurationEntitySerializationTest {


    public static final String RESPONSE = "{\"images\":{\"base_url\":\"http://image.tmdb.org/t/p/\",\"secure_base_url\":\"https://image.tmdb.org/t/p/\",\"backdrop_sizes\":[\"w300\",\"w780\",\"w1280\",\"original\"],\"logo_sizes\":[\"w45\",\"w92\",\"w154\",\"w185\",\"w300\",\"w500\",\"original\"],\"poster_sizes\":[\"w92\",\"w154\",\"w185\",\"w342\",\"w500\",\"w780\",\"original\"],\"profile_sizes\":[\"w45\",\"w185\",\"h632\",\"original\"],\"still_sizes\":[\"w92\",\"w185\",\"w300\",\"original\"]},\"change_keys\":[\"adult\",\"air_date\",\"also_known_as\",\"alternative_titles\",\"biography\",\"birthday\",\"budget\",\"cast\",\"certifications\",\"character_names\",\"created_by\",\"crew\",\"deathday\",\"episode\",\"episode_number\",\"episode_run_time\",\"freebase_id\",\"freebase_mid\",\"general\",\"genres\",\"guest_stars\",\"homepage\",\"images\",\"imdb_id\",\"languages\",\"name\",\"network\",\"origin_country\",\"original_name\",\"original_title\",\"overview\",\"parts\",\"place_of_birth\",\"plot_keywords\",\"production_code\",\"production_companies\",\"production_countries\",\"releases\",\"revenue\",\"runtime\",\"season\",\"season_number\",\"season_regular\",\"spoken_languages\",\"status\",\"tagline\",\"title\",\"translations\",\"tvdb_id\",\"tvrage_id\",\"type\",\"video\",\"videos\"]}";
    private ConfigurationEntity entity;

    @Before
    public void setUp() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectReader objectReader = mapper.reader(ConfigurationEntity.class);
        try {
            entity = objectReader.readValue(RESPONSE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDefaultInitTest() throws Exception {
        ConfigurationEntity entity = new ConfigurationEntity();
        assertNotNull(entity);
        assertEquals(0, entity.changeKeys.size());
        assertNotNull(entity.images);
        assertNotNull(entity.images.baseUrl);
        assertNotNull(entity.images.secureBaseUrl);
        assertNotNull(entity.images.backdropSizes);
        assertEquals(0, entity.images.backdropSizes.size());
        assertNotNull(entity.images.logoSizes);
        assertEquals(0, entity.images.logoSizes.size());
        assertNotNull(entity.images.stillSizes);
        assertEquals(0, entity.images.stillSizes.size());
        assertNotNull(entity.images.profileSizes);
        assertEquals(0, entity.images.profileSizes.size());
    }

    @Test
    public void testParseTest() throws Exception {
        assertNotNull(entity);
        verifyResponse(entity);
    }

    @Test
    public void testSerialize() throws Exception {
        byte[] serializedEntity = pickle(entity);
        ConfigurationEntity deserializedEntity = unpickle(serializedEntity, ConfigurationEntity.class);
        assertNotNull(deserializedEntity);
        verifyResponse(deserializedEntity);
    }

    private void verifyResponse(ConfigurationEntity entity) {
        assertEquals(53, entity.changeKeys.size());
        assertNotNull(entity.images);
        assertNotNull(entity.images.baseUrl);
        assertNotNull(entity.images.secureBaseUrl);
        assertNotNull(entity.images.backdropSizes);
        assertEquals(4, entity.images.backdropSizes.size());
        assertNotNull(entity.images.logoSizes);
        assertEquals(7, entity.images.logoSizes.size());
        assertNotNull(entity.images.stillSizes);
        assertEquals(4, entity.images.stillSizes.size());
        assertNotNull(entity.images.profileSizes);
        assertEquals(4, entity.images.profileSizes.size());
    }

    // Credit Jason S (http://stackoverflow.com/questions/3840356/how-to-test-in-java-that-a-class-implements-serializable-correctly-not-just-is)
    private static <T extends Serializable> byte[] pickle(T obj)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }

    private static <T extends Serializable> T unpickle(byte[] b, Class<T> cl)
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object o = ois.readObject();
        return cl.cast(o);
    }
}