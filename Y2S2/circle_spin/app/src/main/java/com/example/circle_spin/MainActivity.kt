import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FrameLayout planetContainer;
    private EditText orbitInput;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planetContainer = findViewById(R.id.planetContainer);
        orbitInput = findViewById(R.id.orbitInput);
        okButton = findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputValue = orbitInput.getText().toString();
                float orbitRadius = Float.parseFloat(inputValue);
                // Call method to animate moon orbit based on orbitRadius
                animateMoonOrbit(orbitRadius);
            }
        });
    }

    private void animateMoonOrbit(float orbitRadius) {
        // Adjust the duration based on the orbit radius for a smoother animation
        long animationDuration = (long) (orbitRadius * 1000);

        // Calculate the final X position of the moon
        float finalX = planetContainer.getWidth() / 2f + orbitRadius;

        // Animate moon's orbit
        ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.moon), "translationX", finalX);
        animator.setDuration(animationDuration);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
}
