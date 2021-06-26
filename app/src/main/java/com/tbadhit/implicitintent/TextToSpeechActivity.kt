package com.tbadhit.implicitintent

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tbadhit.implicitintent.databinding.ActivityTextToSpeechBinding
import java.util.*

// Bikin layout (Copas aja) ->
class TextToSpeechActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextToSpeechBinding
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextToSpeechBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1.
        binding.btnSpeak.setOnClickListener {
            textToSpeech = TextToSpeech(this@TextToSpeechActivity, TextToSpeech.OnInitListener {
                if (it == TextToSpeech.SUCCESS) {

                    val kodeBahasa = textToSpeech.setLanguage(Locale("id", "ID"))
                    if (kodeBahasa == TextToSpeech.LANG_MISSING_DATA || kodeBahasa == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(
                            this@TextToSpeechActivity,
                            "bahasa tidak didukung",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val text = binding.edtTextToSpeech.text.toString()
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                    }
                } else if (it == TextToSpeech.ERROR) {
                    Toast.makeText(this@TextToSpeechActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
        //---------------
        // Setelah membuat TTS, sekarang bikin new activity "AudioManagerActivity" ->
    }
}