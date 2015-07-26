package lt.mm.moviedb.network;

import com.android.volley.RequestQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class NetworkSearchQueryTest {

    private NetworkSearch networkSearch;
    private RequestQueue requestQueue;

    @Before
    public void setUp() throws Exception {
        requestQueue = mock(RequestQueue.class);
        networkSearch = new NetworkSearch(String.class, requestQueue);
    }

    @Test
    public void testNullInput() throws Exception {
        networkSearch.search(null);
        verify(requestQueue, never()).start();
    }

    @Test
    public void testEmptyInput() throws Exception {
        networkSearch.search("");
        verify(requestQueue, never()).start();
    }

    @Test
    public void testValidInput() throws Exception {
        networkSearch.search("1234");
        verify(requestQueue, times(1)).start();
    }
}