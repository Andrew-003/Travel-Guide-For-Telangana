package mini.project.helpers;


import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public class FullScreenUtils {

    // Method to make an activity go full screen
    public static void enableFullScreen(AppCompatActivity activity) {
        if (activity == null) {
            return; // Null check for safety
        }

        // Make the app hide only the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.getWindow().setDecorFitsSystemWindows(false);
            activity.getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        } else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        // Call the custom fullScreen method for devices with a display cutout
        fullScreen(activity);
    }

    // Method to handle full-screen behavior with respect to display cutouts
    public static void fullScreen(AppCompatActivity activity) {
        Window window = activity.getWindow();
        // Hide only the status bar while keeping the navigation bar visible
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Adjust for devices with display cutouts (e.g., notches)
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        window.setAttributes(attributes);
    }
}