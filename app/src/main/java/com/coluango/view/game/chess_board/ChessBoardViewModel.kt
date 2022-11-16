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

    /**
     * Đếm số nước đi của máy
     */
    private var aiMoveCount = 0

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

        if (!chestBoardItem.canMove(row, column)) {
            return
        }

        val canMoveTo = chestBoardItem.selectNode(chestBoardItem.nodes, row, column)
        _state.value = ChessBoardState.canMoveTo(canMoveTo)
    }

    fun moveTo(toRow: Int, toColumn: Int) {
        _state.value = ChessBoardState.hideNode(PositionItem(selectedRow, selectedColumn))

        chestBoardItem.moveTo(selectedRow, selectedColumn, toRow, toColumn)

        if (turn == 1) {
            _state.value = ChessBoardState.showAlly(PositionItem(toRow, toColumn))
            turn = 2
            if (chestBoardItem.isWinning(1)) {
                _state.value = ChessBoardState.showGreenWin()
            } else {
                _state.value = ChessBoardState.showRedTurn()
                aiMove()
            }
        } else if (turn == 2) {
            _state.value = ChessBoardState.showEnemy(PositionItem(toRow, toColumn))
            turn = 1
            if (chestBoardItem.isWinning(2)) {
                _state.value = ChessBoardState.showRedWin()
            } else {
                _state.value = ChessBoardState.showGreenTurn()
            }
        }
    }

    private fun aiMove() {
        val aiMoveItem = if (aiMoveCount == 0) {
            chestBoardItem.getAiFirstMove()
        } else {
            chestBoardItem.getAiMove()
        }
        aiMoveCount++
        selectedRow = aiMoveItem.fromRow
        selectedColumn = aiMoveItem.fromColumn
        moveTo(aiMoveItem.toRow, aiMoveItem.toColumn)
    }
}