package lt.mm.moviedb.adapters;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import lt.mm.moviedb.R;
import lt.mm.moviedb.entities.ConfigurationEntity;
import lt.mm.moviedb.entities.SearchItem;
import lt.mm.moviedb.persistance.Settings;
import lt.mm.moviedb.utils.Utils;

import java.util.ArrayList;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * An adapter to handle the display view of the search items
 */
public class SearchAdapter extends ArrayAdapter<SearchItem> {
    private final Context context;
    private final ArrayList<SearchItem> searchItems;
    private final ImageLoader imageLoader;
    private final ConfigurationEntity configurationEntity;

    public SearchAdapter(Context context, ArrayList<SearchItem> searchItems) {
        super(context, -1, searchItems);
        configurationEntity = Settings.getInstance(context).getConfigurationEntity();
        // screenSize = Utils.pullScreenSize(context);
        this.imageLoader = ImageLoader.getInstance();
        this.context = context;
        this.searchItems = searchItems;
    }

    public ArrayList<SearchItem> getSearchItems() {
        return searchItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listview_search_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleView = (TextView) rowView.findViewById(R.id.output_title);
            viewHolder.subtitleView = (TextView) rowView.findViewById(R.id.output_subtitle);
            viewHolder.imageResource = (ImageView) rowView.findViewById(R.id.image_res);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        SearchItem searchItem = searchItems.get(position);
        holder.titleView.setText(searchItem.getTitle());
        holder.subtitleView.setText(String.format("%s: %s", context.getString(R.string.search_item_releasedate), searchItem.getReleaseDate()));
        String imageUrl = configurationEntity.formPosterImageUrl(searchItem.getPosterPath(),
                context.getResources().getDimensionPixelSize(R.dimen.search_image_width));
        if (imageUrl != null)
            imageLoader.displayImage(imageUrl, holder.imageResource);
        return rowView;
    }

    static class ViewHolder {
        public ImageView imageResource;
        public TextView titleView;
        public TextView subtitleView;
    }
}
