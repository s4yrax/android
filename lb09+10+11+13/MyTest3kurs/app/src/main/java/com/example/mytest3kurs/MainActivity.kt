package com.example.mytest3kurs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.mytest3kurs.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var userImageView: ImageView
    private lateinit var textViews: Array<TextView>
    private lateinit var button2: Button
    private lateinit var edName: EditText
    private lateinit var edPrice: EditText
    private lateinit var tvList: TextView
    private var threadsPaused: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button2 = findViewById(R.id.button2)
        edName = findViewById(R.id.edName)
        edPrice = findViewById(R.id.edPrice)
        tvList = findViewById(R.id.tvList1)

        button2.setOnClickListener {
            val text = "Name: ${edName.text} Price: ${edPrice.text}"
            tvList.text = text
        }

        userImageView = findViewById(R.id.profileimag)
        Picasso.get().load("https://w.forfun.com/fetch/c4/c493aac67877288476b0fc52d55f55cf.jpeg").
        placeholder(R.drawable.trun).error(R.drawable.trun).into(userImageView)

        textViews = arrayOf(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
            findViewById(R.id.textView6),
            findViewById(R.id.textView7),
            findViewById(R.id.textView8),
            findViewById(R.id.textView9),
            findViewById(R.id.textView10),
            findViewById(R.id.textView11),
            findViewById(R.id.textView12),
            findViewById(R.id.textView13),
            findViewById(R.id.textView14),
            findViewById(R.id.textView15),
            findViewById(R.id.textView16),
            findViewById(R.id.textView17),
            findViewById(R.id.textView18),
            findViewById(R.id.textView19)
        )

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (threadsPaused) {
                threadsPaused = false
            } else {
                threadsPaused = true
                return@setOnClickListener
            }

            textViews.forEach { textView ->
                updateTextViewRandomly(textView)
            }
        }
    }

    private fun updateTextViewRandomly(textView: TextView) {
        val originalString = "100100100001001011001001"
        val handler = Handler()
        val random = Random()

        val runnable = Runnable {
            while (true) {
                if (!threadsPaused) {
                    val stringBuilder = StringBuilder()
                    for (i in 0 until originalString.length) {
                        stringBuilder.append(random.nextInt(2))
                    }
                    val randomString = stringBuilder.toString()

                    handler.post {
                        textView.text = randomString
                    }

                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                } else {
                    try {
                        Thread.sleep(1)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        val thread = Thread(runnable)
        thread.start()
    }
}