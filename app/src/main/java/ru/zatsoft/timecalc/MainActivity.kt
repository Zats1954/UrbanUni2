package ru.zatsoft.timecalc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.zatsoft.timecalc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onButtonClick(view: View){

        val  first = toSeconds(binding.etFirstValue.text.toString())
        val second = toSeconds(binding.etSecondValue.text.toString())

        val res = when (view.id){
            R.id.button -> Operation(first, second).sum()
            R.id.button2 -> Operation(first, second).dif()
            else -> 0
        }
        val result = (res/60).toString() + "m" + (res % 60).toString() +"s"
        binding.tvResult.text = result
    }

    private fun toSeconds(minSec: String): Int {
        val patternM = Regex("\\d+m")
        val patternS = Regex("\\d+s")
        val minutes = (patternM.find(minSec)?.value ?: "0m").let {
            it.substringBefore('m').toInt()
        }
        val seconds = (patternS.find(minSec)?.value ?: "0s").let {
            it.substringBefore('s').toInt()
        }
        return minutes * 60 + seconds
    }
}

class Operation(private val first:Int, private val second:Int){
    fun sum() = first + second
    fun dif() = first - second
}