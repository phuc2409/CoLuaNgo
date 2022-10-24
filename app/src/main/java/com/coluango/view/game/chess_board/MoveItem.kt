package com.coluango.view.game.chess_board

/**
 * User: Quang Phúc
 * Date: 23-Oct-22
 * Time: 10:58 PM
 */
data class MoveItem(
    val nodes: ArrayList<ArrayList<Int>>,
    val fromRow: Int,
    val fromColumn: Int,
    val toRow: Int,
    val toColumn: Int,
    var point: Int = 0
)
