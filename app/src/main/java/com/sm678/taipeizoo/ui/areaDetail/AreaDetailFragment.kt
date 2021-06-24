package com.sm678.taipeizoo.ui.areaDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sm678.taipeizoo.ui.MainViewModel
import com.sm678.taipeizoo.R
import com.sm678.taipeizoo.databinding.FragmentAreaDetailBinding

class AreaDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAreaDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_area_detail, container, false)

        val activity = requireNotNull(this.activity)

        val activityViewModel: MainViewModel = ViewModelProvider(requireActivity(),
            MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
        val clickedArea = activityViewModel.clickedItem!!
        (activity as AppCompatActivity).supportActionBar?.title = clickedArea.name

        val viewModel: AreaDetailViewModel = ViewModelProvider(this,
            AreaDetailViewModel.Factory(clickedArea, activity.application))
                .get(AreaDetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.navigateToPlantDetail.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    AreaDetailFragmentDirections.actionAreaDetailFragmentToPlantDetailFragment())

                viewModel.doneNavigateToPlantDetail()
            }
        })

        val adapter = PlantsAdapter(
            viewModel.broccoliViewMap,
            clickListener = PlantsListener{
                activityViewModel.clickedPlant = it
                viewModel.onNavigateToPlantDetail()
            },
            openUrlListener = OpenUrlListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(clickedArea.url)
                startActivity(intent)
            }
        )

        binding.areaList.adapter = adapter
        binding.areaList.addItemDecoration(DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL))

        viewModel.earlyListItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (viewModel.needInit)
                    adapter.addAreaHeaderAndPlaceHolderList(it)
            }
        })

        viewModel.listItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(listOf(viewModel.areaHeader), it)
            }
        })
        val manager = LinearLayoutManager(activity)
        binding.areaList.layoutManager = manager

        return binding.root
    }

}