package com.example.kudago.ui.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.kudago.App
import com.example.kudago.R
import com.example.kudago.databinding.NewsFragmentBinding

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = NewsFragmentBinding.inflate(inflater)
        val application = requireNotNull(activity).application as App
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        val viewModelFactory = MainViewModelFactory(application)
        // Giving the binding access to the OverviewViewModel3
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        binding.viewModel = viewModel
        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.newsList.adapter = NewsAdapter(NewsAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner) {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController()
                    .navigate(NewsFragmentDirections.actionNewsFragmentToDetailsFragment())
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Updates the filter in the [OverviewViewModel] when the menu items are selected from the
     * overflow menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        this.findNavController()
            .navigate(NewsFragmentDirections.actionNewsFragmentToSearchFragment())
        return true
    }


}