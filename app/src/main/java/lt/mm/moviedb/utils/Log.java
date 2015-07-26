package lt.mm.moviedb.utils;

import lt.mm.moviedb.Constants;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Helper class to output various data
 */
public class Log {

    /**
     * Logs out a custom string message to console.
     * Works only in debug mode.
     * @param msg provided string message
     */
    public static void debugWarning(String msg){
        if (Constants.DEBUG) {
            String str = caller(new Exception().getStackTrace()[1].getClassName(), new Exception().getStackTrace()[1].getMethodName());
            android.util.Log.w(Constants.TAG, str + "():" + msg);
        }
    }

    /**
     * Logs out a custom string message to console.
     * Works only in debug mode.
     * @param msg provided string message
     */
    public static void debugError(String msg){
        if (Constants.DEBUG) {
            String str = caller(new Exception().getStackTrace()[1].getClassName(), new Exception().getStackTrace()[1].getMethodName());
            android.util.Log.e(Constants.TAG, str + "():" + msg);
        }
    }

    /**
     * Logs out a custom string message to console.
     * Works only in debug mode.
     * @param msg provided string message
     */
    public static void debugSilent(String msg){
        if (Constants.DEBUG) {
            String str = caller(new Exception().getStackTrace()[1].getClassName(), new Exception().getStackTrace()[1].getMethodName());
            android.util.Log.d(Constants.TAG, str + "():" + msg);
        }
    }

    /**
     * Gets caller function. Useful when using combined with log messages, as it prints out from
     * where message was called.
     * @param paramString1
     * @param paramString2
     * @return
     */
    private static final String caller(String paramString1, String paramString2) {
        String str1 = "-";
        String str2 = "-";
        if (paramString1 != null) {
            String[] arrayOfString2 = paramString1.split("\\.");
            if (arrayOfString2.length > 0)
                str1 = arrayOfString2[(-1 + arrayOfString2.length)];
        }
        if (paramString2 != null) {
            String[] arrayOfString1 = paramString2.split("\\.");
            if (arrayOfString1.length > 0)
                str2 = arrayOfString1[(-1 + arrayOfString1.length)];
        }
        return str1 + "." + str2;
    }

}
