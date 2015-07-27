package lt.mm.moviedb.controllers;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by mariusmerkevicius on 7/27/15.
 * A class that takes in user input, but only responds with last element inputed after delay.
 */
public class UserInputController {
    public static final int DELAY_MILLIS = 300;

    Handler handler;
    Listener listener;

    String input;

    public UserInputController(Listener listener) {
        this.listener = listener;
        this.handler = new Handler();
    }

    /**
     * Handles user provided input and invokes {@link lt.mm.moviedb.controllers.UserInputController.Listener}
     * @param input
     */
    public void handleInput(String input) {
        this.input = input;
        if (TextUtils.isEmpty(input)) {
            handler.removeCallbacks(reportRunnable);
            if (listener != null)
                listener.onInputClear();
            return;
        }
        handler.removeCallbacks(reportRunnable);
        handler.postDelayed(reportRunnable, DELAY_MILLIS);
    }

    //region Getters / Setters

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    //endregion

    //region Listeners

    Runnable reportRunnable = new Runnable() {
        @Override
        public void run() {
            if (listener != null)
                listener.onInputChange(input);
        }
    };

    //endregion

    //region Classes

    /**
     * Callback interface whenever user adds input
     */
    public interface Listener {
        /**
         * Method when input is ready to be handled outside
         * @param input user input
         */
        void onInputChange(String input);

        /**
         * Callback whenever input was cleared
         */
        void onInputClear();
    }

    //endregion

}
