package ru.zatsoft.timecalc

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.zatsoft.timecalc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        setSupportActionBar( toolBar)
        title = "Калькулятор времени"
        toolBar.setSubtitle("            версия 1")
        toolBar.setLogo(R.drawable.logo_48)
        binding.tvResult.text = "Результат"
        binding.tvResult.setTextColor(0x77000000)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.resetMenuMain -> {
                binding.etFirstValue.text.clear()
                binding.etSecondValue.text.clear()
                binding.tvResult.text = "Результат"
                binding.tvResult.setTextColor(0x77000000)
                Toast.makeText(applicationContext, "Данные очищены", Toast.LENGTH_LONG).show()
            }
            R.id.exitMenuMain -> {finish()
            Toast.makeText(applicationContext, "Приложение закрыто", Toast.LENGTH_LONG).show()
        }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onButtonClick(view: View){

        val  first = toSeconds(binding.etFirstValue.text.toString())
        val second = toSeconds(binding.etSecondValue.text.toString())

        val res = when (view.id){
            R.id.button -> {val result = Operation(first, second).sum()
                makeResult(result)
            }
            R.id.button2 -> {val result = Operation(first, second).dif()
                makeResult(result)
            }
            else -> " "
        }
        binding.tvResult.text = res
        binding.tvResult.setTextColor(0x778b0000)
    }

    private fun makeResult(result: Int): String {
        val resultString = (result / 60).toString() + "m" + (result % 60).toString() + "s"
        Toast.makeText(applicationContext, "Результат: $resultString", Toast.LENGTH_LONG).show()
        return resultString
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