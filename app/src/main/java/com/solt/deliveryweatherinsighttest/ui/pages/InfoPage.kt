package com.solt.deliveryweatherinsighttest.ui.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.solt.deliveryweatherinsighttest.databinding.InfoPageLayoutBinding
import com.solt.deliveryweatherinsighttest.ui.adapters.LocationHistoryListAdapter
import com.solt.deliveryweatherinsighttest.ui.adapters.LocationHistoryPagingAdapter
import com.solt.deliveryweatherinsighttest.ui.viewmodel.InfoViewModel
import dagger.Binds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class InfoPage:Fragment() {
    //This page will display information about history
    lateinit var binding: InfoPageLayoutBinding
    //This is for when we want to display the last 5 location histories
    lateinit var locationHistoryListAdapter :LocationHistoryListAdapter
    //This is when we want to display all the location history
    lateinit var locationHistoryPagingAdapter: LocationHistoryPagingAdapter
    val infoViewModel:InfoViewModel by viewModels<InfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoPageLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         locationHistoryPagingAdapter = LocationHistoryPagingAdapter(this,{infoViewModel.deleteLocationHistoryEntity(it)}){lat,lon->
             val locationData = infoViewModel.getLocationNameByLatLng(lat,lon)
             locationData?.listOfSearchResults?.getOrNull(0)?.name?:"Unknown"
         }


        //Set up the recycler view
        binding.historyList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationHistoryPagingAdapter
        }
        //We set the checkable text view to use the paging adapter if the button is checked

        //Now we listen for updates from the database
        viewLifecycleOwner.lifecycleScope.launch {
            //For Paging
            launch {
                infoViewModel.getLocationHistory().collectLatest {
                    locationHistoryPagingAdapter.submitData(it)
                }
            }
        }
    }
}