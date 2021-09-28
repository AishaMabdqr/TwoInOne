package com.example.twoinone

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class NumbersGame : AppCompatActivity() {

    lateinit var clMain : ConstraintLayout
    lateinit var userText : EditText
    lateinit var guessBut : Button
    lateinit var itemList : ArrayList<String>
    lateinit var rvMessages : RecyclerView

    private var answer = 0
    private var guesses = 3

    private var newGame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers_game)

        clMain = findViewById(R.id.clMain)

        answer = Random.nextInt(10)
        itemList = ArrayList()

        rvMessages = findViewById(R.id.rvMessages)
        rvMessages.adapter = MessageAdapter(this, itemList)
        rvMessages.layoutManager = LinearLayoutManager(this)

        userText = findViewById(R.id.userText)
        guessBut = findViewById(R.id.Guess)

        guessBut.setOnClickListener{Guessfunc()}

        title = "NumberGame"
    }

    override fun recreate() {
        super.recreate()
        answer = Random.nextInt(10)
        guesses = 3
        itemList.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("answer", answer)
        outState.putInt("guesses", guesses)
        outState.putStringArrayList("messages", itemList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        answer = savedInstanceState.getInt("answer", 0)
        guesses = savedInstanceState.getInt("guesses", 0)
        itemList.addAll(savedInstanceState.getStringArrayList("messages")!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.num_game_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numNewGame -> {
                CustomAlertDialog(this,"Are you sure you want to abandon the current game?")
                return true
            }
            R.id.numOtherGame -> {
                changeScreen(GuessThePhrase())
                return true
            }
            R.id.numMainMenu -> {
                changeScreen(MainActivity())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeScreen(activity: Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    fun Guessfunc(){

        val msg = userText.text.toString()
        if (msg.isNotEmpty()){
            if(guesses>0) {
                if (msg.toInt() == answer) {
                    CustomAlertDialog(this, "You win!\n\nPlay again?")
                } else {
                    guesses--
                    itemList.add("You guessed $msg")
                    itemList.add("You have $guesses guesses left")
                }
                if (guesses == 0) {

                    itemList.add("You lose - The correct answer was $answer")
                    itemList.add("Game Over")
                    CustomAlertDialog(this, "You lose...\nThe correct answer was $answer.\n\nPlay again?")
                }
            }
            userText.text.clear()
            userText.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        }else {
            Snackbar.make(clMain, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }

}