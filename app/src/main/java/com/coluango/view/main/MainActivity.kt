package com.coluango.view.main

import android.content.Intent
import android.os.Bundle
import com.coluango.base.BaseActivity
import com.coluango.common.Constant
import com.coluango.databinding.ActivityMainBinding
import com.coluango.view.game.GameActivity

/**
 * User: Quang Phúc
 * Date: 17-Nov-22
 * Time: 12:51 AM
 */
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleListener()
    }

    override fun initView() {

    }

    override fun handleListener() {
        binding.tv1Player.setOnClickListener {
            newGame(1)
        }

        binding.tv2Players.setOnClickListener {
            newGame(2)
        }
    }

    private fun newGame(numberOfPlayers: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(Constant.KEY_NUMBER_OF_PLAYERS, numberOfPlayers)
        startActivity(intent)
    }

    override fun observeViewModel() {

    }
}