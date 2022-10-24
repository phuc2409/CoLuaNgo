package com.coluango.view.game.chess_board

import android.util.Log
import com.coluango.common.copy
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 11:04 PM
 */
class ChestBoardItem {

    companion object {

        private const val TAG = "ChestBoardItem"

        private const val INVALID_MOVE_POINT = 0
        private const val NORMAL_MOVE_POINT = 1
        private const val EAT_MOVE_POINT = 100
        private const val WIN_MOVE_POINT = 1000
        private const val LOSE_MOVE_POINT = -1000

        private const val MAX = 100000
        private const val MIN = -100000

        /**
         * Độ sâu thuật toán alpha beta
         */
        private const val ALPHA_BETA_DEPTH = 2

        /**
         * Một lượt có tối đa 20 nước khác nhau (ước lượng)
         */
        private const val MAX_MOVE = 20
    }

    /**
     * -1: viền bàn cờ, 0: ô trống, 1: quân mình, 2: quân địch
     */
    val nodes = arrayListOf(
        arrayListOf(-1, -1, -1, -1, -1, -1),
        arrayListOf(-1, -1, 2, 2, -1, -1),
        arrayListOf(-1, 0, 2, 2, 0, -1),
        arrayListOf(-1, 0, 1, 1, 0, -1),
        arrayListOf(-1, -1, 1, 1, -1, -1),
        arrayListOf(-1, -1, -1, -1, -1, -1)
    )

    private val directions = arrayOf(arrayOf(-1, 0), arrayOf(0, 1), arrayOf(1, 0), arrayOf(0, -1))

