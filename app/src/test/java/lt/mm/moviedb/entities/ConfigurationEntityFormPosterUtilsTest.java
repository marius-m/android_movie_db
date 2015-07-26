package lt.mm.moviedb.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ConfigurationEntityFormPosterUtilsTest {


    private ConfigurationEntity configurationEntity;

    @Before
    public void setUp() throws Exception {
        configurationEntity = new ConfigurationEntity();
    }

    @Test
    public void testProfileSizePickNull() throws Exception {
        ArrayList<Integer> sizes = configurationEntity.parseWidthSizes(null);
        assertNotNull(sizes);
        assertEquals(0, sizes.size());
    }

    @Test
    public void testProfileSizePickEmpty() throws Exception {
        ArrayList<Integer> sizes = configurationEntity.parseWidthSizes(new ArrayList<String>());
        assertNotNull(sizes);
        assertEquals(0, sizes.size());
    }

    @Test
    public void testProfileSizePickValid() throws Exception {
        ArrayList<String> profileSizes = new ArrayList<String>() {{
            add("w92");
            add("w154");
            add("w185");
            add("w342");
            add("w500");
            add("w780");
            add("original");
        }};

        ArrayList<Integer> sizes = configurationEntity.parseWidthSizes(profileSizes);
        assertNotNull(sizes);
        assertEquals(6, sizes.size());
        assertEquals(92, sizes.get(0).intValue());
        assertEquals(154, sizes.get(1).intValue());
        assertEquals(185, sizes.get(2).intValue());
        assertEquals(342, sizes.get(3).intValue());
        assertEquals(500, sizes.get(4).intValue());
        assertEquals(780, sizes.get(5).intValue());
    }

    @Test
    public void testClosestSize1() throws Exception {
        ArrayList<Integer> sizes = new ArrayList<Integer>() {{
            add(92);
            add(154);
            add(185);
            add(342);
            add(500);
            add(780);
        }};

        assertEquals(342, configurationEntity.closestSize(sizes, 420));
    }

    @Test
    public void testClosestSize2() throws Exception {
        ArrayList<Integer> sizes = new ArrayList<Integer>() {{
            add(92);
            add(154);
            add(185);
            add(342);
            add(500);
            add(780);
        }};

        assertEquals(780, configurationEntity.closestSize(sizes, 720));
    }

    @Test
    public void testClosestSize3() throws Exception {
        ArrayList<Integer> sizes = new ArrayList<Integer>() {{
            add(92);
            add(154);
            add(185);
            add(342);
            add(500);
            add(780);
        }};

        assertEquals(92, configurationEntity.closestSize(sizes, 50));
    }

    @Test
    public void testParseAsSizeValid() throws Exception {
        assertEquals(320, configurationEntity.parseSize("w320"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseAsSizeNull() throws Exception {
        configurationEntity.parseSize(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseAsSizeEmpty() throws Exception {
        configurationEntity.parseSize("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseAsSizeNoSplit() throws Exception {
        configurationEntity.parseSize("h320");
    }

    // Perfectly valid, though should not occur
//    @Test(expected = IllegalArgumentException.class)
//    public void testParseAsSizeMalformedDoubleSplit() throws Exception {
//        configurationEntity.parseSize("w320w322");
//    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseAsSizeDiffMalformed() throws Exception {
        configurationEntity.parseSize("w320h322");
    }

}