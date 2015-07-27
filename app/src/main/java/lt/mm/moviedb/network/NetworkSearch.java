package lt.mm.moviedb.network;

import android.text.TextUtils;
import com.android.volley.RequestQueue;
import lt.mm.moviedb.Constants;
import lt.mm.moviedb.entities.SearchList;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * Responsible for loading search term query
 */
public class NetworkSearch extends AbsNetwork<SearchList> {
    public static final String QUERY_POSTFIX = "query=";

    private String searchTerm;

    public NetworkSearch(Class classType, RequestQueue requestQueue) {
        super(requestQueue, classType);
    }

    @Override
    protected String urlPostfix() {
        return QUERY_POSTFIX + searchTerm;
    }

    @Override
    protected String urlSectionLink() {
        return Constants.LINK_SEARCH_MOVIE;
    }

    /**
     * Initializes the search
     * @param searchTerm provided search term
     */
    public void search(String searchTerm) {
        if (TextUtils.isEmpty(searchTerm))
            return;
        this.searchTerm = searchTerm;
        load();
    }

}
