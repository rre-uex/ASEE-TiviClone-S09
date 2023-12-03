package es.unex.giiis.asee.tiviclone.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import es.unex.giiis.asee.tiviclone.R
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.databinding.FragmentShowDetailBinding

private const val TAG = "ShowDetailFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowDetailFragment : Fragment() {

    private val viewModel: ShowDetailViewModel by viewModels { ShowDetailViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

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

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }
        viewModel.show = show

        // Show a Toast whenever the [toast] is updated a non-null value
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.showDetail.observe(viewLifecycleOwner) { show ->
            show?.let{ showBinding(show) }
        }
    }

    private fun showBinding(show: Show) {
        binding.swFav.isChecked = show.isFavorite
        binding.tvShowTitle.text = show.title
        binding.tvDescription.text = show.description
        binding.tvYear.text = show.year
        binding.swFav.isChecked = show.isFavorite

        Glide.with(this)
            .load(show.imagePath)
            .placeholder(R.drawable.placeholder)
            .into(binding.coverImg)

        Glide.with(this)
            .load(show.bannerPath)
            .placeholder(R.drawable.placeholder)
            .into(binding.bannerImg)

        binding.swFav.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                viewModel.setFavorite(show)
            else
                viewModel.setNoFavorite(show)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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