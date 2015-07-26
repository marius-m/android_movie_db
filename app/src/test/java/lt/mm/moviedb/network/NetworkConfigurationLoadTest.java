package lt.mm.moviedb.network;

import android.content.Context;
import com.android.volley.RequestQueue;
import lt.mm.moviedb.entities.ConfigurationEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class NetworkConfigurationLoadTest {

    private NetworkConfiguration networkConfiguration;
    private NetworkConfiguration.Listener listener;

    @Before
    public void setUp() throws Exception {
        listener = mock(NetworkConfiguration.Listener.class);
        networkConfiguration = spy(new NetworkConfiguration(RuntimeEnvironment.application,
                listener, mock(RequestQueue.class)));
    }

    @Test
    public void testWhenLocalConfigurationExist() throws Exception {
        final ConfigurationEntity localConfiguration = mock(ConfigurationEntity.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return localConfiguration;
            }
        }).when(networkConfiguration).getLocalConfiguration();
        networkConfiguration.loadConfiguration();
        verify(listener, times(1)).onConfiguration(localConfiguration);
    }

    @Test
    public void testWhenRemoteConfSuccess() throws Exception {
        final ConfigurationEntity remoteConfiguration = mock(ConfigurationEntity.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                networkConfiguration.innerLoadListener.onLoadSuccess(remoteConfiguration);
                return null;
            }
        }).when(networkConfiguration).load();
        networkConfiguration.loadConfiguration();
        verify(listener, times(1)).onConfiguration(remoteConfiguration);
    }

    @Test
    public void testWhenRemoteConfFail() throws Exception {
        final ConfigurationEntity remoteConfiguration = mock(ConfigurationEntity.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                networkConfiguration.innerLoadListener.onLoadFail("Some error");
                return null;
            }
        }).when(networkConfiguration).load();
        networkConfiguration.loadConfiguration();
        verify(listener, times(1)).onConfiguration(any(ConfigurationEntity.class));
    }
}