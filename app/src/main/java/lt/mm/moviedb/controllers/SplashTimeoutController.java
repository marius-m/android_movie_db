package lt.mm.moviedb.controllers;

import android.os.Handler;
import lt.mm.moviedb.utils.Log;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * A class that handles that both, configuration setting and timeout would be hit
 */
public class SplashTimeoutController {
    public static final int SPLASH_TIME_OUT = 1000;

    Handler handler = new Handler();
    Listener listener;

    boolean timeoutHit = false;
    boolean configurationReady = false;

    public SplashTimeoutController(Listener listener) {
        if (listener == null)
            throw new IllegalArgumentException("Class cannot function without listener");
        this.listener = listener;
    }

    public void runTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeoutHit();
            }
        }, SPLASH_TIME_OUT);
    }

    public void timeoutHit() {
        Log.debugError("Timeout Hit!");
        this.timeoutHit = true;
        if (configurationReady && timeoutHit)
            listener.onTimeout();
    }

    public void configurationReady() {
        Log.debugError("Configuration ready!");
        this.configurationReady = true;
        if (timeoutHit && configurationReady)
            listener.onTimeout();
    }

    public interface Listener {
        void onTimeout();
    }
}
