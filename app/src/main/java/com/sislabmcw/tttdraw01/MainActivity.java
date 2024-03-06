package com.sislabmcw.tttdraw01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sislabmcw.tttdraw01.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        TView tView = new TView(this);
        setContentView(tView);
    }

    public class TView extends View {

        private Paint paint = new Paint();
        private Path path   = new Path();

        List<Path> paths   = new ArrayList<>();
        List<Paint> paints = new ArrayList<>();

        public TView(Context context) {
            super(context);
            paints = paintList();
            paint.setAntiAlias(true);
            paint.setARGB(255, 255, 255, 0);
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (Path path : paths) {
                canvas.drawPath(path, paint);
            }
            if (path != null) {
                canvas.drawPath(path, paint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x,y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x,y);
                    invalidate();            // 도형 그리는 중에 화면 갱신
                    break;
                case MotionEvent.ACTION_UP:
                    path.close();
                    paths.add(path);
                    path = null;
                    invalidate();            // 도형 그리고 나서 앞에 그린 도형과 함께 화면 갱신
                    Log.d("[DEBUG]-onTouchEvent", "Size of paths = "+ Integer.valueOf(paths.size()));
                    break;
                default:
                    return false;
            }
            invalidate();
            return true;
        }
    }

    public List<Paint> paintList() {
        // Paint array 만들기
        Paint paint = new Paint();
        List<Paint> paints = new ArrayList<>();

        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
        for (int i=0; i<7; i++) {
            int del = i;
            paint.setAntiAlias(true);
            paint.setARGB(255, 255-del, del, del);
            paints.add(paint);
        }
        return paints;
    }
}