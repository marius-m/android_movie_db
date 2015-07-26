package lt.mm.moviedb.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class NetworkSearchTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullInit() throws Exception {
        new NetworkSearch(null);
    }
}