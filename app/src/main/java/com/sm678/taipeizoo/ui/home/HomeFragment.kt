package com.sm678.taipeizoo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sm678.taipeizoo.ui.MainViewModel
import com.sm678.taipeizoo.R
import com.sm678.taipeizoo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val activity = requireNotNull(this.activity)

        val viewModel: HomeViewModel = ViewModelProvider(this,
            HomeViewModel.Factory(activity.application)).get(HomeViewModel::class.java)
        val activityViewModel: MainViewModel = ViewModelProvider(requireActivity(),
            MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val direction = HomeFragmentDirections.actionHomeFragmentToAreaDetailFragment()
                this.findNavController().apply {
                    if (currentDestination?.getAction(direction.actionId) != null)
                        navigate(direction)
                }
//                this.findNavController().navigate(
//                    HomeFragmentDirections.actionHomeFragmentToAreaDetailFragment())
                viewModel.doneNavigateToDetail()
            }
        })



        val adapter = HomeAreaAdapter(
            clickListener = HomeAreaListener {
                activityViewModel.clickedItem = it
                viewModel.onNavigateToDetail()
        })
        binding.areaList.adapter = adapter
        binding.areaList.addItemDecoration(DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL))


        viewModel.listItem.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        val manager = LinearLayoutManager(activity)
        binding.areaList.layoutManager = manager

        return binding.root
    }

    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
    }
}