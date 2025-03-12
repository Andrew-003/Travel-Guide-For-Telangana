package mini.project.travelguide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import mini.project.travelguide.R;
import mini.project.helpers.FullScreenUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       FullScreenUtils.enableFullScreen(SplashActivity.this);

        final MotionLayout motionLayout = findViewById(R.id.motionLayout);

        // Set a listener to detect when the transition ends
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {}

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {}

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                postDelayed(() -> {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }, 250); // Small delay to ensure smooth transition
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {}
        });

        // Start the transition after a delay
        postDelayed(() -> motionLayout.transitionToEnd(), 200); // Adjust delay as needed
    }

    /**
     * Replaces deprecated Handler for delayed tasks with a modern alternative.
     *
     * @param task  Runnable task to execute after delay.
     * @param delay Delay in milliseconds.
     */
    private void postDelayed(Runnable task, int delay) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(task, delay);
        }
    }
}