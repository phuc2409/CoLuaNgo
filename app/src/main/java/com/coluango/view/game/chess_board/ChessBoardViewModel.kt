package com.coluango.view.game.chess_board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coluango.base.BaseViewModel

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 11:02 PM
 */
class ChessBoardViewModel : BaseViewModel() {

    private val _state = MutableLiveData<ChessBoardState>()
    val state: LiveData<ChessBoardState> = _state

    private val chestBoardItem = ChestBoardItem()

    private var turn = 1
    private var selectedRow = 0
    private var selectedColumn = 0

    fun drawBoard() {
        _state.value = ChessBoardState.drawChessBoard(chestBoardItem.nodes)
    }

    fun selectNode(row: Int, column: Int) {
        if (chestBoardItem.nodes[row][column] != turn) {
            return
        }
        _state.value = ChessBoardState.highlightSelectedNode(PositionItem(row, column))

        selectedRow = row
        selectedColumn = column
        val canMoveTo = chestBoardItem.selectNode(row, column)
        _state.value = ChessBoardState.canMoveTo(canMoveTo)
    }

    fun moveTo(toRow: Int, toColumn: Int) {
        _state.value = ChessBoardState.hideNode(PositionItem(selectedRow, selectedColumn))

        chestBoardItem.moveTo(selectedRow, selectedColumn, toRow, toColumn)

        if (turn == 1) {
            _state.value = ChessBoardState.showAlly(PositionItem(toRow, toColumn))
            turn = 2
        } else if (turn == 2) {
            _state.value = ChessBoardState.showEnemy(PositionItem(toRow, toColumn))
            turn = 1
        }
    }
}