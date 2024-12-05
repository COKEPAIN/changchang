package kr.ac.changchang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class OutlineTextView extends AppCompatTextView {
    private Paint strokePaint;
    private int textStrokeColor;
    private float textStrokeWidth;

    public OutlineTextView(Context context) {
        super(context);
        init(null);
    }

    public OutlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OutlineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OutlineTextView);
            textStrokeColor = a.getColor(R.styleable.OutlineTextView_textStrokeColor, 0xFF000000);
            textStrokeWidth = a.getFloat(R.styleable.OutlineTextView_textStrokeWidth, 0.0f);
            a.recycle();
        }
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (strokeWidth > 0) {
//            strokePaint.setStrokeWidth(strokeWidth);
//            strokePaint.setColor(strokeColor);
//            strokePaint.setStyle(Paint.Style.STROKE);
//
//            // Draw stroke
//            super.onDraw(canvas);
//        }
//
//        // Draw text
//        super.onDraw(canvas);
//    }

    private void setOutlineStrokeWidth(float width) {
        getPaint().setStrokeWidth(2 * width + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int textColor = getCurrentTextColor();

        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setStrokeWidth(textStrokeWidth);
        getPaint().setColor(textStrokeColor);
        super.onDraw(canvas);

        // 내부 채우기
        getPaint().setStyle(Paint.Style.FILL);
        getPaint().setColor(textColor);
        super.onDraw(canvas);
    }
}