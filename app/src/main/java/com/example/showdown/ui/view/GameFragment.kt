package com.example.showdown.ui.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.showdown.databinding.FragmentGameBinding
import com.example.showdown.ui.viewmodel.IDGameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment: Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding get() = _binding!!

    private val viewModel: IDGameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getGameData()
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.options.observe(viewLifecycleOwner){ options->
                options?.let {
                    radioBtn1.text = it[0].name
                    radioBtn2.text = it[1].name
                    radioBtn3.text = it[2].name
                    radioBtn4.text = it[3].name }

            }
            radioBtn1.setOnClickListener{
                viewModel.chooseAnswer(radioBtn1.text as String)
                gameSubmitBtn.isEnabled= true
            }
            radioBtn2.setOnClickListener{
                viewModel.chooseAnswer(radioBtn2.text as String)
                gameSubmitBtn.isEnabled= true
            }
            radioBtn3.setOnClickListener{
                viewModel.chooseAnswer(radioBtn3.text as String)
                gameSubmitBtn.isEnabled= true
            }
            radioBtn4.setOnClickListener{
                viewModel.chooseAnswer(radioBtn4.text as String)
                gameSubmitBtn.isEnabled= true
            }
            viewModel.answerImage.observe(viewLifecycleOwner){image ->
                Glide.with(silhouetteIv.context).load(image).into(silhouetteIv)
                val black = floatArrayOf(0F, 0F, 0F)
                silhouetteIv.setColorFilter(Color.HSVToColor(black))
            }

            gameSubmitBtn.setOnClickListener {
                viewModel.answerName.observe(viewLifecycleOwner){answer ->
                    answer?.let {
                        viewModel.checkAnswer(viewModel.choice.value!!,it)
                    }
                }
                val direction = GameFragmentDirections.gameFragmentToAnswerFragmentAction()
                findNavController().navigate(direction)
            }
            gameBackBtn.setOnClickListener {
                viewModel.clearGameData()
                val direction = GameFragmentDirections.gameFragmentToMainFragmentAction()
                findNavController().navigate(direction)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}