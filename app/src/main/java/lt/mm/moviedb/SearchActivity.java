package lt.mm.moviedb;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.android.volley.toolbox.Volley;
import lt.mm.moviedb.network.NetworkSearch;
import lt.mm.moviedb.utils.Log;
import lt.mm.moviedb.views.MovieSearchInput;


public class SearchActivity extends ActionBarActivity {

    private NetworkSearch networkSearch;
    private ProgressBar progressBar;
    private MovieSearchInput searchInput;
    private ListView outputList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Controller init
        networkSearch = new NetworkSearch(Volley.newRequestQueue(this));
        networkSearch.setLoadListener(loadListener);

        // View init
        searchInput = (MovieSearchInput) findViewById(R.id.input_search);
        searchInput.setInputListener(inputListener);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        outputList = (ListView) findViewById(R.id.output_list);

        // Initial state update
        updateViewsByLoadStateChange();
    }

    //region Convenience

    private void updateViewsByLoadStateChange() {
        progressBar.setVisibility((networkSearch.isLoading()) ? View.VISIBLE : View.GONE);
        outputList.setVisibility( (networkSearch.isLoading()) ? View.GONE : View.VISIBLE );
    }

    //endregion

    //region Listeners

    NetworkSearch.LoadListener loadListener = new NetworkSearch.LoadListener() {
        @Override
        public void onLoadStatusChange(boolean loading) {
            updateViewsByLoadStateChange();
        }

        @Override
        public void onLoadSuccess(String request, String response) {
            Log.debugWarning("Response ("+request+"): "+response);
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
