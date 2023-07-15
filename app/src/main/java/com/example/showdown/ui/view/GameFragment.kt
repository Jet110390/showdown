package com.example.showdown.ui.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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
            viewModel.mode.observe(viewLifecycleOwner){
                Log.d(TAG, "setting up ${viewModel.mode.value} mode game view ")
                when(it){
                    "Easy"->{
                        easyRadioBtnBox.visibility = View.VISIBLE
                        viewModel.easyModeOptions.observe(viewLifecycleOwner){ options->
                            options?.let {easyOptions ->
                                easyRadioBtn1.text = easyOptions[0].name
                                easyRadioBtn2.text = easyOptions[1].name
                            }
                        }
                    }
                    "Normal"->{
                        normalRadioBtnBox.visibility = View.VISIBLE
                        viewModel.normalModeOptions.observe(viewLifecycleOwner){ options->
                            options?.let {normalOptions ->
                                radioBtn1.text = normalOptions[0].name
                                radioBtn2.text = normalOptions[1].name
                                radioBtn3.text = normalOptions[2].name
                                radioBtn4.text = normalOptions[3].name
                            }
                        }
                    }
                    "Hard"->{
                        hardModeLayout.visibility = View.VISIBLE
                        hardModeEt.doOnTextChanged { text, start, before, count ->
                            gameSubmitBtn.isEnabled = true
                            viewModel.chooseAnswer(text.toString())
                        }
                    }
                }
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
            easyRadioBtn1.setOnClickListener{
                viewModel.chooseAnswer(easyRadioBtn1.text as String)
                gameSubmitBtn.isEnabled= true
            }
            easyRadioBtn2.setOnClickListener{
                viewModel.chooseAnswer(easyRadioBtn2.text as String)
                gameSubmitBtn.isEnabled= true
            }
            viewModel.answerImage.observe(viewLifecycleOwner){image ->
                Glide.with(silhouetteIv.context).load(image).into(silhouetteIv)
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