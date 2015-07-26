package lt.mm.moviedb.persistance;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.*;

/**
 * Created by mariusmerkevicius on 7/26/15.
 */
public abstract class BaseSettings {

    private Context mContext;
    private SharedPreferences.Editor mCurrentEditor;

    public BaseSettings(Context context){
        if(context==null){
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.mContext = context;
        firstRunInit();
    }

    /**
     * Initialization method
     */
    protected abstract void firstRunInit();

    /**
     * Need to implement
     * @return unique shared preference key
     */
    protected abstract String getSettingsKey();

    /**
     * Call this method before starting modifying/setting values, at the end of modifications, you must call <code>commit</code>
     */
    public void beginEdit(){
        mCurrentEditor = mContext.getSharedPreferences(getSettingsKey(), 0).edit();
    }

    /**
     * Call this method after you finnish modify/set values
     */
    public void commit(){
        mCurrentEditor.commit();
        mCurrentEditor = null;
    }

    /**
     * Short method for saving boolean value. Do not call <code>beginEdit()</code> and <code>commit()</code>. They will be called automatically
     * @param key String key
     * @param value boolean value
     */
    public void saveBoolean(String key, boolean value){
        beginEdit();
        putBoolean(key,value);
        commit();
    }

    /**
     * Short method for saving int value. Do not call <code>beginEdit()</code> and <code>commit()</code>. They will be called automatically
     * @param key String key
     * @param value int value
     */
    public void saveInt(String key, int value){
        beginEdit();
        putInt(key,value);
        commit();
    }

    /**
     * Short method for saving long value. Do not call <code>beginEdit()</code> and <code>commit()</code>. They will be called automatically
     * @param key String key
     * @param value long value
     */
    public void saveLong(String key, long value){
        beginEdit();
        putLong(key, value);
        commit();
    }

    /**
     * Short method for saving String value. Do not call <code>beginEdit()</code> and <code>commit()</code>. They will be called automatically
     * @param key String key
     * @param value String value
     */
    public void saveString(String key, String value){
        beginEdit();
        putString(key,value);
        commit();
    }

    /**
     * @param key for boolean value
     * @param value boolean value
     */
    public void putBoolean(String key, boolean value){
        mCurrentEditor.putBoolean(key,value);
    }

    /**
     * @param key for String value
     * @param value String value
     */
    public void putString(String key, String value){
        mCurrentEditor.putString(key,value);
    }

    /**
     * @param key for int value
     * @param value boolean
     */
    public void putInt(String key, int value){
        mCurrentEditor.putInt(key,value);
    }

    /**
     * @param key for long value
     * @param value boolean
     */
    public void putLong(String key, long value){
        mCurrentEditor.putLong(key, value);
    }

    /**
     * delete all saved preferences. Do not call <code>beginEdit()</code> and <code>commit()</code>. They will be called automatically
     */
    public void resetSettings() {
        mContext.getSharedPreferences(getSettingsKey(),0).edit().clear().commit();
    }

    /**
     * Retrieves stored string value for key
     * @param key stored String value key
     * @return String value, default is <code>null</code>
     */
    public String getString(String key){
        return mContext.getSharedPreferences(getSettingsKey(),0).getString(key,null);
    }

    /**
     * Retrieves stored int value for key
     * @param key stored int value key
     * @return int value, default is <code>0</code>
     */
    public int getInt(String key){
        return mContext.getSharedPreferences(getSettingsKey(),0).getInt(key,0);
    }

    /**
     * Retrieves stored int value for key
     * @param key stored int value key
     * @return int value, if this value is not set, <code>defaultValue</code> is saved and returned
     */
    public int getInt(String key, int defaultValue){

        boolean valueExists = mContext.getSharedPreferences(getSettingsKey(),0).contains(key);
        int result = defaultValue;
        if(!valueExists){
            saveInt(key,defaultValue);
        } else {
            result = mContext.getSharedPreferences(getSettingsKey(),0).getInt(key,defaultValue);
        }
        return result;
    }

    /**
     * Retrieves stored long value for key
     * @param key stored long value key
     * @return long value, if this value is not set, <code>defaultValue</code> is saved and returned
     */
    public long getLong(String key, long defaultValue){

        boolean valueExists = mContext.getSharedPreferences(getSettingsKey(),0).contains(key);
        long result = defaultValue;
        if(!valueExists){
            saveLong(key,defaultValue);
        } else {
            result = mContext.getSharedPreferences(getSettingsKey(),0).getLong(key, defaultValue);
        }
        return result;
    }

    /**
     * Retrieves stored string value for key
     * @param key stored string value key
     * @return string value, if this value is not set, <code>defaultValue</code> is saved and returned
     */
    public String getString(String key, String defaultValue){

        boolean valueExists = mContext.getSharedPreferences(getSettingsKey(),0).contains(key);
        String result = defaultValue;
        if(!valueExists){
            saveString(key,defaultValue);
        } else {
            result = mContext.getSharedPreferences(getSettingsKey(),0).getString(key,defaultValue);
        }
        return result;
    }

    /**
     * Retrieves stored boolean value for key
     * @param key stored boolean value key
     * @return boolean value, default is <code>false</code>
     */
    public boolean getBoolean(String key){
        return mContext.getSharedPreferences(getSettingsKey(),0).getBoolean(key,false);
    }

    /**
     * Retrieves stored boolean value for key
     * @param key boolean string value key
     * @return boolean value, if this value is not set, <code>defaultValue</code> is saved and returned
     */
    public boolean getBoolean(String key, boolean defaultValue){

        boolean valueExists = mContext.getSharedPreferences(getSettingsKey(),0).contains(key);
        boolean result = defaultValue;
        if(!valueExists){
            saveBoolean(key,defaultValue);
        } else {
            result = mContext.getSharedPreferences(getSettingsKey(),0).getBoolean(key,defaultValue);
        }
        return result;
    }

    public void putObject(String key, Object object) {
        ObjectOutputStream objectOut = null;
        try {
            FileOutputStream fileOut = mContext.openFileOutput(key, Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            fileOut.getFD().sync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) { }
            }
        }
    }

    public Object getObject(String filename) {
        ObjectInputStream objectIn = null;
        Object object = null;
        try {
            FileInputStream fileIn = mContext.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();
        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        return object;
    }
}
