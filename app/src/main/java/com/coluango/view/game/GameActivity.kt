package com.coluango.view.game

import android.os.Bundle
import com.coluango.R
import com.coluango.base.BaseActivity
import com.coluango.common.Constant
import com.coluango.databinding.ActivityGameBinding
import com.coluango.view.game.chess_board.ChessBoardFragment

class GameActivity : BaseActivity() {

    private lateinit var binding: ActivityGameBinding

    private var numberOfPlayers = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numberOfPlayers = intent.getIntExtra(Constant.KEY_NUMBER_OF_PLAYERS, 1)
        initView()
        handleListener()
    }

    override fun initView() {
        newGame()
    }

    override fun handleListener() {
        binding.tvNewGame.setOnClickListener {
            newGame()
        }
    }

    private fun newGame() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layoutChessBoard, ChessBoardFragment(numberOfPlayers)).commit()
    }

    override fun observeViewModel() {

    }
}