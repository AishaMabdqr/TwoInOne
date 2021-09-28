package com.example.twoinone

import android.app.Activity
import android.content.ComponentCallbacks2
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    lateinit var clMain : ConstraintLayout
    lateinit var myButton: Button
    lateinit var myButton2 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clMain = findViewById(R.id.clMain)

         myButton = findViewById(R.id.game1)
        myButton.setOnClickListener{ startGame(NumbersGame()) }

         myButton2 = findViewById(R.id.game2)
        myButton2.setOnClickListener{ startGame(GuessThePhrase()) }

        title = "Main Activity"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.game1 -> {
                startGame(NumbersGame())
                return true
            }
            R.id.game2 -> {
                startGame(GuessThePhrase())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startGame(activity : Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}