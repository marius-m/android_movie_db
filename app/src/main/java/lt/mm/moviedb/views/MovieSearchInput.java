package lt.mm.moviedb.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import lt.mm.moviedb.controllers.UserInputController;
import lt.mm.moviedb.utils.Utils;

/**
 * Created by mariusmerkevicius on 7/26/15.
 * View class that is responsible for handling user input.
 */
public class MovieSearchInput extends RelativeLayout {

    private EditText inputView;
    private UserInputController userInputController;

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
        userInputController = new UserInputController(null);
    }

    //region Convenience

    private void initChildViews(Context context) {
        inputView = new EditText(context);
        LayoutParams editParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inputView.addTextChangedListener(inputWatcher);
        addView(inputView, editParams);
    }

    //endregion

    //region Getters / Setters

    public void setInputListener(UserInputController.Listener inputListener) {
        userInputController.setListener(inputListener);
    }

    //endregion

    //region Listeners

    TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            userInputController.handleInput(String.valueOf(charSequence));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //endregion

    //region Instance saving

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.input = inputView.getText().toString();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.inputView.removeTextChangedListener(inputWatcher);
        this.inputView.setText(savedState.input);
        this.inputView.addTextChangedListener(inputWatcher);
    }

    private static class SavedState extends BaseSavedState {
        public String input;

        public SavedState(Parcel source) {
            super(source);
            if (source.readInt() == 1)
                input = source.readString();
        }
        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt((input != null) ? 1 : 0);
            if (input != null)
                dest.writeString(input);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }

    //endregion

    //region Classes

//    public interface InputListener {
//        void onInputChange(String input);
//    }

    //endregion

}
