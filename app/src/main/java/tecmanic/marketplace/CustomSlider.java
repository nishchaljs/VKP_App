package tecmanic.marketplace;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.R.id;
import com.daimajia.slider.library.R.layout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

public class CustomSlider extends BaseSliderView {

    ImageView target;

    public CustomSlider(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.home_slider, null);
        target = (ImageView) v.findViewById(R.id.image);


        bindEventAndShow(v, target);
        return v;
    }

}