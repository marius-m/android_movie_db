package lt.mm.moviedb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.toolbox.Volley;
import lt.mm.moviedb.controllers.SplashTimeoutController;
import lt.mm.moviedb.entities.ConfigurationEntity;
import lt.mm.moviedb.network.NetworkConfiguration;


public class SplashActivity extends Activity {


    private SplashTimeoutController timeoutController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timeoutController = new SplashTimeoutController(timeoutListener);
        timeoutController.runTimer();
        NetworkConfiguration configuration =
                new NetworkConfiguration(this, configurationListener, Volley.newRequestQueue(this));
        configuration.loadConfiguration();
    }

    //region Listeners

    SplashTimeoutController.Listener timeoutListener = new SplashTimeoutController.Listener() {
        @Override
        public void onTimeout() {
            Intent i = new Intent(SplashActivity.this, SearchActivity.class);
            startActivity(i);
            finish();
        }
    };

    NetworkConfiguration.Listener configurationListener = new NetworkConfiguration.Listener() {
        @Override
        public void onConfiguration(ConfigurationEntity configurationEntity) {
            timeoutController.configurationReady();
        }
    };

    //endregion

}
