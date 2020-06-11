package com.example.puzzlegame;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GridView extends android.widget.GridView {

        private GestureDetector gDetector;
        private boolean mFlingConfirmed = false;
        private float mTouchX;
        private float mTouchY;

        private static final int SWIPE_MIN_DISTANCE = 100;
        private static final int SWIPE_MAX_OFF_PATH = 100;
        private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        private static String currentActivity;

        public GridView(Context context) {
            super(context);
            init(context);
        }

        public GridView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public GridView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
        public GridView(Context context, AttributeSet attrs, int defStyleAttr,
                                     int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init(context);
        }

        private void init(final Context context) {
            gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent event) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    final int position = GridView.this.pointToPosition
                            (Math.round(e1.getX()), Math.round(e1.getY()));

                    if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                        if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH
                                || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                            return false;
                        }
                        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {

                            if(currentActivity.equals("easy")){
                                EasyPuzzleActivity.moveTiles(context, Constants.UP, position);
                            }
                            else if(currentActivity.equals("medium")){
                                MediumPuzzleActivity.moveTiles(context, Constants.UP, position);
                            }
                            else if(currentActivity.equals("hard")){
                                HardPuzzleActivity.moveTiles(context, Constants.UP, position);
                            }
                            else if(currentActivity.equals("veryHard")){
                                ExpertPuzzleActivity.moveTiles(context, Constants.UP, position);
                            }

                        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                            if(currentActivity.equals("easy")){
                                EasyPuzzleActivity.moveTiles(context, Constants.DOWN, position);
                            }
                            else if(currentActivity.equals("medium")){
                                MediumPuzzleActivity.moveTiles(context, Constants.DOWN, position);
                            }
                            else if(currentActivity.equals("hard")){
                                HardPuzzleActivity.moveTiles(context, Constants.DOWN, position);
                            }
                            else if(currentActivity.equals("veryHard")){
                                ExpertPuzzleActivity.moveTiles(context, Constants.DOWN, position);
                            }
                        }

                    } else {
                        if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                            return false;
                        }
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                            if(currentActivity.equals("easy")){
                                EasyPuzzleActivity.moveTiles(context, Constants.LEFT, position);
                            }
                            else if(currentActivity.equals("medium")){
                                MediumPuzzleActivity.moveTiles(context, Constants.LEFT, position);
                            }
                            else if(currentActivity.equals("hard")){
                                HardPuzzleActivity.moveTiles(context, Constants.LEFT, position);
                            }
                            else if(currentActivity.equals("veryHard")){
                                ExpertPuzzleActivity.moveTiles(context, Constants.LEFT, position);
                            }

                        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                            if(currentActivity.equals("easy")){
                                EasyPuzzleActivity.moveTiles(context, Constants.RIGHT, position);
                            }
                            else if(currentActivity.equals("medium")){
                                MediumPuzzleActivity.moveTiles(context, Constants.RIGHT, position);
                            }
                            else if(currentActivity.equals("hard")){
                                HardPuzzleActivity.moveTiles(context, Constants.RIGHT, position);
                            }
                            else if(currentActivity.equals("veryHard")){
                                ExpertPuzzleActivity.moveTiles(context, Constants.RIGHT, position);
                            }
                        }
                    }
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            int action = ev.getActionMasked();
            gDetector.onTouchEvent(ev);

            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                mFlingConfirmed = false;
            } else if (action == MotionEvent.ACTION_DOWN) {
                mTouchX = ev.getX();
                mTouchY = ev.getY();
            } else {

                if (mFlingConfirmed) {
                    return true;
                }

                float dX = (Math.abs(ev.getX() - mTouchX));
                float dY = (Math.abs(ev.getY() - mTouchY));
                if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                    mFlingConfirmed = true;
                    return true;
                }
            }

            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return gDetector.onTouchEvent(ev);
        }

        public static void setActivity(String activity){
            currentActivity = activity;
        }

}
