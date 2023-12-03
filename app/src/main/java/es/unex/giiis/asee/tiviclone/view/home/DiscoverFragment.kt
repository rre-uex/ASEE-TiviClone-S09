package es.unex.giiis.asee.tiviclone.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.asee.tiviclone.databinding.FragmentDiscoverBinding
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.tiviclone.TiviCloneApplication
import es.unex.giiis.asee.tiviclone.api.APIError
import es.unex.giiis.asee.tiviclone.api.getNetworkService
import es.unex.giiis.asee.tiviclone.data.Repository
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.data.model.User
import es.unex.giiis.asee.tiviclone.database.TiviCloneDatabase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverFragment : Fragment() {

    private lateinit var user: User
    private val viewModel: DiscoverViewModel by viewModels { DiscoverViewModel.Factory }

    private val TAG = "DiscoverFragment"

    private lateinit var listener: OnShowClickListener
    interface OnShowClickListener {
        fun onShowClick(show: Show)
    }

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DiscoverAdapter

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)

        if (context is OnShowClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnShowClickListener")
        }
    }

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

        val userProvider = activity as UserProvider
        user = userProvider.getUser()

        viewModel.user = user

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
                listener.onShowClick(it)
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