    fun selectNode(
        nodes: ArrayList<ArrayList<Int>>,
        row: Int,
        column: Int
    ): ArrayList<PositionItem> {
        val selectedValue = nodes[row][column]
        val canMoveTo = ArrayList<PositionItem>()
        val queue: Queue<PositionItem> = LinkedList()
        queue.add(PositionItem(row, column))
        var minDistance = 1
        var queueSizeForCurrentDistance = 1

        val newNodes = copy(nodes)

        while (queue.isNotEmpty()) {
            val position = queue.poll()!!
            for (i in directions) {
                val nodeValue = newNodes[position.row + i[0]][position.column + i[1]]
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

                newNodes[position.row + i[0]][position.column + i[1]] = -1
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

    fun canMove(nodes: ArrayList<ArrayList<Int>>, row: Int, column: Int): Boolean {
        for (i in directions) {
            if (nodes[row + i[0]][column + i[1]].toInt() == 0) {
                return true
            }
        }
        return false
    }

    /**
     * Hàm kiểm tra chiến thắng
     */
    fun isWinning(turn: Int): Boolean {
        for (i in 1..4) {
            for (j in 1..4) {
                if (nodes[i][j] != -1 && nodes[i][j] != 0 && nodes[i][j] != turn) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Hàm đánh giá nước đi
     */
    private fun getPointOfMove(turn: Int, toRow: Int, toColumn: Int): Int {
        if (nodes[toRow][toColumn] == -1 || nodes[toRow][toColumn] == turn) {
            return INVALID_MOVE_POINT
        }

        if (nodes[toRow][toColumn] == 0) {
            return NORMAL_MOVE_POINT
        }

        if (nodes[toRow][toColumn] != turn) {
            if (isWinning(turn)) {
                return WIN_MOVE_POINT
            }
        }
        return EAT_MOVE_POINT
    }

    private fun getPointOfMove(nodes: ArrayList<ArrayList<Int>>, turn: Int): Int {
        var count1 = 0
        var count2 = 0

        for (i in 1..4) {
            for (j in 1..4) {
                if (nodes[i][j].toInt() == 1) {
                    count1++
                } else if (nodes[i][j].toInt() == 2) {
                    count2++
                }
            }
        }

        if (turn == 1) {
            if (count2 == 0) {
                return WIN_MOVE_POINT
            }
            if (count1 == 0) {
                return LOSE_MOVE_POINT
            }
            return count1 - count2
        }
        if (count1 == 0) {
            return WIN_MOVE_POINT
        }
        if (count2 == 0) {
            return LOSE_MOVE_POINT
        }
        return count2 - count1
    }

    private fun getAiMove(): ArrayList<MoveItem?> {
        val nodes = copy(this.nodes)
        var turn = 2
        var depth = 0
        val tree = ArrayList<MoveItem?>()
        tree.add(MoveItem(copy(nodes), 0, 0, 0, 0))
        var size = tree.size
        var pos = 0

        while (pos < tree.size) {
            tree[pos]?.let {
                Log.i(TAG, "pos: $pos")

                val newTree = ArrayList<MoveItem?>()

                for (i in 1..4) {
                    for (j in 1..4) {
                        if (it.nodes[i][j].toInt() == turn && canMove(it.nodes, i, j)) {
                            val moves = selectNode(it.nodes, i, j)
                            for (k in moves) {
                                val newNodes = copy(it.nodes)
                                newNodes[k.row][k.column] = turn
                                newNodes[i][j] = 0

                                val point = if (depth == ALPHA_BETA_DEPTH - 1) {
                                    10 + getPointOfMove(newNodes, turn)
                                } else {
                                    0
                                }
                                if (depth == 0) {
                                    newTree.add(MoveItem(newNodes, i, j, k.row, k.column, point))
                                } else {
                                    //todo: Sửa array list lưu đường đi
                                    newTree.add(
                                        MoveItem(
                                            newNodes,
                                            it.fromRow,
                                            it.fromColumn,
                                            it.toRow,
                                            it.toColumn,
                                            point
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                while (newTree.size < MAX_MOVE) {
                    newTree.add(null)
                }
                tree.addAll(newTree)
            }

            pos++
            if (pos == size) {
                Log.i(TAG, "size: ${tree.size}")
                depth++
                if (depth >= ALPHA_BETA_DEPTH) {
                    break
                }
                size = tree.size
                if (turn == 1) {
                    turn = 2
                } else if (turn == 2) {
                    turn = 1
                }
            }
        }
        Log.i(TAG, tree.size.toString())

        for (i in 0 until tree.size) {
            if (tree[i] != null) {
                Log.i(TAG, "$i ${tree[i]}")
            } else {
                Log.i(TAG, "$i null")
            }
        }
        return tree
    }

    // Returns optimal value for current player (Initially called for root and maximizer)
    private fun minimax(
        depth: Int, nodeIndex: Int,
        maximizingPlayer: Boolean,
        values: ArrayList<MoveItem?>, alpha: Int,
        beta: Int
    ): AlphaBetaItem {
        // Terminating condition. i.e
        // leaf node is reached
        var newAlpha = alpha
        var newBeta = beta
        if (depth == ALPHA_BETA_DEPTH) {
            Log.i(TAG, "node index: $nodeIndex ${values[nodeIndex]}")
            return AlphaBetaItem(
                values[nodeIndex]!!.point,
                nodeIndex
            )
        }

        if (maximizingPlayer) {
            var best = MIN
            var pos = -1

            // Recur for left and right children
            for (i in 0 until MAX_MOVE) {
                if (nodeIndex * MAX_MOVE + i >= values.size || values[nodeIndex * MAX_MOVE + i] == null) {
                    break
                }

                val value = minimax(
                    depth + 1, nodeIndex * MAX_MOVE + i,
                    false, values, newAlpha, newBeta
                )
                best = max(best, value.value)
                newAlpha = max(newAlpha, best)

                if (value.value >= best) {
                    pos = value.pos
                }

                // Alpha Beta Pruning
                if (newBeta <= newAlpha) {
                    break
                }
            }
            return AlphaBetaItem(best, pos)
        } else {
            var best = MAX
            var pos = -1

            // Recur for left and right children
            for (i in 0 until MAX_MOVE) {
                if (nodeIndex * MAX_MOVE + i >= values.size || values[nodeIndex * MAX_MOVE + i] == null) {
                    break
                }

                val value = minimax(
                    depth + 1, nodeIndex * MAX_MOVE + i,
                    true, values, newAlpha, newBeta
                )
                best = min(best, value.value)
                newBeta = min(newBeta, best)

                if (value.value <= best) {
                    pos = value.pos
                }

                // Alpha Beta Pruning
                if (newBeta <= newAlpha) {
                    break
                }
            }
            return AlphaBetaItem(best, pos)
        }
    }

    fun cal() {
        val tree = getAiMove()
        val tree2 = ArrayList<MoveItem?>()
        var pos = 0

        for (i in 0 until tree.size) {
            if (tree[i] != null && tree[i]!!.point != 0) {
                pos = i
                break
            }
        }


        for (i in pos until tree.size) {
            tree2.add(tree[i])
        }

        Log.i(TAG, "tree2 all:")
        for (i in 0 until tree2.size) {
            Log.i(TAG, "$i ${tree2[i]}")
        }
        Log.i(TAG, "")

        val x = minimax(0, 0, true, tree2, MIN, MAX)
        Log.i(TAG, "alpha beta: $x")
        Log.i(TAG, "tree2: ${tree2[x.pos]}")
    }
}