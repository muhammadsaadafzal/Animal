package com.example.animal

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast

class CustomGestureListener : GestureDetector.SimpleOnGestureListener() {
    
    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100
    
    private var activity: Context? = null
    
    fun setActivity(activity: Context) {
        this.activity = activity
    }
    
    fun getActivity(): Context? {
        return this.activity
    }
    
    override fun onDown(e: MotionEvent): Boolean {
        return true
    }
    
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val diffX = e2.x - e1!!.x
        val diffY = e2.y - e1!!.y
        
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    //right swipe
                    onSwipeRight()
                } else {
                    //left swipe
                    onLeftSwipe()
                }
            }
        } else {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    //swipe down
                    onSwipeBottom()
                } else {
                    // swipe up
                    onSwipeTop()
                }
            }
        }
        return true
    }
    
    private fun onSwipeBottom() {
        Toast.makeText(getActivity(), "Bottom Swipe", Toast.LENGTH_LONG).show()
    }
    
    private fun onSwipeTop() {
        Toast.makeText(getActivity(), "Top Swipe", Toast.LENGTH_LONG).show()
    }
    
    private fun onLeftSwipe() {
        Toast.makeText(getActivity(), "Left Swipe", Toast.LENGTH_LONG).show()
    }
    
    private fun onSwipeRight() {
        Toast.makeText(getActivity(), "Right Swipe", Toast.LENGTH_LONG).show()
    }
    
    
}