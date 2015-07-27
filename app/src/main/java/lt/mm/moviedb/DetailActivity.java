package lt.mm.moviedb;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import lt.mm.moviedb.adapters.SearchAdapter;
import lt.mm.moviedb.controllers.UserInputController;
import lt.mm.moviedb.entities.DetailItem;
import lt.mm.moviedb.entities.SearchItem;
import lt.mm.moviedb.entities.SearchList;
import lt.mm.moviedb.network.JsonRequest;
import lt.mm.moviedb.network.LoadListener;
import lt.mm.moviedb.network.NetworkDetail;
import lt.mm.moviedb.network.NetworkSearch;
import lt.mm.moviedb.persistance.Settings;
import lt.mm.moviedb.utils.Log;
import lt.mm.moviedb.utils.Utils;
import lt.mm.moviedb.views.MovieSearchInput;

import java.util.ArrayList;


public class DetailActivity extends Activity {
    public static final String BUNDLE_ID = "BUNDLE_ID";
    public static final String INSTANCE_DETAIL_MODEL = "INSTANCE_DETAIL_MODEL";

    private ImageLoader imageLoader;

    private DetailItem detailItem;

    // Views
    private ProgressBar progressBar;
    private TextView titleView;
    private TextView tagView;
    private TextView summaryView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageLoader = ImageLoader.getInstance();
        NetworkDetail networkDetail = new NetworkDetail(Volley.newRequestQueue(this));
        networkDetail.setLoadListener(loadListener);
        if (savedInstanceState == null)
            networkDetail.load(getIntent().getExtras().getInt(BUNDLE_ID));

        // Views
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility( (networkDetail.isLoading()) ? View.VISIBLE : View.GONE );
        titleView = (TextView) findViewById(R.id.view_title);
        tagView = (TextView) findViewById(R.id.view_tag);
        summaryView = (TextView) findViewById(R.id.view_summary);
        imageView = (ImageView) findViewById(R.id.view_image);
    }

    //region Convenience

    private void updateViews() {
        titleView.setText(detailItem.getTitle());
        tagView.setText(detailItem.getTagline());
        summaryView.setText(detailItem.getOverview());
        imageLoader.displayImage(
                Settings.getInstance(this).getConfigurationEntity().formOriginalPosterImageUrl(detailItem.getPosterPath()),
                imageView
        );
    }

    //endregion

    //region Listeners

    LoadListener loadListener = new LoadListener<DetailItem>() {

        @Override
        public void onLoadStatusChange(boolean loading) {
            if (progressBar != null)
                progressBar.setVisibility( (loading) ? View.VISIBLE : View.GONE );
        }

        @Override
        public void onLoadSuccess(DetailItem response) {
            detailItem = response;
            updateViews();
        }

        @Override
        public void onLoadFail(String error) {
            // todo : inform user that the id does not exist !
            finish();
        }
    };

    //endregion

    //region Instance saving

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(INSTANCE_DETAIL_MODEL, detailItem);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        detailItem = (DetailItem) savedInstanceState.getSerializable(INSTANCE_DETAIL_MODEL);
        updateViews();
    }

    //endregion

}
