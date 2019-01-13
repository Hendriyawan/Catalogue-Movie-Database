package gdev.id.cataloguemovie.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Utils {
    public static void showNotificationNetworkError(Context context, LinearLayout linearLayout, TextView tvMessage, String message) {
        tvMessage.setText(message);
        if (linearLayout.getVisibility() == View.GONE) linearLayout.setVisibility(View.VISIBLE);
    }
}
