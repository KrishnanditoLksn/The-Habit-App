package com.dicoding.habitapp.ui.countdown

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.utils.HABIT
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {
    private lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"
        workManager = WorkManager.getInstance(this)
        val habit = getParcelableExtra(intent, HABIT, Habit::class.java)

        if (habit != null) {
            findViewById<TextView>(R.id.tv_count_down_title).text = habit.title
            val viewModel = ViewModelProvider(this)[CountDownViewModel::class.java]

            //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
            viewModel.setInitialTime(habit.minutesFocus)

            viewModel.currentTimeString.observe(this) {
                findViewById<TextView>(R.id.tv_count_down).text = it
            }


            //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.
            viewModel.eventCountDownFinish.observe(this) {
                updateButtonState(!it)
            }

            findViewById<Button>(R.id.btn_start).setOnClickListener {
                viewModel.startTimer()
                updateButtonState(true)
                val workData = workDataOf(
                    HABIT_ID to habit.id,
                    HABIT_TITLE to habit.title
                )
                val oneTimeWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                    .setInputData(workData)
                    .setInitialDelay(habit.minutesFocus, TimeUnit.MILLISECONDS)
                    .build()

                workManager.enqueue(oneTimeWorkRequest)

            }

            findViewById<Button>(R.id.btn_stop).setOnClickListener {
                workManager.cancelAllWork()
                updateButtonState(false)
                viewModel.resetTimer()
            }
        }

    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }
}