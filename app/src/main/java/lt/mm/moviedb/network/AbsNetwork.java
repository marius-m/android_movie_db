package lt.mm.moviedb.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import lt.mm.moviedb.Constants;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Wrapper class that is responsible for handling networking functions
 */
public abstract class AbsNetwork<Type> {
    protected RequestQueue queue;
    protected Class classType;
    protected JsonRequest<Type> request;
    LoadListener loadListener;
    boolean loading = false;

    public AbsNetwork(RequestQueue requestQueue, Class classType) {
        if (requestQueue == null)
            throw new IllegalArgumentException("Cant function without RequestQueue!");
        this.queue = requestQueue;
        this.classType = classType;
    }

    /**
     * Defines the postfix of the url that should be loaded
     * @return
     */
    protected abstract String urlPostfix();

    /**
     * Initializes load method
     */
    public void load() {
        if (isLoading())
            queue.stop();
        if (request != null)
            queue.cancelAll(request);
        String url = Constants.BASE_URL + Constants.MOVIE_LINK + "?" + Constants.API_KEY +"&" + urlPostfix();
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


}
