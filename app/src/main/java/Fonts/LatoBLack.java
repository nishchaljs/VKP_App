package Fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


@SuppressLint("AppCompatCustomView")
public class LatoBLack extends TextView {

    public LatoBLack(Context context) {
        super(context);
        applyCustomFont(context);

    }

    public LatoBLack(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);

    }

    public LatoBLack(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {

        Typeface customFont = FontCacheRagular.getTypeface("Bold.otf", context);

        setTypeface(customFont);
    }
}

