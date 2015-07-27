package lt.mm.moviedb.network;

import android.content.Context;
import com.android.volley.RequestQueue;
import lt.mm.moviedb.Constants;
import lt.mm.moviedb.entities.ConfigurationEntity;
import lt.mm.moviedb.persistance.Settings;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Wrapper class that is responsible for handling networking functions for the movie search
 *
 */

public class NetworkConfiguration extends AbsNetwork<ConfigurationEntity> {
    private Context context;

    Listener listener;

    public NetworkConfiguration(Context context, Listener listener, RequestQueue requestQueue) {
        super(requestQueue, ConfigurationEntity.class);
        if (context == null)
            throw new IllegalArgumentException("Context must be provided for this class to function properly");
        if (listener == null)
            throw new IllegalArgumentException("Listener must be provided for this class to function properly");
        this.context = context;
        this.listener = listener;
        setLoadListener(innerLoadListener);
    }

    @Override
    protected String urlPostfix() {
        return null;
    }

    @Override
    protected String urlSectionLink() {
        return Constants.LINK_CONFIGURATION;
    }

    public void loadConfiguration() {
        ConfigurationEntity configurationEntity = getLocalConfiguration();
        if (configurationEntity != null) {
            listener.onConfiguration(configurationEntity);
            return;
        }
        load();
    }

    //region Convenience

    ConfigurationEntity getLocalConfiguration() {
        return Settings.getInstance(context).getConfigurationEntity();
    }

    //endregion

    //region Listeners

    LoadListener<ConfigurationEntity> innerLoadListener = new LoadListener<ConfigurationEntity>() {
        @Override
        public void onLoadStatusChange(boolean loading) { }

        @Override
        public void onLoadSuccess(ConfigurationEntity response) {
            Settings.getInstance(context).setConfigurationEntity(response);
            listener.onConfiguration(response);
        }

        @Override
        public void onLoadFail(String error) {
            // We should probably create a minimal version of configuration entity that should be used here
            ConfigurationEntity defaultConfigurationEntity = new ConfigurationEntity();
            defaultConfigurationEntity.setImages(new ConfigurationEntity.Images());
            Settings.getInstance(context).setConfigurationEntity(defaultConfigurationEntity);
            listener.onConfiguration(defaultConfigurationEntity);
        }
    };

    //endregion

    //region Classes

    /**
     * Listener when configuration is loaded successfully
     */
    public interface Listener {
        /**
         * Callback method when configuration is loaded successfully
         * @param configurationEntity provided coniguration
         */
        void onConfiguration(ConfigurationEntity configurationEntity);
    }

    //endregion
}
