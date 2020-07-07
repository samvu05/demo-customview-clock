package com.t3h.democustomview

import android.content.Context
import android.graphics.*
import android.icu.text.SimpleDateFormat
import android.icu.text.TimeZoneFormat
import android.os.Build
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import java.util.*

public class CustomViewKotlin : View {
//    var isRunningTime1:Boolean = true // cach khoi tao 1
    private var isRunningTime = true // cach khoi tao 2 , Khai bao bang "True" thi he thong tu biet no la Boolean
    private var colorSmile = 0;
    private var colorDate = 0;
    private var colorTime = 0;
    private var sizeSmile = 0;
    private var titleText = ""
    constructor(context: Context,attrs: AttributeSet)
            :super(context,attrs){
        extractAtt(attrs)

    }
    constructor(context: Context,attrs: AttributeSet?,defStyleAtrr:Int)
            :super(context,attrs,defStyleAtrr){

    }
    private fun extractAtt(attrs:AttributeSet) {
        //lay tat ca cac thuoc tinh tu xml
        val customXML = context.obtainStyledAttributes(attrs,R.styleable.CustomViewKotlin)

        //lay tung gia tri cac thuoc tinh trong xml da khai bao
        colorSmile = customXML.getColor(R.styleable.CustomViewKotlin_colorSmile,Color.parseColor("#D50000"))
        colorTime = customXML.getColor(R.styleable.CustomViewKotlin_colorTime,Color.parseColor("#D50000"))
        colorDate = customXML.getColor(R.styleable.CustomViewKotlin_colorDate,Color.parseColor("#D50000"))
        titleText = customXML.getString(R.styleable.CustomViewKotlin_titleText).toString()
        sizeSmile = customXML.getDimension(R.styleable.CustomViewKotlin_sizeSmile,30.0f).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var p = Paint()
        p.style = Paint.Style.STROKE
        p.isAntiAlias = true; //Khu rang cua
        p.color = Color.parseColor("#9CCC65") //Set Color
        p.strokeWidth = 15.0f //Set do rong duong vien
        canvas.drawCircle(width.toFloat()/2,height.toFloat()/2,width.toFloat()/2-7.5f,p)

        p.textSize = sizeSmile.toFloat()
        p.color = colorSmile
        val bound = Rect()
        p.getTextBounds(titleText,0,titleText.length,bound)
        canvas.drawText(titleText,(width - bound.width())/2.0f,110.0f,p)

        //draw Date
        val currentDate = Date() // Lay thoi gian hien tai cua dien thoai
        val formatTime = SimpleDateFormat("HH:mm:ss")
        val textTime= formatTime.format(currentDate)
        p.textSize = 80.0f
        p.color = colorTime
        canvas.drawText(textTime,110.0f,270.0f,p)

        val formatDate = SimpleDateFormat("dd/MM/yyyy")
        p.textSize = 45.0f
        p.color = colorDate
        canvas.drawText(formatDate.format(currentDate),140.0f,350.0f,p)

        //Chuyen anh trong drawable thanh Bitmap

        val bm = BitmapFactory.decodeResource(resources,R.drawable.clock_icon)
        val retRes = Rect(0,0,bm.width,bm.height)
        val retDes = Rect(200,380,200+100,380+100)
        canvas.drawBitmap(bm,retRes,retDes,null)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        createTheard();
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isRunningTime = false;
    }

    private fun createTheard(){
        var run = Runnable{
            // noi dung
            //Trong kotlin, do run la interface ma interface chi co 1 phuong thuc duy nhat
            //Nen noi dung cua no se nam o ham run
            //Khi goi interface nay thi chac chan se goi vao phuong thuc do vi interface chi co 1 phuong thuc
            while (isRunningTime){
                postInvalidate() // Tuong ung voi Repaint
                SystemClock.sleep(1000)
            }

        }
        var th = Thread(run)
        th.start()
    }

}