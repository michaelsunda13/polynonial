package ru.notsmak.pr204

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.awt.Polygon

class PolynomialTest {

    @Test
    fun getCoeff() {
        //val p = Polynomial()
        //assertEquals(mapOf(0 to 0.0), p.coeff)
        with(Polynomial()){
            assertEquals(mapOf(0 to 0.0), coeff)
        }
        with(Polynomial(mapOf(1 to 2.3, 2 to 0.0, 3 to 7.4))) {
            assertEquals(mapOf(1 to 2.3, 3 to 7.4), coeff)
        }
        with(Polynomial(mapOf(1 to 2.3, 2 to 3.7, 1 to 0.0))) {
            assertEquals(mapOf(2 to 3.7), coeff)
        }
        with(Polynomial(mapOf(-5 to 3.3))) {
            assertEquals(mapOf(0 to 0.0), coeff)
        }
    }

    @Test
    fun testToString() {
        with(Polynomial()){
            assertEquals("0.0", toString())
        }
        with(Polynomial(mapOf(0 to 1.0))){
            assertEquals("1.0", toString())
        }
        with(Polynomial(mapOf(0 to 1.0))){
            assertEquals("1.0", toString())
        }
        with(Polynomial(mapOf(0 to -1.0))){
            assertEquals("-1.0", toString())
        }
        with(Polynomial(mapOf(1 to 1.0))){
            assertEquals("x", toString())
        }
        with(Polynomial(mapOf(1 to -1.0))){
            assertEquals("-x", toString())
        }
        with(Polynomial(mapOf(1 to 2.0))){
            assertEquals("2.0x", toString())
        }
        with(Polynomial(mapOf(1 to 1.0, 0 to 2.0))){
            assertEquals("x+2.0", toString())
        }
        with(Polynomial(mapOf(1 to -3.0, 0 to 4.0))){
            assertEquals("-3.0x+4.0", toString())
        }
        with(Polynomial(mapOf(2 to 1.0))){
            assertEquals("x^2", toString())
        }
    }

    @Test
    fun testPlus(){
        with(Polynomial(mapOf(3 to 3.0, 0 to 3.0))){
            val p1 = Polynomial(mapOf(2 to 2.0, 1 to 3.0, 0 to -1.0))
            val p2 = Polynomial(mapOf(3 to 3.0, 2 to -2.0, 1 to -3.0, 0 to 4.0))
            assertEquals(this, p1+p2)
        }
    }

    @Test
    fun testMinus(){
        with(Polynomial(mapOf(3 to -3.0, 2 to 4.0, 1 to 6.0, 0 to -5.0))){
            val p1 = Polynomial(mapOf(2 to 2.0, 1 to 3.0, 0 to -1.0))
            val p2 = Polynomial(mapOf(3 to 3.0, 2 to -2.0, 1 to -3.0, 0 to 4.0))
            assertEquals(this, p1-p2)
        }
    }

    @Test
    fun testTimes() {
        with(Polynomial(mapOf(2 to 2.0, 1 to -6.0, 0 to 3.0))) {
            val k = 2.0
            val p = Polynomial(mapOf(2 to 1.0, 1 to -3.0, 0 to 1.5))
            assertEquals(this, p * k)
            assertEquals(this, k * p) //определяет умножение по левому операнду
        }

        with(Polynomial(mapOf(2 to 6.0, 1 to 7.0, 0 to -5.0))){
            val p1 = Polynomial(mapOf(1 to 2.0, 0 to -1.0))
            val p2 = Polynomial(mapOf(1 to 3.0, 0 to 5.0))
            assertEquals(this, p1*p2)
        }
    }

    @Test
    fun testInvoke() {
        with(Polynomial(mapOf(2 to 1.0, 0 to 1.0))) {
            assertEquals(10.0, this(3.0))
            assertEquals(1.0, this(0.0))
            assertEquals(2.0, this(1.0))
            assertEquals(5.0, this(-2.0))
        }
        with(Polynomial(mapOf(0 to 5.0))) {
            assertEquals(5.0, this(100.0))
        }

    }

    @Test
    fun testDiv() {
        with(Polynomial(mapOf(2 to 2.0, 1 to 4.0, 0 to 6.0))) {
            val divided = this / 2.0
            assertEquals(Polynomial(mapOf(2 to 1.0, 1 to 2.0, 0 to 3.0)), divided)
        }
        assertThrows<IllegalArgumentException>{Polynomial(mapOf(0 to 0.0))/0.0}
        assertThrows<IllegalArgumentException>{Polynomial(mapOf(5 to 0.0))/0.0}
        assertThrows<IllegalArgumentException>{Polynomial(mapOf(1 to 1.0, 1 to 0.0))/0.0}
    }


