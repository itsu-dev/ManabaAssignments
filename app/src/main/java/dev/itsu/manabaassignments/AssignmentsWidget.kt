package dev.itsu.manabaassignments

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import dev.itsu.manaba.Manaba
import kotlinx.coroutines.*

class AssignmentsWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.Default) {
                for (appWidgetId in appWidgetIds) {
                    val remoteViews = RemoteViews(context.packageName, R.layout.assignments_widget)

                    // TODO Read credentials from external file(s)
                    Manaba.login("", "")
                    ManabaAssignmentsApplication.lastUpdatedAt = System.currentTimeMillis()
                    ManabaAssignmentsApplication.assignments = Manaba.getAssignments()

                    remoteViews.setRemoteAdapter(
                        R.id.assignments_list,
                        Intent(context, AssignmentsWidgetService::class.java)
                    )

                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.assignments_list)
                    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
                }
            }
        }
    }

    override fun onEnabled(context: Context) {

    }

    override fun onDisabled(context: Context) {

    }
}