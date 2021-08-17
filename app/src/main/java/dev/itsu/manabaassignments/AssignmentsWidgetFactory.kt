package dev.itsu.manabaassignments

import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.TextView
import dev.itsu.manaba.Manaba
import dev.itsu.manabaassignments.ManabaAssignmentsApplication.Companion.assignments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class AssignmentsWidgetFactory : RemoteViewsService.RemoteViewsFactory {

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        if (assignments.isEmpty() || System.currentTimeMillis() - ManabaAssignmentsApplication.lastUpdatedAt >= 1800000) {
            val token = Binder.clearCallingIdentity()
            reloadAssignments()
            Binder.restoreCallingIdentity(token)
        }
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return assignments.size
    }

    override fun getViewAt(position: Int): RemoteViews? {
        if (assignments.size < 0) return null

        val assignment = assignments[position]
        val remoteViews = RemoteViews(ManabaAssignmentsApplication.getContext().packageName, R.layout.assignment_cell)
        remoteViews.setTextViewText(R.id.assignment_title, assignment.title)
        remoteViews.setTextViewText(R.id.assignment_deadline, SimpleDateFormat("yyyy/MM/dd HH:mm").format(assignment.expiredAt) + " / ${assignment.course}")
        remoteViews.setInt(R.id.assignment_cell, "setBackgroundResource", when {
            assignment.expiredAt - System.currentTimeMillis() < 0 -> R.drawable.assignment_cell_none
            assignment.expiredAt - System.currentTimeMillis() < 86400000 -> R.drawable.assignment_cell_red
            assignment.expiredAt - System.currentTimeMillis() < 86400000 * 3 -> R.drawable.assignment_cell_yellow
            assignment.expiredAt - System.currentTimeMillis() < 86400000 * 7 -> R.drawable.assignment_cell_green
            else -> R.drawable.assignment_cell_none
        })

        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    private fun reloadAssignments() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Default) {
                // TODO Read credentials from external file(s)
                Manaba.login("", "")
                ManabaAssignmentsApplication.lastUpdatedAt = System.currentTimeMillis()
                Manaba.getAssignments()
            }.let {
                assignments = it
            }
        }
    }

}