package com.example.showdown.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.showdown.R
import com.example.showdown.databinding.FragmentAnswerBinding
import com.example.showdown.ui.viewmodel.IDGameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment : Fragment() {
    private var _binding: FragmentAnswerBinding? = null
    private val binding: FragmentAnswerBinding get() = _binding!!

    private val viewModel: IDGameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnswerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            viewModel.answerImage.observe(viewLifecycleOwner){image->

                Glide.with(correctPokeIv.context).load(image).into(correctPokeIv)
            }

            viewModel.answerName.observe(viewLifecycleOwner){ name ->
                answerHeaderTv.text = getString(R.string.answerTitle, name)
            }

            viewModel.correctOrIncorrectAnswer.observe(viewLifecycleOwner){ userAnswer ->
                userAnswer?.let{
                    if(it){
                        retryBtn.text = getString(R.string.replay)
                        answerTv.text = getString(R.string.rightAnswer)
                    }else{
                        retryBtn.text = getString(R.string.retry)
                            answerTv.text = getString(R.string.wrongAnswer)
                    }
                }

            }

            homeBtn.setOnClickListener {
                viewModel.clearGameData()
                val direction = AnswerFragmentDirections.answerFragmentToMainFragmentAction()
                findNavController().navigate(direction)
            }

            retryBtn.setOnClickListener{
                viewModel.clearGameData()
                val direction = AnswerFragmentDirections.answerFragmentToGameFragmentAction()
                findNavController().navigate(direction)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

