package lt.mm.moviedb.network;

import android.content.Context;
import com.android.volley.RequestQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
public class NetworkConfigurationInitTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullContext() throws Exception {
        new NetworkConfiguration(null, mock(NetworkConfiguration.Listener.class), mock(RequestQueue.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullListener() throws Exception {
        new NetworkConfiguration(mock(Context.class), null, mock(RequestQueue.class));
    }


}