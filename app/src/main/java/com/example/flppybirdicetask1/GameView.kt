package com.example.flppybirdicetask1

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.SystemClock
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback {
private val bird: Bird
private val towers = mutableListOf<Tower>()
    private val paint = Paint()
    private val thread = GameThread()
    private val background: Bitmap
    private var score = 0
    private var startTime : Long =0


    init{
        holder.addCallback(this)
        bird = Bird(context)
        towers.add(Tower(context, 800f, 400))
        towers.add(Tower(context, 1600f,300 ))
        thread = GameThread(holder, this)
        background = BitmapFactory.decodeResource(resources, R.drawable.screenshot_2024_07_23_094521)
    }

    override fun surfaceCreated(holder : SurfaceHolder){
        thread.setRunning(true)
        thread.start()
        startTime = SystemClock.elapsedRealtime()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder){

        var retry = true
        thread.setRunning(false)
        while(retry){
            try{
                thread.join()
                retry =false
            }
            catch(e: InterruptedException){
                e.printStackTrace()
            }

        }
    }

    override fun draw(canvas: Canvas){
        super.draw(canvas)
        if(canvas !=null){
            canvas.drawBitmap(background,0f,0f,paint)
            bird.draw(canvas, paint)
            for(tower in towers){
                tower.draw(canvas)
            }
            drawScore(canvas)
            drawTimer(canvas)
        }
    }

    fun update(){
        bird.update()
        for (tower in towers){
            tower.update()
            if(Rect.intersects(bird.getRectangle(), tower.getTopRectangle()) || Rect.intersects(bird.getRectangle(), tower.getBottomRectangle())){
                thread.setRunning(false)
                navigateToHomeScreen()
            }
        }
        for(tower in towers){
            if(tower.x + 200 < bird.x && !tower.passed){
                score++
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN){
                bird.flap()
            }
        return true;
    }

    private fun drawScore(canvas: Canvas){
        paint.textSize=100f
        canvas.drawText("Score: $score", 100f,100f, paint)
    }

    private fun drawTimer(canvas: Canvas){
        val elapsedSeconds = (SystemClock.elapsedRealtime()-startTime)/1000
        paint.textSize=100f
        canvas.drawText("Time: $elapsedSeconds", 100f,100f, paint)
    }

    private fun navigateToHomeScreen(){
        val intent = Intent(context, MainActivity:: class.java).apply{

        }
        context.startActivity(intent)
        (context as GameScreen).finish()
    }
}