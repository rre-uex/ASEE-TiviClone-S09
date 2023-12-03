package es.unex.giiis.asee.tiviclone.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import es.unex.giiis.asee.tiviclone.databinding.FragmentDiscoverBinding
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverFragment : Fragment() {

    private val viewModel: DiscoverViewModel by viewModels { DiscoverViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val TAG = "DiscoverFragment"

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DiscoverAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        // show the spinner when [spinner] is true
        viewModel.spinner.observe(viewLifecycleOwner) { show ->
            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Show a Toast whenever the [toast] is updated a non-null value
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        subscribeUi(adapter)

    }

    private fun subscribeUi(adapter: DiscoverAdapter) {
        viewModel.shows.observe(viewLifecycleOwner) { shows ->
            adapter.updateData(shows)
        }
    }

    private fun setUpRecyclerView() {
        adapter = DiscoverAdapter(
            shows = emptyList(),
            onClick = {
                homeViewModel.onShowClick(it)
            },
            onLongClick = {
                viewModel.setFavorite(it)
                Toast.makeText(context, "Added to library: "+it.title, Toast.LENGTH_SHORT).show()
            },
            context = this.context
        )
        with(binding) {
            rvShowList.layoutManager = LinearLayoutManager(context)
            rvShowList.adapter = adapter
        }
        Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

}