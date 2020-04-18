package com.it.pokerer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.it.pokerer.R
import com.it.pokerer.data.Player
import com.it.pokerer.databinding.RoundFragmentBinding
import com.it.pokerer.utils.InjectorUtils

class RoundFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private var _binding: RoundFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val factory = InjectorUtils.provideMainViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

        _binding = RoundFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.gil = Player.GIL
        binding.tal = Player.TAL
        binding.shay = Player.SHAY

        binding.gilWonButton.setOnClickListener {
            viewModel.roundPlayed(getBets(), Player.GIL)
            resetBets()
        }
        binding.talWonButton.setOnClickListener {
            viewModel.roundPlayed(getBets(), Player.TAL)
            resetBets()
        }
        binding.shayWonButton.setOnClickListener {
            viewModel.roundPlayed(getBets(), Player.SHAY)
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

    private fun getBets(): Map<Player, Int>{
        return mutableMapOf<Player, Int>().apply {
            if (binding.gilBet.text.isNotEmpty() && Integer.valueOf(binding.gilBet.text.toString()) != 0)
                put(Player.GIL, Integer.valueOf(binding.gilBet.text.toString()))
            if (binding.talBet.text.isNotEmpty() && Integer.valueOf(binding.talBet.text.toString()) != 0)
                put(Player.TAL, Integer.valueOf(binding.talBet.text.toString()))
            if (binding.shayBet.text.isNotEmpty() && Integer.valueOf(binding.shayBet.text.toString()) != 0)
                put(Player.SHAY, Integer.valueOf(binding.shayBet.text.toString()))
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
