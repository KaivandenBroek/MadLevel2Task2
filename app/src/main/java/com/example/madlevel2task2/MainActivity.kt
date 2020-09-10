package com.example.madlevel2task2

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.*
import com.example.madlevel2task2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

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
        val callbackLeft = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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

        }

        val callbackRight = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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

        }

        // returns the outcome of the question


        return ItemTouchHelper(callbackLeft)
    }
}