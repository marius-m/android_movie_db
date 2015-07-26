package lt.mm.moviedb.persistance;

import android.content.Context;
import lt.mm.moviedb.entities.ConfigurationEntity;

/**
 * Created by mariusmerkevicius on 7/26/15.
 */
public class Settings extends BaseSettings {
    public static final String SETTINGS_KEY = "SETTINGS_KEY";
    public static final String SETTINGS_KEY_CONFIGURATION = "SETTINGS_KEY_CONFIGURATION";
    private static Settings sInstance;
    private ConfigurationEntity configurationEntity;

    private Settings(Context context) {
        super(context);
    }

    public static Settings getInstance(Context context) {
        if (sInstance == null)
            sInstance = new Settings(context);
        return sInstance;
    }

    @Override
    protected void firstRunInit() {
        configurationEntity = (ConfigurationEntity) getObject(SETTINGS_KEY_CONFIGURATION); // -1 means there is no version saved
    }

    @Override
    protected String getSettingsKey() {
        return SETTINGS_KEY;
    }


    public ConfigurationEntity getConfigurationEntity() {
        return configurationEntity;
    }

    public void setConfigurationEntity(ConfigurationEntity configurationEntity) {
        this.configurationEntity = configurationEntity;
        putObject(SETTINGS_KEY_CONFIGURATION, configurationEntity);
    }

}
