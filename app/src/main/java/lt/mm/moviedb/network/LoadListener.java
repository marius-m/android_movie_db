package lt.mm.moviedb.network;

/**
 * An interface that reports networking state changes
 */
public interface LoadListener<Type> {
    /**
     * Reports if class is loading something
     * @param loading load state
     */
    void onLoadStatusChange(boolean loading);

    /**
     * Callback with a string response from the server
     * @param response
     */
    void onLoadSuccess(Type response);

    /**
     * Callback with a fail message from the server
     * @param error
     */
    void onLoadFail(String error);
}
