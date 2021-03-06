package lt.mm.moviedb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.android.volley.toolbox.Volley;
import lt.mm.moviedb.adapters.SearchAdapter;
import lt.mm.moviedb.controllers.UserInputController;
import lt.mm.moviedb.entities.SearchItem;
import lt.mm.moviedb.entities.SearchList;
import lt.mm.moviedb.network.LoadListener;
import lt.mm.moviedb.network.NetworkSearch;
import lt.mm.moviedb.utils.Log;
import lt.mm.moviedb.views.MovieSearchInput;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    public static final String INSTANCE_SEARCH_RESULT = "INSTANCE_SEARCH_RESULT";
    private NetworkSearch networkSearch;
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
        networkSearch = new NetworkSearch(SearchList.class, Volley.newRequestQueue(this));
        networkSearch.setLoadListener(loadListener);

        // View init
        searchInput = (MovieSearchInput) findViewById(R.id.input_search);
        searchInput.setInputListener(userInputListener);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        outputList = (ListView) findViewById(R.id.output_list);
        outputList.setAdapter(new SearchAdapter(this, searchItems));
        outputList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.BUNDLE_ID, searchItems.get(i).getId());
                startActivity(intent);
            }
        });

        // Initial state update
        updateViewsByLoadStateChange();
    }

    //region Convenience

    private void updateViewsByLoadStateChange() {
        progressBar.setVisibility((networkSearch.isLoading()) ? View.VISIBLE : View.GONE);
    }

    //endregion

    //region Listeners

    LoadListener<SearchList> loadListener = new LoadListener<SearchList>() {
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

    UserInputController.Listener userInputListener = new UserInputController.Listener() {
        @Override
        public void onInputChange(String input) {
            networkSearch.search(input);
        }

        @Override
        public void onInputClear() {
            searchItems.clear();
            ((ArrayAdapter) outputList.getAdapter()).notifyDataSetChanged();
        }
    };

    //endregion

    //region Instance saving

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(INSTANCE_SEARCH_RESULT, searchItems);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        searchItems.clear();
        searchItems.addAll((ArrayList<SearchItem>)savedInstanceState.getSerializable(INSTANCE_SEARCH_RESULT));
    }

    //endregion

}
