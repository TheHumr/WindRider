package com.example.thehumr.windrider.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.thehumr.windrider.R;

import java.util.List;

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
        double relativeAngle = getRelativeWindAngle(rideAngle, windAngle);

        if (relativeAngle > 360 - EVALUATION_GRADE_1 || relativeAngle < EVALUATION_GRADE_1) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_6));
        } else if (relativeAngle > 360 - EVALUATION_GRADE_2 || relativeAngle < EVALUATION_GRADE_2) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_5));
        } else if (relativeAngle > 360 - EVALUATION_GRADE_3 || relativeAngle < EVALUATION_GRADE_3) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_4));
        } else if (relativeAngle > 360 - EVALUATION_GRADE_4 || relativeAngle < EVALUATION_GRADE_4) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_3));
        } else if (relativeAngle > 360 - EVALUATION_GRADE_5 || relativeAngle < EVALUATION_GRADE_5) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_2));
        } else {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_1));
        }

        imageView.setRotation((float) relativeAngle + 180);
    }

    public static int getEvaluatedColor(Context context, double rideAngle, double windAngle) {
        double relativeAngle = getRelativeWindAngle(rideAngle, windAngle);

        if (relativeAngle > 360 - EVALUATION_GRADE_1 || relativeAngle < EVALUATION_GRADE_1) {
            return context.getColor(R.color.evaluation_6);
        } else if (relativeAngle > 360 - EVALUATION_GRADE_2 || relativeAngle < EVALUATION_GRADE_2) {
            return context.getColor(R.color.evaluation_5);
        } else if (relativeAngle > 360 - EVALUATION_GRADE_3 || relativeAngle < EVALUATION_GRADE_3) {
            return context.getColor(R.color.evaluation_4);
        } else if (relativeAngle > 360 - EVALUATION_GRADE_4 || relativeAngle < EVALUATION_GRADE_4) {
            return context.getColor(R.color.evaluation_3);
        } else if (relativeAngle > 360 - EVALUATION_GRADE_5 || relativeAngle < EVALUATION_GRADE_5) {
            return context.getColor(R.color.evaluation_2);
        } else {
            return context.getColor(R.color.evaluation_1);
        }
    }

    public static double getRelativeWindAngle(double rideAngle, double windAngle) {
        if (rideAngle < 0) {
            rideAngle = 360 + rideAngle;
        }
        double relativeAngle = windAngle - rideAngle;
        if (relativeAngle < 0) {
            relativeAngle = 360 + relativeAngle;
        }
        return relativeAngle;
    }

    public static double calculateApparentWindSpeed(double rideSpeed, double windSpeed, double relativeWindAngle) {
        return Math.sqrt(Math.pow(rideSpeed + windSpeed * Math.cos(relativeWindAngle), 2) + Math.pow(windSpeed * Math.sin(relativeWindAngle), 2));
    }

    public static double calculateEvaluatedCoef(List<Double> rideAngles, double rideSpeed, double windAngle, double windSpeed) {
        double sum = 0;
        for (Double rideAngle : rideAngles) {
            double relativeWindAngle = getRelativeWindAngle(rideAngle, windAngle);
            double relativeWindAngleRad = Math.toRadians(relativeWindAngle);
            double apparentWindSpeed = calculateApparentWindSpeed(rideSpeed, windSpeed, relativeWindAngleRad);
            double relativeSpeed = apparentWindSpeed / rideSpeed;
            sum += relativeSpeed;
        }
        return sum / rideAngles.size();
    }
}
