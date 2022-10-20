package com.coluango.view.game.chess_board

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 11:04 PM
 */
class ChestBoardItem {

    /**
     * -1: viền bàn cờ, 0: ô trống, 1: quân mình, 2: quân địch
     */
    val nodes = arrayOf(
        arrayOf(-1, -1, -1, -1, -1, -1),
        arrayOf(-1, -1, 2, 2, -1, -1),
        arrayOf(-1, 0, 2, 2, 0, -1),
        arrayOf(-1, 0, 1, 1, 0, -1),
        arrayOf(-1, -1, 1, 1, -1, -1),
        arrayOf(-1, -1, -1, -1, -1, -1)
    )

    fun selectNode(row: Int, column: Int): ArrayList<PositionItem> {
        val selectedValue = nodes[row][column]
        val canMoveTo = ArrayList<PositionItem>()

        for (i in 1..4) {
            for (j in 1..4) {
                if (nodes[i][j] != selectedValue && nodes[i][j] != -1) {
                    canMoveTo.add(PositionItem(i, j))
                }
            }
        }
        return canMoveTo
    }

    fun moveTo(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int) {
        nodes[toRow][toColumn] = nodes[fromRow][fromColumn]
        nodes[fromRow][fromColumn] = 0
    }
}