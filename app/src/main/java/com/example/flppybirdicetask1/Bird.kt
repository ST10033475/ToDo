package com.example.flppybirdicetask1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Bird(context: Context) {
    private var birdBitMap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.phoenix_pixel_art_19x0p0sly95iicb4)
    var x: Float = 100f
    var y: Float = 100f
    var velocity: Float = 0f

    fun draw(canvas: Canvas, paint: Paint){
        canvas.drawBitmap(birdBitMap,x,y,paint)
    }

    fun update(){

        y += velocity
        velocity += 1f
    }

    fun flap(){

        velocity= -20f
    }

    fun getRectangle(): Rect {
        return Rect(x.toInt(), y.toInt(), (x + birdBitMap.width).toInt(), (y + birdBitMap.height).toInt())
    }
}