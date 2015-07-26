package lt.mm.moviedb.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NetworkSearchTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullInit() throws Exception {
        new NetworkSearch(String.class, null);
    }
}