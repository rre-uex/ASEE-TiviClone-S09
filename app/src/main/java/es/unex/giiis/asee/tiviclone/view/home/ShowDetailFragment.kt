package es.unex.giiis.asee.tiviclone.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import es.unex.giiis.asee.tiviclone.databinding.FragmentShowDetailBinding

private const val TAG = "ShowDetailFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowDetailFragment : Fragment() {

    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ShowDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val show = args.show
        binding.tvShowTitle.text = show.title
        binding.tvDescription.text = show.description
        binding.tvYear.text = show.year
        binding.swFav.isChecked = show.isFavorite
        binding.coverImg.setImageResource(show.image)
        binding.bannerImg.setImageResource(show.banner)
        Log.d(TAG, "Showing ${show.title} details")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment ShowDetailFragment.
         */
        @JvmStatic
        fun newInstance() =
            ShowDetailFragment()
    }
}