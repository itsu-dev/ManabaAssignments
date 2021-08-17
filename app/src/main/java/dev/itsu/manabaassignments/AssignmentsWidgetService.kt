package dev.itsu.manabaassignments

import android.content.Intent
import android.widget.RemoteViewsService

class AssignmentsWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return AssignmentsWidgetFactory()
    }

}