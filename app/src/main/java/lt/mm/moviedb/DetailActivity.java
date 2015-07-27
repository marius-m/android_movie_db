package lt.mm.moviedb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import lt.mm.moviedb.adapters.SearchAdapter;
import lt.mm.moviedb.controllers.UserInputController;
import lt.mm.moviedb.entities.DetailItem;
import lt.mm.moviedb.entities.SearchItem;
import lt.mm.moviedb.entities.SearchList;
import lt.mm.moviedb.network.JsonRequest;
import lt.mm.moviedb.network.LoadListener;
import lt.mm.moviedb.network.NetworkDetail;
import lt.mm.moviedb.network.NetworkSearch;
import lt.mm.moviedb.utils.Log;
import lt.mm.moviedb.views.MovieSearchInput;

import java.util.ArrayList;


public class DetailActivity extends Activity {
    public static final String BUNDLE_ID = "BUNDLE_ID";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        NetworkDetail networkDetail = new NetworkDetail(Volley.newRequestQueue(this));
        networkDetail.setLoadListener(loadListener);
        networkDetail.load(getIntent().getExtras().getInt(BUNDLE_ID));

        // Views
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility( (networkDetail.isLoading()) ? View.VISIBLE : View.GONE );
    }

    //region Listeners

    LoadListener loadListener = new LoadListener<DetailItem>() {
        @Override
        public void onLoadStatusChange(boolean loading) {
            if (progressBar != null)
                progressBar.setVisibility( (loading) ? View.VISIBLE : View.GONE );
        }

        @Override
        public void onLoadSuccess(DetailItem response) {

        }

        @Override
        public void onLoadFail(String error) {

        }
    };

    //endregion
}
