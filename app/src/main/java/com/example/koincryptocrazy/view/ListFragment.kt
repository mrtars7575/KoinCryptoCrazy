package com.example.koincryptocrazy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koincryptocrazy.R
import com.example.koincryptocrazy.databinding.FragmentListBinding
import com.example.koincryptocrazy.model.CryptoList
import com.example.koincryptocrazy.model.CryptoListItem
import com.example.koincryptocrazy.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() , RecyclerViewAdapter.Listener {


    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    var cryptoAdapter = RecyclerViewAdapter(CryptoList(),this)

    private val viewModel : CryptoViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        viewModel.getDataFromAPI()

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer { cryptos ->
            cryptos?.let {
                binding.recyclerView.visibility = View.VISIBLE
                cryptoAdapter = RecyclerViewAdapter(cryptos.data ?: CryptoList(),this@ListFragment)
                binding.recyclerView.adapter = cryptoAdapter
            }

        })

        viewModel.cryptoError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it.data == true) {
                    binding.cryptoErrorText.visibility = View.VISIBLE
                } else {
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it.data == true) {
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                } else {
                    binding.cryptoProgressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoListItem) {
        Toast.makeText(context,"${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }


}