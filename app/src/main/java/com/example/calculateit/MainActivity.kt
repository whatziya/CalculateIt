package com.example.calculateit

import MainViewModel
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.calculateit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setupUI()
    }

    private fun setupUI() = with(binding) {
        // Observe LiveData
        viewModel.equationText.observe(this@MainActivity) { textResult.text = it }

        // Set up button clicks using an improved map function
        mapOf(
            btnClear to "C", btnPercent to "%", btnDivide to "/", btnMultiply to "*",
            btnMinus to "-", btnPlus to "+", btnEqual to "=", btnDot to ".", btn0 to "0",
            btn1 to "1", btn2 to "2", btn3 to "3", btn4 to "4", btn5 to "5",
            btn6 to "6", btn7 to "7", btn8 to "8", btn9 to "9"
        ).forEach { (button, value) ->
            button.setOnClickListener { viewModel.addDigit(value) }
        }
    }
}
