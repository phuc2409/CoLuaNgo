package com.coluango.view.game.chess_board

import com.coluango.view.game.chess_board.item.PositionItem

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 11:03 PM
 */
class ChessBoardState(val status: Status, val data: Any? = null) {

    companion object {

        fun drawChessBoard(nodes: ArrayList<ArrayList<Int>>) =
            ChessBoardState(Status.DRAW_CHESS_BOARD, nodes)

        fun highlightSelectedNode(positionItem: PositionItem) =
            ChessBoardState(Status.HIGHLIGHT_SELECTED_NODE, positionItem)

        fun canMoveTo(positionItems: ArrayList<PositionItem>) =
            ChessBoardState(Status.CAN_MOVE_TO, positionItems)

        fun hideNode(positionItem: PositionItem) = ChessBoardState(Status.HIDE_NODE, positionItem)

        fun showAlly(positionItem: PositionItem) = ChessBoardState(Status.SHOW_ALLY, positionItem)

        fun showEnemy(positionItem: PositionItem) = ChessBoardState(Status.SHOW_ENEMY, positionItem)

        fun showGreenTurn() = ChessBoardState(Status.SHOW_GREEN_TURN)

        fun showRedTurn() = ChessBoardState(Status.SHOW_RED_TURN)

        fun showGreenWin() = ChessBoardState(Status.SHOW_GREEN_WIN)

        fun showRedWin() = ChessBoardState(Status.SHOW_RED_WIN)
    }

    enum class Status {
        DRAW_CHESS_BOARD,
        HIGHLIGHT_SELECTED_NODE,
        CAN_MOVE_TO,
        HIDE_NODE,
        SHOW_ALLY,
        SHOW_ENEMY,
        SHOW_GREEN_TURN,
        SHOW_RED_TURN,
        SHOW_GREEN_WIN,
        SHOW_RED_WIN
    }
}