package lt.mm.moviedb.network;

import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import lt.mm.moviedb.Constants;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Wrapper class that is responsible for handling networking functions for the movie search
 *
 */

// Fixme : needs more abstraction for any networking client to be replaced
public class NetworkSearch {
    public static final String QUERY_PREFIX = "query=";

    private RequestQueue queue;

    LoadListener loadListener;

    boolean loading = false;
    private StringRequest stringRequest;

    public NetworkSearch(RequestQueue requestQueue) {
        if (requestQueue == null)
            throw new IllegalArgumentException("Cant function without RequestQueue!");
        queue = requestQueue;
    }

    public void search(String search) {
        if (TextUtils.isEmpty(search))
            return;
        if (isLoading())
            queue.stop();
        if (stringRequest != null)
            queue.cancelAll(stringRequest);
        String url = Constants.BASE_URL + Constants.LINK + "?" + Constants.API_KEY +"&" + QUERY_PREFIX + search;
        stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        queue.add(stringRequest);
        queue.start();
        setLoading(true);
    }

    //region Getters / Setters

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

    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if (stringRequest == null)
                return;
            if (loadListener != null)
                loadListener.onLoadSuccess(stringRequest.getUrl(), response);
            stringRequest = null;
            setLoading(false);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (loadListener != null)
                loadListener.onLoadFail(error.toString());
            stringRequest = null;
            setLoading(false);
        }

    };

    //endregion

    //region Classes

    /**
     * An interface that reports networking state changes
     */
    public interface LoadListener {
        /**
         * Reports if class is loading something
         * @param loading load state
         */
        void onLoadStatusChange(boolean loading);

        /**
         * Callback with a string response from the server
         * @param response
         */
        void onLoadSuccess(String request, String response);

        /**
         * Callback with a fail message from the server
         * @param error
         */
        void onLoadFail(String error);
    }

    //endregion

}
