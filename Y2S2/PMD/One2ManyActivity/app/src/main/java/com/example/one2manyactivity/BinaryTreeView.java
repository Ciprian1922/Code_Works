package com.example.one2manyactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BinaryTreeView extends View {
    private BinaryTree binaryTree;

    public BinaryTreeView(Context context) {
        super(context);
    }

    public BinaryTreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBinaryTree(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
        invalidate(); // Redraw the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (binaryTree != null && binaryTree.getRoot() != null) {
            drawNode(canvas, binaryTree.getRoot(), getWidth() / 2, 100, getWidth() / 4);
        }
    }

    private void drawNode(Canvas canvas, BinaryTree.Node node, float x, float y, float offset) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(40);

        if (node.left != null) {
            Paint linePaint = new Paint();
            linePaint.setColor(Color.MAGENTA); // Set line color to pink
            linePaint.setStrokeWidth(5);
            canvas.drawLine(x, y, x - offset, y + 150, linePaint);
            drawNode(canvas, node.left, x - offset, y + 150, offset / 2);
        }

        if (node.right != null) {
            Paint linePaint = new Paint();
            linePaint.setColor(Color.MAGENTA); // Set line color to pink
            linePaint.setStrokeWidth(5);
            canvas.drawLine(x, y, x + offset, y + 150, linePaint);
            drawNode(canvas, node.right, x + offset, y + 150, offset / 2);
        }

        canvas.drawCircle(x, y, 50, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(node.value), x, y + 15, paint);
    }
}

