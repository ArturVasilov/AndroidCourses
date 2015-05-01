package ru.guar7387.kotlinsample

import ru.guar7387.kotlinsample.data.Earthquake
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date

public class EarthquakesAdapter(private val earthquakes: List<Earthquake>) :
        RecyclerView.Adapter<EarthquakesAdapter.Holder>() {

    public inner class Holder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        val mPlace: TextView
        val mMagnitude: TextView
        val mDateTime: TextView

        {
            mPlace = itemView.findViewById(R.id.place) as TextView
            mMagnitude = itemView.findViewById(R.id.magnitude) as TextView
            mDateTime = itemView.findViewById(R.id.date) as TextView
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        return Holder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.earthquake_item, viewGroup, false))
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {
        val earthquake = earthquakes.get(i)
        holder.mPlace.setText("Location: " + earthquake.location)
        holder.mMagnitude.setText("Magnitude: " + earthquake.magnitude)
        holder.mDateTime.setText(SimpleDateFormat("dd.mm.yyyy hh:mm",
                Locale.getDefault()).format(Date(earthquake.dateTime)))
    }

    override fun getItemCount(): Int {
        return earthquakes.size()
    }
}



