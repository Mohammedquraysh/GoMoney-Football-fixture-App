package com.presentation.ui.fixtures

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.domain.model.Competition

class CompetitionsSpinnerAdapter(
    private val context: Context
) : BaseAdapter() {

    private var competitions: List<Competition> = emptyList()

    fun updateCompetitions(newCompetitions: List<Competition>) {
        competitions = newCompetitions
        notifyDataSetChanged()
    }

    override fun getCount(): Int = competitions.size

    override fun getItem(position: Int): Competition? = competitions.getOrNull(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = competitions[position].name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = competitions[position].name

        return view
    }
}