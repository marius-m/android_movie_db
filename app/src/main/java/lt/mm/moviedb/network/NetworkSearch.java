package lt.mm.moviedb.network;

import android.text.TextUtils;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Wrapper class that is responsible for handling networking functions for the moview serach
 *
 */

// Fixme : needs more abstraction for any networking client to be replaced
public class NetworkSearch {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String LINK = "search/movie";
    public static final String API_KEY = "api_key=f13d3cddb56308882dd225a7e06c92d1";
    public static final String QUERY_PREFIX = "query=terminator";

    private RequestQueue queue;

    LoadListener loadListener;

    boolean loading = false;

    public NetworkSearch(RequestQueue requestQueue) {
        if (requestQueue == null)
            throw new IllegalArgumentException("Cant function without RequestQueue!");
        queue = requestQueue;
    }

    public void search(String search) {
        if (TextUtils.isEmpty(search))
            return;
        String url = BASE_URL + LINK + "?" + API_KEY +"&" + QUERY_PREFIX;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
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
            if (loadListener != null)
                loadListener.onLoadSuccess(response);
            setLoading(false);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (loadListener != null)
                loadListener.onLoadFail(error.toString());
            setLoading(false);
        }

    };

    //endregion

    //region Classes

    public interface LoadListener {
        void onLoadStatusChange(boolean loading);
        void onLoadSuccess(String response);
        void onLoadFail(String error);
    }

    //endregion

}
