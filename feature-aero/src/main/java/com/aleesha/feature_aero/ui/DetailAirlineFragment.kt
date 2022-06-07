package com.aleesha.feature_aero.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aleesha.core.extension.visibleOrGone
import com.aleesha.feature_aero.R
import com.aleesha.feature_aero.databinding.FragmentDetailBinding
import com.aleesha.feature_aero.ui.AirlinesListFragment.Companion.AIRLINE_LOGO
import com.aleesha.feature_aero.ui.AirlinesListFragment.Companion.AIRLINE_NAME
import com.aleesha.feature_aero.ui.AirlinesListFragment.Companion.AIRLINE_PHONE
import com.aleesha.feature_aero.ui.AirlinesListFragment.Companion.AIRLINE_WEBSITE
import com.aleesha.feature_aero.viewmodel.AirlineViewModel
import com.aleesha.lib.image.loader.abstraction.ImageLoader
import com.aleesha.lib.image.loader.abstraction.ImageScaleType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailAirlineFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: AirlineViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var website: String
    private lateinit var phone: String
    private lateinit var name: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        setDataToViews()
        setClickListeners()

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
    }

    private fun setToolbar() {
        val appCompatActivity = activity as? AppCompatActivity
        appCompatActivity?.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        arguments?.getString(AIRLINE_NAME)?.let { name ->
            binding.toolbar.title = name
        }

        (activity as AppCompatActivity).supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setClickListeners() {
        binding.itemWebsite.parent.setOnClickListener {
            actionOpenWebsite(website)
        }
        binding.itemPhone.parent.setOnClickListener {
            actionPhone(phone)
        }
    }

    private fun setDataToViews() {
        arguments?.getString(AIRLINE_NAME)?.let { name ->
            binding.itemTitle.parent.visibleOrGone(name.isNotBlank())
            binding.itemTitle.textViewTitle.text = name
            this.name = name
            arguments?.getString(AIRLINE_LOGO)?.let { imageUrl ->
                imageLoader.load(
                    progress = null,
                    imageView = binding.itemTitle.imageViewIcon,
                    imageUrl = imageUrl,
                    scalingType = ImageScaleType.CENTER_INSIDE,
                    placeHolder = androidx.appcompat.R.color.abc_color_highlight_material,
                    errorDrawable = R.drawable.ic_placeholder_airline
                )
            }
        }

        arguments?.getString(AIRLINE_WEBSITE)?.let { website ->
            this.website = website
            binding.itemWebsite.parent.visibleOrGone(website.isNotBlank())
            binding.itemWebsite.imageViewIcon.setImageResource(R.drawable.ic_web)
            binding.itemWebsite.textViewTitle.text = website
        }

        arguments?.getString(AIRLINE_PHONE)?.let { phone ->
            this.phone = phone
            binding.itemPhone.parent.visibleOrGone(phone.isNotBlank())
            binding.itemPhone.textViewTitle.text = phone
            binding.itemPhone.imageViewIcon.setImageResource(R.drawable.ic_phone)
        }
    }

    private fun actionPhone(phone: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phone")
        startActivity(dialIntent)
    }

    private fun actionOpenWebsite(website: String) {
        var websiteUrl = website

        if (!websiteUrl.startsWith("http://") && !websiteUrl.startsWith("https://"))
            websiteUrl = "http://$websiteUrl"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(websiteUrl)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.markFavorite -> {

                if (item.title == getString(R.string.mark_favorite)) {
                    viewModel.markFavoriteAirline(name)
                } else {
                    viewModel.removeFavoriteAirline(name)
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(AIRLINE_NAME_REMOVED_FAVORITE, name)
                }
                requireActivity().invalidateOptionsMenu()
                true
            }
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.markFavorite)

        val isMarkedFavorite = viewModel.isFavoriteAirline(name)
        if (isMarkedFavorite) {
            item.title = getString(R.string.remove_favorite)
        } else {
            item.title = getString(R.string.mark_favorite)
        }
    }

    companion object {
        const val AIRLINE_NAME_REMOVED_FAVORITE = "AIRLINE_NAME_REMOVED_FAVORITE"
    }
}
