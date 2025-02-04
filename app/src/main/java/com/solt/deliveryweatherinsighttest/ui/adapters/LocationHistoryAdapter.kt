package com.solt.deliveryweatherinsighttest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import com.solt.deliveryweatherinsighttest.databinding.HistoryItemLayoutBinding
import java.text.DateFormat
import java.time.format.DateTimeFormatter

val locationHistoryDiffUtil = object: DiffUtil.ItemCallback<LocationHistoryEntity>() {
    override fun areItemsTheSame(
        oldItem: LocationHistoryEntity,
        newItem: LocationHistoryEntity
    ): Boolean {
        return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude

    }

    override fun areContentsTheSame(
        oldItem: LocationHistoryEntity,
        newItem: LocationHistoryEntity
    ): Boolean {
        return oldItem == newItem

}}
  class LocationHistoryPagingAdapter: PagingDataAdapter<LocationHistoryEntity,LocationHistoryItemViewHolder>(
      locationHistoryDiffUtil) {
      override fun onBindViewHolder(holder: LocationHistoryItemViewHolder, position: Int) {
          holder.bind(getItem(position))
      }

      override fun onCreateViewHolder(
          parent: ViewGroup,
          viewType: Int
      ): LocationHistoryItemViewHolder {
         val binding = HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
          return LocationHistoryItemViewHolder(binding)
      }


  }

class LocationHistoryListAdapter:ListAdapter<LocationHistoryEntity,LocationHistoryItemViewHolder>(
    locationHistoryDiffUtil){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationHistoryItemViewHolder {
        val binding = HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LocationHistoryItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LocationHistoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
  class LocationHistoryItemViewHolder(val binding:HistoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
      fun bind(locationHistory:LocationHistoryEntity?) {
          if (locationHistory != null) {
              binding.apply {
                  //Set the Latitude and Longitude
                  latLng.text =
                      "Latitude : ${locationHistory.latitude} ,Longitude: ${locationHistory.longitude}"
                  //Date
                  //We format it first
                  val dateTimeFormatter =
                      DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG)
                  val formattedDate = dateTimeFormatter.format(locationHistory.timeStamp)
                  time.text = formattedDate

              }
          }
      }

  }