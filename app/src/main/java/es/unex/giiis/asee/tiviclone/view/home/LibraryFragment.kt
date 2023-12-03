package es.unex.giiis.asee.tiviclone.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import es.unex.giiis.asee.tiviclone.databinding.FragmentLibraryBinding
/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {

    private lateinit var adapter: LibraryAdapter
    private val viewModel: LibraryViewModel by viewModels { LibraryViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        subscribeUi(adapter)
    }

    private fun setUpRecyclerView() {
        adapter = LibraryAdapter(shows = emptyList(), onClick = {
            homeViewModel.onShowClick(it)
        },
            onLongClick = {
                viewModel.setNoFavorite(it)
                Toast.makeText(context, "${it.title} removed from library", Toast.LENGTH_SHORT).show()
            },
            context = context
        )
        with(binding) {
            rvLibShowList.layoutManager = GridLayoutManager(context, 3)
            rvLibShowList.adapter = adapter
        }
        android.util.Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    private fun subscribeUi(adapter: LibraryAdapter) {
        viewModel.showsInLibrary.observe(viewLifecycleOwner) { user ->
            adapter.updateData(user.shows)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

}