package lt.mm.moviedb.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import lt.mm.moviedb.utils.Utils;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * View class that is responsible for handling user input.
 */
public class MovieSearchInput extends RelativeLayout {

    private ProgressBar progressBar;
    private EditText inputView;

    public MovieSearchInput(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MovieSearchInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MovieSearchInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MovieSearchInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleAttr1) {
        initChildViews(context);
    }

    //region Convenience

    private void initChildViews(Context context) {
        progressBar = new ProgressBar(context);
        LayoutParams progressParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        progressBar.setId(Utils.generateViewId());
        progressBar.setVisibility(GONE);
        addView(progressBar, progressParams);
        inputView = new EditText(context);
        LayoutParams editParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editParams.addRule(RelativeLayout.LEFT_OF, progressBar.getId());
        addView(inputView, editParams);
    }

    //endregion

}
