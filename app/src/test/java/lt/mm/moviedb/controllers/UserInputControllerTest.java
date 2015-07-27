package lt.mm.moviedb.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.*;

/**
 * Created by mariusmerkevicius on 7/27/15.
 */
@RunWith(RobolectricTestRunner.class)
public class UserInputControllerTest {

    private UserInputController inputController;
    private UserInputController.Listener inputListener;

    @Test
    public void testInitNull() throws Exception {
        // Should not throw anything
        new UserInputController(null);
    }

    @Before
    public void setUp() throws Exception {
        inputListener = mock(UserInputController.Listener.class);
        inputController = new UserInputController(inputListener);

    }

    @Test
    public void testValidInput() throws Exception {
        inputController.handleInput("asdf");
        verify(inputListener, never()).onInputChange("asdf");
        Robolectric.flushForegroundThreadScheduler();
        verify(inputListener, times(1)).onInputChange("asdf");
    }

    @Test
    public void testValidMultipleInputChange() throws Exception {
        inputController.handleInput("a");
        inputController.handleInput("as");
        inputController.handleInput("asd");
        inputController.handleInput("asdf");
        verify(inputListener, never()).onInputChange(any(String.class));
        Robolectric.flushForegroundThreadScheduler();
        verify(inputListener, times(1)).onInputChange("asdf");
    }

    @Test
    public void testValidMultipleInputChangeAndClear() throws Exception {
        inputController.handleInput("a");
        inputController.handleInput("as");
        inputController.handleInput("asd");
        inputController.handleInput("asdf");
        verify(inputListener, never()).onInputChange(any(String.class));
        Robolectric.flushForegroundThreadScheduler();
        verify(inputListener, times(1)).onInputChange("asdf");
        inputController.handleInput("");
        verify(inputListener, times(1)).onInputClear();
    }

    @Test
    public void testValidMultipleInputAndClearInstantly() throws Exception {
        inputController.handleInput("a");
        inputController.handleInput("as");
        inputController.handleInput("asd");
        inputController.handleInput("asdf");
        inputController.handleInput("");
        verify(inputListener, never()).onInputChange(any(String.class));
        verify(inputListener, times(1)).onInputClear();
    }
}