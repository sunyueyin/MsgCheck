package com.xiyuan.template

import com.xiyuan.msgCheck.filter.impl.letterMatrix.model.MTuple3

import scala.collection.mutable.ArrayBuffer

/**
  * Created by xiyuan_fengyu on 2016/8/9 16:05.
  */
object Test {

  def main(args: Array[String]) {
    val normalArr = Array(142, 264, 1203, 87, 78, 134, 24567, 235, 478, 2567, 975, 15689)
    val dirtyArr = Array(2659871, 809296, 8180043, 804567, 289760, 343400, 5004567, 589760, 243400, 305800, 303400, 1864066)

    val normalLen = normalArr.length
    val dirtyLen = dirtyArr.length

    val allArr = new ArrayBuffer[MTuple3[Int, Int, Double]](normalLen + dirtyLen)
    normalArr.foreach(item => allArr += new MTuple3[Int, Int, Double](0, item, 0))
    dirtyArr.foreach(item => allArr += new MTuple3[Int, Int, Double](1, item, 0))
    val sortedAllArr = allArr.sortWith(_._2 < _._2)
    val allLen = sortedAllArr.length
    var lastI = 0
    var lastV = sortedAllArr(0)._2
    var curIndexSum = 0
    for (i <- 0 to allLen) {
      if ((i < allLen && sortedAllArr(i)._2 != lastV) || i == allLen) {
        val index = curIndexSum / (i - lastI).toDouble
        for (j <- lastI until i) {
          sortedAllArr(j)._3 = index
        }

        lastI = i
        if (i < allLen) {
          lastV = sortedAllArr(i)._2
        }
        curIndexSum = 0
      }
      curIndexSum += i + 1
    }

    sortedAllArr.foreach(println)

    var normalSum: Double = 0
    var dirtySum: Double = 0
    sortedAllArr.foreach(item => {
      if (item._1 == 0) {
        normalSum += item._3
      }
      else {
        dirtySum += item._3
      }
    })

    val t = normalSum
    val result = (t - normalLen * (normalLen + dirtyLen + 1) / 2.0) / math.pow(normalLen * dirtyLen * (normalLen + dirtyLen + 1) / 12.0, 0.5)
    println(result)
  }

}