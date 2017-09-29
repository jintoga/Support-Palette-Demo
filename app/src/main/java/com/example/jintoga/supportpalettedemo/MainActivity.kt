package com.example.jintoga.supportpalettedemo

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.util.Log
import android.view.WindowManager
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        button1.onClick {
            loadImage("https://pp.userapi.com/c638430/v638430781/2bee8/MbUNRm1y2Ik.jpg")
        }
        button2.onClick {
            loadImage("https://pp.userapi.com/c626616/v626616781/156f7/RC0mX0qa1Z4.jpg")
        }
        button3.onClick {
            loadImage("https://pp.userapi.com/c629224/v629224781/4b2f1/nx1fxGsU6xE.jpg")
        }
    }

    private fun loadImage(avatar: String) {

        val imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(avatar))
                .build()

        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(imageRequest, this)

        dataSource.subscribe(object : BaseBitmapDataSubscriber() {

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                if (dataSource != null) {
                    dataSource.close()
                }
            }

            override fun onNewResultImpl(bitmap: Bitmap?) {
                if (dataSource.isFinished && bitmap != null) {
                    Log.d("Bitmap", "has come")
                    bindData(bitmap, avatar)
                    dataSource.close()
                }
            }

        }, CallerThreadExecutor.getInstance())
    }

    private fun bindData(bitmap: Bitmap?, avatar: String) {
        runOnUiThread {
            val palette = Palette.from(bitmap).generate()

            if (palette.dominantSwatch != null) {
                dominantSwatch.backgroundColor = palette.dominantSwatch!!.rgb
                dominantSwatch.text = "dominantSwatch" +
                        "" + palette.dominantSwatch!!.titleTextColor.toString()
                tintStatusBar(palette.dominantSwatch!!.rgb)
            }

            if (palette.vibrantSwatch != null) {
                vibrantSwatch.backgroundColor = palette.vibrantSwatch!!.rgb
                vibrantSwatch.text = "vibrantSwatch" + palette.vibrantSwatch!!.titleTextColor.toString()
            }

            if (palette.lightVibrantSwatch != null) {
                vibrantLightSwatch.backgroundColor = palette.lightVibrantSwatch!!.rgb
                vibrantLightSwatch.text = "vibrantLightSwatch" + palette.lightVibrantSwatch!!.titleTextColor.toString()
            }

            if (palette.darkVibrantSwatch != null) {
                vibrantDarkSwatch.backgroundColor = palette.darkVibrantSwatch!!.rgb
                vibrantDarkSwatch.text = "vibrantDarkSwatch" + palette.darkVibrantSwatch!!.titleTextColor.toString()
            }

            if (palette.mutedSwatch != null) {
                mutedSwatch.backgroundColor = palette.mutedSwatch!!.rgb
                mutedSwatch.text = "mutedSwatch" + palette.mutedSwatch!!.titleTextColor.toString()
            }

            if (palette.lightMutedSwatch != null) {
                mutedLightSwatch.backgroundColor = palette.lightMutedSwatch!!.rgb
                mutedLightSwatch.text = "mutedLightSwatch" + palette.lightMutedSwatch!!.titleTextColor.toString()
            }
            if (palette.darkMutedSwatch != null) {
                mutedDarkSwatch.backgroundColor = palette.darkMutedSwatch!!.rgb
                mutedDarkSwatch.text = "mutedDarkSwatch" + palette.darkMutedSwatch!!.titleTextColor.toString()
            }

            picture.setImageURI(avatar)
        }
    }

    private fun tintStatusBar(rgb: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = manipulateColor(rgb, 0.8f)
        }
    }

    private fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255))
    }

}
