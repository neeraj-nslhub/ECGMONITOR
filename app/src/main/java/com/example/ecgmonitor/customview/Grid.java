package com.example.ecgmonitor.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Grid extends View {

    private int rowsCount = 68;
    private int columnsCount = 100;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float height = getHeight();
        float width = getWidth();
        for (int i = 0; i < rowsCount; i++) {
            canvas.drawLine(0, height / rowsCount * (i + 1), width, height / rowsCount * (i + 1), paint);
        }
        for (int i = 0; i < columnsCount; i++) {
            canvas.drawLine(width / columnsCount * (i + 1), 0, width / columnsCount * (i + 1), height, paint);
        }
        super.onDraw(canvas);
    }
}