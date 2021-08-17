package dev.itsu.manabaassignments

import android.app.Application
import dev.itsu.manaba.model.Assignment

class ManabaAssignmentsApplication : Application() {

    companion object {
        private lateinit var INSTANCE: ManabaAssignmentsApplication
        var lastUpdatedAt = 0L
        var assignments = listOf<Assignment>()

        fun getInstance() = INSTANCE
        fun getContext() = INSTANCE
    }

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
    }
}