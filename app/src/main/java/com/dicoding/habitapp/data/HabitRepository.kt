package com.dicoding.habitapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dicoding.habitapp.utils.HabitSortType
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HabitRepository(private val habitDao: HabitDao, private val executor: ExecutorService) {

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDERS = true

        @Volatile
        private var instance: HabitRepository? = null

        fun getInstance(context: Context): HabitRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = HabitDatabase.getInstance(context)
                    instance = HabitRepository(
                        database.habitDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return instance as HabitRepository
            }

        }
    }

    //TODO 4 : Use SortUtils.getSortedQuery to create sortable query and build paged list
    fun getHabits(sortType: HabitSortType): LiveData<PagingData<Habit>> {
        throw NotImplementedError("Not yet implemented")
    }

    //TODO 5 : Complete other function inside repository
    fun getHabitById(habitId: Int): LiveData<Habit> {
        throw NotImplementedError("Not yet implemented")
    }

    fun insertHabit(newHabit: Habit) {
        throw NotImplementedError("Not yet implemented")
    }

    fun deleteHabit(habit: Habit) {
        executor.execute {
            habitDao.deleteHabit(habit)
        }
    }

    fun getRandomHabitByPriorityLevel(level: String): LiveData<Habit> {
        throw NotImplementedError("Not yet implemented")
    }
}