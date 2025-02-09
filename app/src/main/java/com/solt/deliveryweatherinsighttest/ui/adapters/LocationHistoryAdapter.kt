package com.solt.deliveryweatherinsighttest.ui.adapters

import android.os.Build
import android.os.Bundle

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.solt.deliveryweatherinsighttest.R
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import com.solt.deliveryweatherinsighttest.databinding.HistoryItemLayoutBinding
import com.solt.deliveryweatherinsighttest.ui.pages.LOCATION_HISTORY_ITEM
import kotlinx.coroutines.launch

import org.maplibre.android.geometry.LatLng
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
  class LocationHistoryPagingAdapter( val fragment: Fragment,val onDelete: (LocationHistoryEntity) -> Unit,val getLocationNameByLatLng:suspend (Double,Double)->String): PagingDataAdapter<LocationHistoryEntity,LocationHistoryItemViewHolder>(
      locationHistoryDiffUtil) {
      override fun onBindViewHolder(holder: LocationHistoryItemViewHolder, position: Int) {
          holder.bind(getItem(position))
      }

      override fun onCreateViewHolder(
          parent: ViewGroup,
          viewType: Int
      ): LocationHistoryItemViewHolder {
         val binding = HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
          return LocationHistoryItemViewHolder(fragment,binding,onDelete,getLocationNameByLatLng)
      }


  }

class LocationHistoryListAdapter( val fragment: Fragment,val onDelete: (LocationHistoryEntity) -> Unit,val getLocationNameByLatLng:suspend (Double,Double)->String):ListAdapter<LocationHistoryEntity,LocationHistoryItemViewHolder>(
    locationHistoryDiffUtil){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationHistoryItemViewHolder {
        val binding = HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LocationHistoryItemViewHolder(fragment,binding, onDelete,getLocationNameByLatLng)
    }


    override fun onBindViewHolder(holder: LocationHistoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
  class LocationHistoryItemViewHolder(val fragment:Fragment,val binding:HistoryItemLayoutBinding,val onDelete:(LocationHistoryEntity)->Unit,val getLocationNameByLatLng:suspend (Double,Double)->String): RecyclerView.ViewHolder(binding.root){
      fun bind(locationHistory:LocationHistoryEntity?) {
          if (locationHistory != null) {
              binding.apply {
                  // We try and get the name from the lat and lng
                  fragment.viewLifecycleOwner.lifecycleScope.launch {
                      val locationName = getLocationNameByLatLng(locationHistory.latitude,locationHistory.longitude)
                      nameOfLocation.text = locationName
                  }
                  //Set the Latitude and Longitude
                  latitude.text = locationHistory.latitude.toString()
                  longitude.text = locationHistory.longitude.toString()
                  //Date
                  //We format it first
                  val dateTimeFormatter =
                      DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                  //Try using android date format
                  val formattedDate = dateTimeFormatter.format(locationHistory.timeStamp)
                  time.text = formattedDate
                  //Make the delete layout visible if not visible
                  root.setOnLongClickListener {
                      //Just delete it
                      onDelete(locationHistory)
                      true
                  }
                  //When we click on the the x button or the layout  we delete the location history


                  //Now in the on click if the delete layout is visible make it invisible else go the map page
                  root.setOnClickListener {

                              //Navigate to the map page with the longitude and latitude
                              val locationParcel = LatLng(locationHistory.longitude,locationHistory.latitude)
                              val bundle = Bundle().apply {
                                  putParcelable(LOCATION_HISTORY_ITEM, locationParcel)
                              }
                              root.findNavController().navigate(R.id.action_infoPage_to_mainPage,
                                 bundle )
                          }

                      }
                  }

              }
          }
