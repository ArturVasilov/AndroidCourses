package ru.guar7387.kotlinsample

import android.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ru.guar7387.kotlinsample.data.Earthquake
import ru.guar7387.kotlinsample.data.EARTHQUAKES_KEY

public class EarthquakesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val list = inflater.inflate(R.layout.earthquakes_fragment,
                container, false) as RecyclerView

        val manager = LinearLayoutManager(getActivity().getApplicationContext())
        manager.setOrientation(LinearLayoutManager.VERTICAL)
        list.setLayoutManager(manager)

        val adapter = EarthquakesAdapter(getArguments().
                getParcelableArrayList<Earthquake>(EARTHQUAKES_KEY).filter { it.magnitude >= 5 })
        list.setAdapter(adapter)

        return list
    }
}



