package com.coluango.view.game.chess_board

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.coluango.R
import com.coluango.base.BaseFragment
import com.coluango.common.hide
import com.coluango.common.show
import com.coluango.common.viewBinding
import com.coluango.databinding.FragmentChessBoardBinding
import com.coluango.databinding.ItemNodeBinding

class ChessBoardFragment : BaseFragment(R.layout.fragment_chess_board) {

    private val binding by viewBinding(FragmentChessBoardBinding::bind)
    private val viewModel: ChessBoardViewModel by viewModels()

    private val nodeLayouts = ArrayList<ArrayList<ItemNodeBinding>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleListener()
        observeViewModel()

        viewModel.drawBoard()
    }

    override fun initView() {
        initNodeLayouts()
    }

    private fun initNodeLayouts() {
        nodeLayouts.add(ArrayList())

        nodeLayouts.add(ArrayList())
        nodeLayouts.last().add(binding.node00)
        nodeLayouts.last().add(binding.node11)
        nodeLayouts.last().add(binding.node12)
        nodeLayouts.last().add(binding.node13)
        nodeLayouts.last().add(binding.node14)
        nodeLayouts.last().add(binding.node00)

        nodeLayouts.add(ArrayList())
        nodeLayouts.last().add(binding.node00)
        nodeLayouts.last().add(binding.node21)
        nodeLayouts.last().add(binding.node22)
        nodeLayouts.last().add(binding.node23)
        nodeLayouts.last().add(binding.node24)
        nodeLayouts.last().add(binding.node00)

        nodeLayouts.add(ArrayList())
        nodeLayouts.last().add(binding.node00)
        nodeLayouts.last().add(binding.node31)
        nodeLayouts.last().add(binding.node32)
        nodeLayouts.last().add(binding.node33)
        nodeLayouts.last().add(binding.node34)
        nodeLayouts.last().add(binding.node00)

        nodeLayouts.add(ArrayList())
        nodeLayouts.last().add(binding.node00)
        nodeLayouts.last().add(binding.node41)
        nodeLayouts.last().add(binding.node42)
        nodeLayouts.last().add(binding.node43)
        nodeLayouts.last().add(binding.node44)
        nodeLayouts.last().add(binding.node00)

        nodeLayouts.add(ArrayList())
    }

    override fun handleListener() {
        for (i in 1..4) {
            for (j in 1..4) {
                nodeLayouts[i][j].imgViewAlly.setOnClickListener {
                    viewModel.selectNode(i, j)
                }
                nodeLayouts[i][j].imgViewEnemy.setOnClickListener {
                    viewModel.selectNode(i, j)
                }
                nodeLayouts[i][j].imgViewFrom.setOnClickListener {
                    unhighlightAllMoves()
                }
                nodeLayouts[i][j].imgViewTo.setOnClickListener {
                    unhighlightAllMoves()
                    viewModel.moveTo(i, j)
                }
            }
        }
    }

    override fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it.status) {
                ChessBoardState.Status.DRAW_CHESS_BOARD -> {
                    val nodes = it.data as Array<Array<Int>>
                    drawChessBoard(nodes)
                }

                ChessBoardState.Status.HIGHLIGHT_SELECTED_NODE -> {
                    val positionItem = it.data as PositionItem
                    unhighlightAllMoves()
                    nodeLayouts[positionItem.row][positionItem.column].imgViewFrom.show()
                }

                ChessBoardState.Status.CAN_MOVE_TO -> {
                    val positionItems = it.data as ArrayList<PositionItem>
                    highlightCanMoveTo(positionItems)
                }

                ChessBoardState.Status.HIDE_NODE -> {
                    val positionItem = it.data as PositionItem
                    hideNode(nodeLayouts[positionItem.row][positionItem.column])
                }

                ChessBoardState.Status.SHOW_ALLY -> {
                    val positionItem = it.data as PositionItem
                    showAlly(nodeLayouts[positionItem.row][positionItem.column])
                }

                ChessBoardState.Status.SHOW_ENEMY -> {
                    val positionItem = it.data as PositionItem
                    showEnemy(nodeLayouts[positionItem.row][positionItem.column])
                }
            }
        }
    }

    private fun drawChessBoard(nodes: Array<Array<Int>>) {
        for (i in 1..4) {
            for (j in 1..4) {
                if (nodes[i][j] == -1 || nodes[i][j] == 0) {
                    hideNode(nodeLayouts[i][j])
                } else if (nodes[i][j] == 1) {
                    showAlly(nodeLayouts[i][j])
                } else if (nodes[i][j] == 2) {
                    showEnemy(nodeLayouts[i][j])
                }
            }
        }
    }

    private fun showAlly(node: ItemNodeBinding) {
        node.imgViewAlly.show()
        node.imgViewEnemy.hide()
        node.imgViewFrom.hide()
        node.imgViewTo.hide()
    }

    private fun showEnemy(node: ItemNodeBinding) {
        node.imgViewAlly.hide()
        node.imgViewEnemy.show()
        node.imgViewFrom.hide()
        node.imgViewTo.hide()
    }

    private fun hideNode(node: ItemNodeBinding) {
        node.imgViewAlly.hide()
        node.imgViewEnemy.hide()
        node.imgViewFrom.hide()
        node.imgViewTo.hide()
    }

    private fun highlightCanMoveTo(positionItems: ArrayList<PositionItem>) {
        for (i in positionItems) {
            nodeLayouts[i.row][i.column].imgViewTo.show()
        }
    }

    private fun unhighlightAllMoves() {
        for (i in 1..4) {
            for (j in 1..4) {
                nodeLayouts[i][j].imgViewFrom.hide()
                nodeLayouts[i][j].imgViewTo.hide()
            }
        }
    }
}