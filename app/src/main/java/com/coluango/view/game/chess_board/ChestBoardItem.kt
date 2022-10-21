package com.coluango.view.game.chess_board

import com.coluango.common.fromJson
import com.coluango.common.toJson
import java.util.LinkedList
import java.util.Queue

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

    private val directions = arrayOf(arrayOf(-1, 0), arrayOf(0, 1), arrayOf(1, 0), arrayOf(0, -1))

    fun selectNode(row: Int, column: Int): ArrayList<PositionItem> {
        val selectedValue = nodes[row][column]
        val canMoveTo = ArrayList<PositionItem>()
        val queue: Queue<PositionItem> = LinkedList()
        queue.add(PositionItem(row, column))
        var minDistance = 1
        var queueSizeForCurrentDistance = 1

        val json = nodes.toJson()
        val nodes = fromJson<ArrayList<ArrayList<Int>>>(json)

        while (queue.isNotEmpty()) {
            val position = queue.poll()!!
            for (i in directions) {
                val nodeValue = nodes[position.row + i[0]][position.column + i[1]]
                if (nodeValue == 0) {
                    val newPosition = PositionItem(position.row + i[0], position.column + i[1])
                    queue.add(newPosition)
                    if (minDistance % 2 == 1) {
                        canMoveTo.add(newPosition)
                    }
                } else if (nodeValue > 0 && nodeValue != selectedValue && minDistance % 2 == 1) {
                    val newPosition = PositionItem(position.row + i[0], position.column + i[1])
                    canMoveTo.add(newPosition)
                }

                nodes[position.row + i[0]][position.column + i[1]] = -1
            }
            queueSizeForCurrentDistance--
            if (queueSizeForCurrentDistance == 0) {
                minDistance++
                queueSizeForCurrentDistance = queue.size
            }
        }

        return canMoveTo
    }

    fun moveTo(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int) {
        nodes[toRow][toColumn] = nodes[fromRow][fromColumn]
        nodes[fromRow][fromColumn] = 0
    }

    fun canMove(row: Int, column: Int): Boolean {
        for (i in directions) {
            if (nodes[row + i[0]][column + i[1]] == 0) {
                return true
            }
        }
        return false
    }
}