package lt.mm.moviedb;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import lt.mm.moviedb.adapters.SearchAdapter;
import lt.mm.moviedb.entities.SearchItem;
import lt.mm.moviedb.entities.SearchList;
import lt.mm.moviedb.network.NetworkSearch;
import lt.mm.moviedb.utils.Log;
import lt.mm.moviedb.views.MovieSearchInput;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    private NetworkSearch<SearchList> networkSearch;
    private ProgressBar progressBar;
    private MovieSearchInput searchInput;
    private ListView outputList;
    private ArrayList<SearchItem> searchItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Variables
        searchItems = new ArrayList<>();

        // Controller init
        networkSearch = new NetworkSearch<>(SearchList.class, Volley.newRequestQueue(this));
        networkSearch.setLoadListener(loadListener);
        configurationCall();

        // View init
        searchInput = (MovieSearchInput) findViewById(R.id.input_search);
        searchInput.setInputListener(inputListener);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        outputList = (ListView) findViewById(R.id.output_list);
        outputList.setAdapter(new SearchAdapter(this, searchItems));

        // Initial state update
        updateViewsByLoadStateChange();
    }

    private void configurationCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.BASE_URL + "configuration" + "?" + Constants.API_KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        queue.add(request);
        queue.start();
    }

    //region Convenience

    private void updateViewsByLoadStateChange() {
        progressBar.setVisibility((networkSearch.isLoading()) ? View.VISIBLE : View.GONE);
    }

    //endregion

    //region Listeners

    NetworkSearch.LoadListener<SearchList> loadListener = new NetworkSearch.LoadListener<SearchList>() {
        @Override
        public void onLoadStatusChange(boolean loading) {
            updateViewsByLoadStateChange();
        }

        @Override
        public void onLoadSuccess(SearchList response) {
            searchItems.clear();
            searchItems.addAll(response.getResults());
        }

        @Override
        public void onLoadFail(String error) {
            Log.debugWarning("Fail: "+error);
        }
    };

    MovieSearchInput.InputListener inputListener = new MovieSearchInput.InputListener() {
        @Override
        public void onInputChange(String input) {
            Log.debugError("Input:" +input);
            networkSearch.search(input);
        }
    };


    //endregion

}
