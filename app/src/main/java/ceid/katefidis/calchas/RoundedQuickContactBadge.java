package ceid.katefidis.calchas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.QuickContactBadge;

import java.lang.reflect.Field;

/**
 * A rounded version of {@link QuickContactBadge]
 *
 * @author kishu27 (http://linkd.in/1laN852)
 */
public class RoundedQuickContactBadge extends QuickContactBadge {

    /**
     * This path is used to mask out the outer edges of a circle on this View
     */
    Path clipPath;

    public RoundedQuickContactBadge(Context context) {
        super(context);
        init(); //Set our initialization
    }

    public RoundedQuickContactBadge(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(); //Set our initialization
    }

    public RoundedQuickContactBadge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(); //Set our initialization
    }

    /**
     * Initialize our stuff
     */
    private void init() {

        //Use reflection to reset the default triangular overlay from default quick contact badge
        try {
            Field field = QuickContactBadge.class.getDeclaredField("mOverlay");
            field.setAccessible(true);

            //Using a drawable that draws a white circle. This could be set as null to not draw anything at all
            field.set(this, getResources().getDrawable(R.drawable.ic_circle));

        } catch (Exception e) {
            //No-op, just well off with the default overlay
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*
         * Create a new clip path. Anything outside this path will be clipped from this view and not drawn by onDraw method
         */
        clipPath = new Path();

        //Adding a circle. The circle will be positioned in the center using x = w/2 and y = w/2
        //Circle will be limiting it's radius to the smaller one of height or width.
        //Direction doesn't matter
        clipPath.addCircle(w / 2, h / 2, w < h ? w / 2 : h / 2, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Erase everything out of our little circle in clipPath and hence create the real rounded QuickContactBadge
        canvas.clipPath(clipPath);

        //Do everything else that original badge does. Drawing of the overlay is also handled there
        super.onDraw(canvas);
    }
}

