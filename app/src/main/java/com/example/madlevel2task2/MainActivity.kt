package com.example.madlevel2task2

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import androidx.recyclerview.widget.*
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val questions = arrayListOf<Question>()
    private val questionsAdapter = QuestionsAdapter(questions)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.rvQuestions.layoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.rvQuestions.adapter = questionsAdapter
        binding.rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        for (i in Question.QUESTIONS.indices) {
            questions.add(
                Question(
                    Question.QUESTIONS[i],
                    Question.QUESTION_AWNSER[i]
                )
            )
        }
        questionsAdapter.notifyDataSetChanged()
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                questions.removeAt(position)
                questionsAdapter.notifyDataSetChanged()
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
                var direction = false;
                direction = dX <= 0
                val position = viewHolder.adapterPosition
                if (questions[position].answer == direction) {
                    Snackbar.make(rvQuestions, "Incorrect", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(rvQuestions, "Correct", Snackbar.LENGTH_SHORT).show()
                }
            }
        }


        // returns the outcome of the question left is incorrect right is correct
        return ItemTouchHelper(callback)
    }
}