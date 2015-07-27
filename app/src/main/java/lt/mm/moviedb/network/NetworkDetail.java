package lt.mm.moviedb.network;

import com.android.volley.RequestQueue;
import lt.mm.moviedb.Constants;
import lt.mm.moviedb.entities.DetailItem;
import lt.mm.moviedb.utils.Log;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Responsible for loading details about the movie
 */
public class NetworkDetail extends AbsNetwork<DetailItem> {

    int id;

    public NetworkDetail(RequestQueue requestQueue) {
        super(requestQueue, DetailItem.class);
    }

    @Override
    protected String urlPostfix() {
        return null;
    }

    @Override
    protected String urlSectionLink() {
        return Constants.LINK_DETAIL_MOVIE + "/" + id;
    }

    public void load(int id) {
        this.id = id;
        load();
    }

}
