package com.example.thehumr.windrider.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.thehumr.windrider.R;

/**
 * Created by ondraboura on 19/05/2018.
 */

public class EvaluationUtils {

    public static final int EVALUATION_GRADE_1 = 30;
    public static final int EVALUATION_GRADE_2 = 60;
    public static final int EVALUATION_GRADE_3 = 90;
    public static final int EVALUATION_GRADE_4 = 120;
    public static final int EVALUATION_GRADE_5 = 150;

    public static void setupEvaluationImageView(Context context, ImageView imageView, double rideAngle, double windAngle) {
        if (rideAngle < 0) {
            rideAngle = 360 + rideAngle;
        }
        double relativeAngle = windAngle - rideAngle;
        if (relativeAngle < 0) {
            relativeAngle = 360 + relativeAngle;
        }

        if (360 - relativeAngle > EVALUATION_GRADE_5 || relativeAngle < EVALUATION_GRADE_5) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_1));
        } else if (360 - relativeAngle > EVALUATION_GRADE_4 || relativeAngle < EVALUATION_GRADE_4) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_2));
        } else if (360 - relativeAngle > EVALUATION_GRADE_3 || relativeAngle < EVALUATION_GRADE_3) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_3));
        } else if (360 - relativeAngle > EVALUATION_GRADE_2 || relativeAngle < EVALUATION_GRADE_2) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_4));
        } else if (360 - relativeAngle > EVALUATION_GRADE_1 || relativeAngle < EVALUATION_GRADE_1) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_5));
        } else {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_6));
        }

        imageView.setRotation((float) relativeAngle + 180);
    }

}
