package com.example.flppybirdicetask1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Tower(private val context: Context, var x:Float, var height: Int) {
    private val paint = Paint()
    private val width: Int=200
    private val gap: Int= 1500
    var passed = false

    fun draw(canvas: Canvas){
        val topTower = Rect(x.toInt(),0, (x + width).toInt(), height)
        val bottomTower = Rect(x.toInt(), height + gap, (x+ width).toInt(), canvas.height)
        canvas.drawRect(topTower,paint)
        canvas.drawRect(bottomTower, paint)
    }

    fun update(){
        x -=10f
        if(x < -width){
            x = context.resources.displayMetrics.widthPixels.toFloat()
            height = (Math.random()* (context.resources.displayMetrics.heightPixels - gap)).toInt()

        }
    }

    fun getTopRectangle(): Rect{
        return Rect(x.toInt(), 0, (x+width).toInt(),height)
    }

    fun getBottomRectangle(): Rect{
        return Rect(x.toInt(), height+gap,(x+width).toInt(),context.resources.displayMetrics.heightPixels)
    }
}