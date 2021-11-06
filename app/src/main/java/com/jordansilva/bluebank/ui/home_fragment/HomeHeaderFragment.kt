package com.jordansilva.bluebank.ui.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.button.MaterialButton
import com.jordansilva.bluebank.R
import com.jordansilva.bluebank.ui.home.HomeUiState
import com.jordansilva.bluebank.ui.home.HomeViewModel

class HomeHeaderFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels { HomeViewModel.provideFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) { loadData(it) }
    }

    private fun loadData(state: HomeUiState) {
        view?.let { view ->
            val visibilityButton: MaterialButton = view.findViewById(R.id.visibilityButton)
            visibilityButton.contentDescription = if (state.isPrivateMode) getString(R.string.balance_show) else getString(R.string.balance_hide)
            visibilityButton.setIconResource(if (state.isPrivateMode) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on)
            visibilityButton.setOnClickListener {
                viewModel.changeMode(!state.isPrivateMode)
            }

            view.findViewById<Button>(R.id.profileButton).setOnClickListener { }
            view.findViewById<Button>(R.id.helperButton).setOnClickListener { }
            view.findViewById<Button>(R.id.contactButton).setOnClickListener { }
            view.findViewById<TextView>(R.id.accountName).text = getString(R.string.hello_username, state.accountName)
        }
    }

    companion object {
        fun newInstance() = HomeHeaderFragment()
    }
}