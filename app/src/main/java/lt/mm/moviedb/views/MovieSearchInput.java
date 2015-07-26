package lt.mm.moviedb.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
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
    private EditText inputView;

    InputListener inputListener;

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
        // No progress bar in search input for more decoupled handling.
//        progressBar = new ProgressBar(context);
//        LayoutParams progressParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        progressParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//        progressBar.setId(Utils.generateViewId());
//        progressBar.setVisibility(GONE);
//        addView(progressBar, progressParams);
        inputView = new EditText(context);
        LayoutParams editParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputView.addTextChangedListener(inputWatcher);
        addView(inputView, editParams);
    }

    //endregion

    //region Getters / Setters

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    //endregion

    //region Listeners

    TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (inputListener != null)
                inputListener.onInputChange(String.valueOf(charSequence));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //endregion

    //region Classes

    public interface InputListener {
        void onInputChange(String input);
    }

    //endregion

}
