package com.coluango.view.game.chess_board

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 11:03 PM
 */
class ChessBoardState(val status: Status, val data: Any? = null) {

    companion object {

        fun drawChessBoard(nodes: Array<Array<Int>>) =
            ChessBoardState(Status.DRAW_CHESS_BOARD, nodes)

        fun highlightSelectedNode(positionItem: PositionItem) =
            ChessBoardState(Status.HIGHLIGHT_SELECTED_NODE, positionItem)

        fun canMoveTo(positionItems: ArrayList<PositionItem>) =
            ChessBoardState(Status.CAN_MOVE_TO, positionItems)

        fun hideNode(positionItem: PositionItem) = ChessBoardState(Status.HIDE_NODE, positionItem)

        fun showAlly(positionItem: PositionItem) = ChessBoardState(Status.SHOW_ALLY, positionItem)

        fun showEnemy(positionItem: PositionItem) = ChessBoardState(Status.SHOW_ENEMY, positionItem)
    }

    enum class Status {
        DRAW_CHESS_BOARD,
        HIGHLIGHT_SELECTED_NODE,
        CAN_MOVE_TO,
        HIDE_NODE,
        SHOW_ALLY,
        SHOW_ENEMY
    }
}