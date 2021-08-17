package dev.itsu.manabaassignments

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent

class AssignmentsAppWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_ITEM_CLICK = "dev.itsu.manabaassignments.ACTION_ITEM_CLICK"
        const val ACTION_CLICK = "dev.itsu.manabaassignments.ACTION_CLICK"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        val mainActivityIntent = Intent(context, MainActivity::class.java)
        when (intent?.action) {
            ACTION_ITEM_CLICK -> {
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(mainActivityIntent)
            }
            ACTION_CLICK -> {
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(mainActivityIntent)
            }
        }
    }

}