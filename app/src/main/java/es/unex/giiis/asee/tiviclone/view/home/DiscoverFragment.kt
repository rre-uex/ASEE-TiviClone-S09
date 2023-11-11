package es.unex.giiis.asee.tiviclone.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.asee.tiviclone.databinding.FragmentDiscoverBinding
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.asee.tiviclone.api.APIError
import es.unex.giiis.asee.tiviclone.api.getNetworkService
import es.unex.giiis.asee.tiviclone.data.api.TvShow
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.data.model.User
import es.unex.giiis.asee.tiviclone.data.toShow
import es.unex.giiis.asee.tiviclone.database.TiviCloneDatabase
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverFragment : Fragment() {

    private lateinit var user: User
    private lateinit var db: TiviCloneDatabase

    private val TAG = "DiscoverFragment"

    private var _shows: List<Show> = emptyList()

    private lateinit var listener: OnShowClickListener
    interface OnShowClickListener {
        fun onShowClick(show: Show)
    }

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DiscoverAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate DiscoverFragment")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = TiviCloneDatabase.getInstance(context)!!
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

        lifecycleScope.launch {
            if (_shows.isEmpty()) {
                binding.spinner.visibility = View.VISIBLE
                try {
                    _shows = fetchShows().map(TvShow::toShow)
                    adapter.updateData(_shows)
                } catch (error: APIError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                } finally {
                    binding.spinner.visibility = View.GONE

                }
            }
        }

    }

    private suspend fun fetchShows(): List<TvShow> {
        var apiShows = listOf<TvShow>()
        try {
            apiShows = getNetworkService().getShows(1).tvShows
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return apiShows
    }

    private fun setUpRecyclerView() {
        adapter = DiscoverAdapter(
            shows = _shows,
            onClick = {
                listener.onShowClick(it)
            },
            onLongClick = {
                setFavorite(it)
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

    private fun setFavorite(show: Show){
        lifecycleScope.launch {
            show.isFavorite = true
            db.showDao().insertAndRelate(show,user.userId!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}