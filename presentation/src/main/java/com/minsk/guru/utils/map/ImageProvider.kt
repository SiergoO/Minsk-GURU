package com.minsk.guru.utils.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.WindowManager
import com.yandex.runtime.image.ImageProvider
import kotlin.math.abs
import kotlin.math.sqrt

class TextImageProvider(private val context: Context, private val text: String) : ImageProvider() {

    override fun getId(): String =
        "text_$text"

    @Suppress("DEPRECATION")
    override fun getImage(): Bitmap {
        val metrics = DisplayMetrics()
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getMetrics(metrics)
        val textPaint = Paint()
        textPaint.textSize = FONT_SIZE * metrics.density
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.style = Paint.Style.FILL
        textPaint.isAntiAlias = true
        val widthF = textPaint.measureText(text)
        val textMetrics = textPaint.fontMetrics
        val heightF = abs(textMetrics.bottom) + abs(textMetrics.top)
        val textRadius = sqrt((widthF * widthF + heightF * heightF).toDouble()).toFloat() / 2
        val internalRadius: Float = textRadius + MARGIN_SIZE * metrics.density
        val externalRadius: Float = internalRadius + STROKE_SIZE * metrics.density
        val width = (2 * externalRadius + 0.5).toInt()
        val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val backgroundPaint = Paint()
        backgroundPaint.isAntiAlias = true
        backgroundPaint.color = Color.DKGRAY
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), externalRadius, backgroundPaint)
        backgroundPaint.color = Color.WHITE
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), internalRadius, backgroundPaint)
        canvas.drawText(
            text,
            (width / 2).toFloat(),
            width / 2 - (textMetrics.ascent + textMetrics.descent) / 2,
            textPaint
        )
        return bitmap
    }

    companion object {
        private const val FONT_SIZE = 15f
        private const val MARGIN_SIZE = 3f
        private const val STROKE_SIZE = 3f
    }
}