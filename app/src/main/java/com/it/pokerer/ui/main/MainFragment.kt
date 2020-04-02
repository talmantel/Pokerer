package com.it.pokerer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.it.pokerer.R
import com.it.pokerer.Repository
import com.it.pokerer.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.gil = Repository.Companion.Player.GIL
        binding.tal = Repository.Companion.Player.TAL
        binding.shay = Repository.Companion.Player.SHAY

        binding.gilWonButton.setOnClickListener {
            viewModel.roundPlayed(getBets(), Repository.Companion.Player.GIL)
            resetBets()
        }
        binding.talWonButton.setOnClickListener {
            viewModel.roundPlayed(getBets(), Repository.Companion.Player.TAL)
            resetBets()
        }
        binding.shayWonButton.setOnClickListener {
            viewModel.roundPlayed(getBets(), Repository.Companion.Player.SHAY)
            resetBets()
        }

        binding.gilBet.setOnFocusChangeListener { view, b ->
            if(b && binding.gilBet.text.isEmpty())
                (view.focusSearch(View.FOCUS_RIGHT) as EditText).run{
                    if(text.isNotEmpty())
                        requestFocus()
                }
        }

        binding.talBet.setOnFocusChangeListener { view, b ->
            if(b && binding.talBet.text.isEmpty())
                (view.focusSearch(View.FOCUS_RIGHT) as EditText).run{
                    if(text.isNotEmpty())
                        requestFocus()
                }
        }

        binding.shayBet.setOnFocusChangeListener { view, b ->
            if(b && binding.shayBet.text.isEmpty())
                (view.focusSearch(View.FOCUS_RIGHT) as EditText).run{
                    if(text.isNotEmpty())
                        requestFocus()
                }
        }

        resources.getInteger(R.integer.default_bet).toString().let{ defaultBet ->
            binding.gilBet.setText(defaultBet)
            binding.talBet.setText(defaultBet)
            binding.shayBet.setText(defaultBet)
            binding.gilTitle.setOnLongClickListener { binding.gilBet.setText(defaultBet); true }
            binding.talTitle.setOnLongClickListener { binding.talBet.setText(defaultBet); true }
            binding.shayTitle.setOnLongClickListener { binding.shayBet.setText(defaultBet); true }
        }

        return binding.root
    }

    private fun getBets(): Map<Repository.Companion.Player, Int>{
        return mutableMapOf<Repository.Companion.Player, Int>().apply {
            if (binding.gilBet.text.isNotEmpty() && Integer.valueOf(binding.gilBet.text.toString()) != 0)
                put(Repository.Companion.Player.GIL, Integer.valueOf(binding.gilBet.text.toString()))
            if (binding.talBet.text.isNotEmpty() && Integer.valueOf(binding.talBet.text.toString()) != 0)
                put(Repository.Companion.Player.TAL, Integer.valueOf(binding.talBet.text.toString()))
            if (binding.shayBet.text.isNotEmpty() && Integer.valueOf(binding.shayBet.text.toString()) != 0)
                put(Repository.Companion.Player.SHAY, Integer.valueOf(binding.shayBet.text.toString()))
        }
    }

    private fun resetBets(){
        resources.getInteger(R.integer.default_bet).toString().let { defaultBet ->
            if (binding.gilBet.text.isNotEmpty())
                binding.gilBet.setText(defaultBet)
            if (binding.talBet.text.isNotEmpty())
                binding.talBet.setText(defaultBet)
            if (binding.shayBet.text.isNotEmpty())
                binding.shayBet.setText(defaultBet)
        }

        binding.gilBet.requestFocus()
        binding.gilBet.selectAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
