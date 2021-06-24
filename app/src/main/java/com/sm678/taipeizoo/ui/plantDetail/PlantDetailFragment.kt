package com.sm678.taipeizoo.ui.plantDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sm678.taipeizoo.ui.MainViewModel
import com.sm678.taipeizoo.R
import com.sm678.taipeizoo.databinding.FragmentPlantDetailBinding

class PlantDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPlantDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_plant_detail, container, false)

        val activity = requireNotNull(this.activity)

        val activityViewModel: MainViewModel = ViewModelProvider(requireActivity(),
            MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
        val clickedPlant = activityViewModel.clickedPlant!!

        (activity as AppCompatActivity).supportActionBar?.title = clickedPlant.name

        binding.plant = clickedPlant
        binding.setLifecycleOwner(this)

        return binding.root
    }

}