    @Test
    fun testPlusAssign() {
        with(Polynomial(mapOf(3 to 3.0, 0 to 3.0))){
            val p1 = Polynomial(mapOf(2 to 2.0, 1 to 3.0, 0 to -1.0))
            val p2 = Polynomial(mapOf(3 to 3.0, 2 to -2.0, 1 to -3.0, 0 to 4.0))
            p1 += p2
            assertEquals(this, p1)
        }
    }

    @Test
    fun testMinusAssign(){
        with(Polynomial(mapOf(3 to -3.0, 2 to 4.0, 1 to 6.0, 0 to -5.0))){
            val p1 = Polynomial(mapOf(2 to 2.0, 1 to 3.0, 0 to -1.0))
            val p2 = Polynomial(mapOf(3 to 3.0, 2 to -2.0, 1 to -3.0, 0 to 4.0))
            p1 -= p2
            assertEquals(this, p1)
        }
    }

    @Test
    fun testTimesAssign() {
        with(Polynomial(mapOf(2 to 2.0, 1 to -6.0, 0 to 3.0))) {
            val k = 2.0
            val p = Polynomial(mapOf(2 to 1.0, 1 to -3.0, 0 to 1.5))
            p *= k
            assertEquals(this, p)
        }

        with(Polynomial(mapOf(2 to 6.0, 1 to 7.0, 0 to -5.0))){
            val p1 = Polynomial(mapOf(1 to 2.0, 0 to -1.0))
            val p2 = Polynomial(mapOf(1 to 3.0, 0 to 5.0))
            p1 *= p2
            assertEquals(this, p1)
        }
    }

    @Test
    fun testDivAssign() {
        with(Polynomial(mapOf(2 to 2.0, 1 to 4.0, 0 to 6.0))) {
            val p = this / 2.0
            this /= 2.0
            assertEquals(this, p)
        }
        assertThrows<IllegalArgumentException>{
            with(Polynomial(mapOf(0 to 0.0))){
                this /= 0.0
            }
        }
        assertThrows<IllegalArgumentException>{
            with(Polynomial(mapOf(5 to 0.0))){
                this /= 0.0
            }
        }
        assertThrows<IllegalArgumentException>{
            with(Polynomial(mapOf(1 to 1.0, 1 to 0.0))){
                this /=  0.0
            }
        }


    }

    @Test
    fun testHashCode(){
        val p1 = Polynomial(mapOf(2 to 3.0, 1 to 4.0))
        val p2 = Polynomial(mapOf(1 to 4.0, 2 to 3.0))
        assertEquals(p1.hashCode(), p2.hashCode())

        val p3 = Polynomial(mapOf(1 to 0.0, 2 to 0.0))
        val p4 = Polynomial(mapOf(0 to 0.0))
        assertEquals(p3.hashCode(), p4.hashCode())

        val p5 = Polynomial(mapOf(2 to 3.0))
        val p6 = Polynomial(mapOf(1 to 3.0))
        assertNotEquals(p5.hashCode(), p6.hashCode())

        val p7 = Polynomial(mapOf(2 to 3.0))
        val p8 = Polynomial(mapOf(2 to 4.0))
        assertNotEquals(p7.hashCode(), p8.hashCode())
    }

    @Test
    fun testEquals() {
        val p1 = Polynomial(mapOf(2 to 3.0, 1 to 4.0))
        val p2 = Polynomial(mapOf(1 to 4.0, 2 to 3.0))
        assertEquals(p1, p2) //Порядок не важен

        val p3 = Polynomial(mapOf(2 to 3.0))
        val p4 = Polynomial(mapOf(2 to 4.0))
        assertNotEquals(p3, p4) //Разные значения не равны

        val p5 = Polynomial(mapOf(2 to 3.0))
        val p6 = Polynomial(mapOf(3 to 3.0))
        assertNotEquals(p5, p6) //Один коэф при разной степени не равен

        val p7 = Polynomial(mapOf(0 to 5.0))
        assertNotEquals(p7, 5.0) //Полином не равен числу

        val p8 = Polynomial(mapOf(1 to 0.0, 2 to 0.0))
        val p9 = Polynomial(mapOf(0 to 0.0))
        assertEquals(p8, p9) //Нулевые равны
    }
}