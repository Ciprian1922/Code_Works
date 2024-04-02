import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

@SuppressLint("ViewConstructor")
public class MovingObjectView extends View {

    private Paint paint;
    private float posX, posY;
    private float speedX, speedY;
    private int radius = 50; // Radius of the object
    private boolean visible = true;

    private float initialX, initialY;

    public MovingObjectView(Context context, float startX, float startY, float speedX, float speedY) {
        super(context);
        this.posX = startX;
        this.posY = startY;
        this.initialX = startX; // Store initial X position
        this.initialY = startY; // Store initial Y position
        this.speedX = speedX;
        this.speedY = speedY;
        paint = new Paint();
        paint.setColor(Color.RED); // Set the color of the moving object
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (visible) {
            canvas.drawCircle(posX, posY, radius, paint); // Draw a circle representing the moving object
        }
    }

    public void updatePosition(int screenWidth, int screenHeight, MovingObjectView[] objects) {
        posX += speedX;
        posY += speedY;

        // Check collision with screen boundaries
        if (posX - radius <= 0 || posX + radius >= screenWidth) {
            speedX = -speedX; // Reverse horizontal direction
            // Adjust position to prevent sticking to the screen boundary
            if (posX - radius <= 0) {
                posX = radius;
            } else {
                posX = screenWidth - radius;
            }
        }
        if (posY - radius <= 0 || posY + radius >= screenHeight) {
            speedY = -speedY; // Reverse vertical direction
            // Adjust position to prevent sticking to the screen boundary
            if (posY - radius <= 0) {
                posY = radius;
            } else {
                posY = screenHeight - radius;
            }
        }

        // Check collision with other objects
        for (MovingObjectView object : objects) {
            if (object != this && object.isVisible()) {
                float dx = posX - object.posX;
                float dy = posY - object.posY;
                float distanceSquared = dx * dx + dy * dy;
                float radiusSquared = (radius + object.radius) * (radius + object.radius);

                if (distanceSquared < radiusSquared) {
                    // Collision detected, reverse direction for both objects
                    float tempSpeedX = speedX;
                    float tempSpeedY = speedY;
                    speedX = object.speedX;
                    speedY = object.speedY;
                    object.speedX = tempSpeedX;
                    object.speedY = tempSpeedY;
                    break; // No need to check further collisions
                }
            }
        }

        invalidate(); // Redraw the view to update the positions
    }

    public boolean isVisible() {
        return visible;
    }

    public void resetPosition() {
        posX = initialX; // Set posX to its initial value
        posY = initialY; // Set posY to its initial value
        invalidate(); // Redraw the view to update the positions
    }
}
