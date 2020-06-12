package com.mzm.sample.iconclassifier

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mzm.sample.iconclassifier.ml.Icons50
import kotlinx.android.synthetic.main.activity_main.*
import org.tensorflow.lite.support.image.TensorImage

class MainActivity : AppCompatActivity() {

    private val iconList: ArrayList<Icon> = ArrayList()
    var iconAdapter: IconAdapter? = null

    private val icons = arrayOf(R.drawable.alarm_clock,
            R.drawable.cloud_with_rain,
            R.drawable.island,
            R.drawable.lion_face,
            R.drawable.mailbox,
            R.drawable.mountain,
            R.drawable.taxi,
            R.drawable.tulip)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create data source for the icon grid
        addIconData()
        iconAdapter = iconList.let { IconAdapter(this, it) }
        iconGrid.adapter = iconAdapter
    }

    /* Add icon data to icon list */
    private fun addIconData() {
        var iconBitmap: Bitmap
        var prediction: String

        for (iconId in icons) {
            iconBitmap = BitmapFactory.decodeResource(resources, iconId)
            prediction = predict(iconBitmap)
            iconList?.add(Icon(iconId, prediction))
        }
    }

    private fun predict(bitmap: Bitmap): String {

        val model = Icons50.newInstance(this)

        // Creates inputs for reference.
        val image = TensorImage.fromBitmap(bitmap)

        // Runs model inference and gets result.
        val outputs = model.process(image)
        val predictedClass = outputs.probabilityAsCategoryList.maxBy { it.score }
        val prediction = predictedClass!!.label + " " + predictedClass.score

        // Releases model resources if no longer used.
        model.close()

        return prediction
    }

    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
    }
}

data class Icon(var iconId: Int, var prediction: String = " ")
