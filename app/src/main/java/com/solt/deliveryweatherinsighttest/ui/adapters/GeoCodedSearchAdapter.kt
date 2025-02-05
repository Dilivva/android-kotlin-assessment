package com.solt.deliveryweatherinsighttest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.GeoCodedLocation
import com.solt.deliveryweatherinsighttest.databinding.MapSearchItemLayoutBinding

val geoCodedLocationDiffUtil = object: DiffUtil.ItemCallback<GeoCodedLocation>(){
    override fun areItemsTheSame(oldItem: GeoCodedLocation, newItem: GeoCodedLocation): Boolean {
       return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude
    }

    override fun areContentsTheSame(oldItem: GeoCodedLocation, newItem: GeoCodedLocation): Boolean {
        return oldItem == newItem
    }

}
class GeoCodedSearchAdapter: ListAdapter<GeoCodedLocation,GeocodedSearchViewHolder>(
    geoCodedLocationDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeocodedSearchViewHolder {
        val binding = MapSearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GeocodedSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GeocodedSearchViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}
class GeocodedSearchViewHolder(val binding:MapSearchItemLayoutBinding):ViewHolder(binding.root){
    fun bind(location:GeoCodedLocation){
        binding.apply {
            nameOfLocation.text = location.name?:"Unknown"
            latitude.text = (location.latitude?:0.0).toString()
            longitude.text = (location.longitude?:0.0).toString()
        }
    }
}