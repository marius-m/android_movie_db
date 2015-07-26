package lt.mm.moviedb.network;

import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import lt.mm.moviedb.Constants;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Wrapper class that is responsible for handling networking functions for the movie search
 *
 */

// Fixme : needs more abstraction for any networking client to be replaced
public class NetworkSearch<Type> {
    public static final String QUERY_PREFIX = "query=";

    private RequestQueue queue;
    private Class classType;

    LoadListener loadListener;

    boolean loading = false;
    private JsonRequest<Type> request;

    public NetworkSearch(Class classType, RequestQueue requestQueue) {
        if (requestQueue == null)
            throw new IllegalArgumentException("Cant function without RequestQueue!");
        this.classType = classType;
        this.queue = requestQueue;
    }

    public void search(String search) {
        if (TextUtils.isEmpty(search))
            return;
        if (isLoading())
            queue.stop();
        if (request != null)
            queue.cancelAll(request);
        String url = Constants.BASE_URL + Constants.MOVIE_LINK + "?" + Constants.API_KEY +"&" + QUERY_PREFIX + search;
        request = new JsonRequest<>(classType, Request.Method.GET, url,
                successListener,
                errorListener);
        queue.add(request);
        queue.start();
        setLoading(true);
    }

    //region Getters / Setters

    /**
     * Sets the loading state
     * @param loading provided loading state
     */
    void setLoading(boolean loading) {
        if (this.loading == loading)
            return;
        this.loading = loading;
        if (loadListener != null)
            loadListener.onLoadStatusChange(loading);
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }

    //endregion

    //region Listeners

    Response.Listener<Type> successListener = new Response.Listener<Type>() {
        @Override
        public void onResponse(Type response) {
            if (request == null)
                return;
            if (loadListener != null)
                loadListener.onLoadSuccess(response);
            request = null;
            setLoading(false);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (loadListener != null)
                loadListener.onLoadFail(error.toString());
            request = null;
            setLoading(false);
        }
    };


    //endregion

    //region Classes

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

    //endregion

}
