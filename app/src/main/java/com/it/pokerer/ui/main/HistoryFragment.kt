package com.it.pokerer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.it.pokerer.R
import com.it.pokerer.utils.InjectorUtils

class HistoryFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val factory = InjectorUtils.provideMainViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

        val rootView = inflater.inflate(R.layout.history_fragment, container, false)


        val roundsRecyclerView = rootView.findViewById<RecyclerView>(R.id.history_recycler_view)

        val adapter = RoundHistoryAdapter(requireContext())

        adapter.onLongClickListener = { round ->
            AlertDialog.Builder(requireActivity())
                .setTitle("Are you sure you want to undo this round?")
                .setPositiveButton("Yes") { dialog, _ ->
                    viewModel.undoRound(round)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        roundsRecyclerView.adapter = adapter
        roundsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allRounds.observe(viewLifecycleOwner, Observer{rounds ->
            rounds?.let{
                adapter.setRounds(rounds)
            }
        })

        return rootView
    }
}
