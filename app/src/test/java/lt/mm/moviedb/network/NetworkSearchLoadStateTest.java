package lt.mm.moviedb.network;

import com.android.volley.RequestQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class NetworkSearchLoadStateTest {

    private RequestQueue requestQueue;
    private NetworkSearch networkSearch;
    private NetworkSearch.LoadListener loadListener;

    @Before
    public void setUp() throws Exception {
        requestQueue = mock(RequestQueue.class);
        networkSearch = new NetworkSearch(requestQueue);
        loadListener = mock(NetworkSearch.LoadListener.class);
        networkSearch.setLoadListener(loadListener);
    }

    @Test
    public void testLoadTrueWhenNotLoading() throws Exception {
        networkSearch.setLoading(true);
        verify(loadListener, times(1)).onLoadStatusChange(true);
    }

    @Test
    public void testLoadFalseWhenNotLoading() throws Exception {
        networkSearch.setLoading(false);
        verify(loadListener, never()).onLoadStatusChange(false);
    }

    @Test
    public void testLoadTrueWhenAlreadyLoading() throws Exception {
        networkSearch.loading = true;
        networkSearch.setLoading(true);
        verify(loadListener, never()).onLoadStatusChange(true);
    }

    @Test
    public void testLoadFalseWhenAlreadyLoading() throws Exception {
        networkSearch.loading = true;
        networkSearch.setLoading(false);
        verify(loadListener, times(1)).onLoadStatusChange(false);
    }

}