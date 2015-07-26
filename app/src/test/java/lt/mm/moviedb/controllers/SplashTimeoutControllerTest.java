package lt.mm.moviedb.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class SplashTimeoutControllerTest {

    private SplashTimeoutController.Listener listener;
    private SplashTimeoutController timeoutController;

    @Test(expected = IllegalArgumentException.class)
    public void testInit() throws Exception {
        new SplashTimeoutController(null);
    }

    @Before
    public void setUp() throws Exception {
        listener = mock(SplashTimeoutController.Listener.class);
        timeoutController = spy(new SplashTimeoutController(listener));
    }

    @Test
    public void testTimeoutHitFirst() throws Exception {
        timeoutController.runTimer();
        timeoutController.timeoutHit();
        verify(listener, never()).onTimeout();
        timeoutController.configurationReady();
        verify(listener, times(1)).onTimeout();
    }

    @Test
    public void testTimeoutOnDelay() throws Exception {
        timeoutController.runTimer();
        verify(timeoutController, never()).timeoutHit();
        Robolectric.flushForegroundThreadScheduler();
        verify(timeoutController, times(1)).timeoutHit();
    }

    @Test
    public void testConfigurationHitFirst() throws Exception {
        timeoutController.runTimer();
        timeoutController.configurationReady();
        verify(listener, never()).onTimeout();
        timeoutController.timeoutHit();
        verify(listener, times(1)).onTimeout();
    }
}