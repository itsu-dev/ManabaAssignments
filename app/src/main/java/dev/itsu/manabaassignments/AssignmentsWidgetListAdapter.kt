package dev.itsu.manabaassignments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import dev.itsu.manabaassignments.model.AssignmentsWidgetListItem

class AssignmentsWidgetListAdapter(context: Context, private val resource: Int, private val items: List<AssignmentsWidgetListItem>) : ArrayAdapter<AssignmentsWidgetListItem>(context, resource, items) {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(resource, null)
        val item = items.get(position)

        view.findViewById<TextView>(R.id.assignment_title).text = item.title
        view.findViewById<TextView>(R.id.assignment_deadline).text = item.deadline
        view.findViewById<LinearLayout>(R.id.assignment_cell).setOnClickListener {
            Toast.makeText(context, item.url, Toast.LENGTH_SHORT)
        }

        return view
    }

}