package ru.notsmak.pr204

import java.util.SortedMap
import kotlin.math.absoluteValue

class Polynomial(coeff: Map<Int,Double> = mapOf(0 to 0.0)) {
    private val _coeff: SortedMap<Int, Double> = coeff.toSortedMap()
    val coeff: Map<Int, Double>
        get() = _coeff.toMap()

    init { //когда в конструкторе мало места для функций
        clearZeros()
    }

    override fun toString(): String = buildString {
        for ((p, v) in _coeff.asIterable().reversed()) {
            if (v >= 0) {
                if (p != _coeff.lastKey()) append('+')
            } else append('-')
            if (v.absoluteValue != 1.0 || p == 0)
                append(v.absoluteValue)
            if (p > 0) append('x')
            if (p > 1) append('^').append(p)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Polynomial) return false
        return _coeff.equals(other._coeff) //зелёный потому что котлин умный и привёл тип
    }
    override fun hashCode(): Int = _coeff.hashCode()*31

    private fun clearZeros(){
        val c = _coeff.filterValues{ it.absoluteValue > 0 } //уже цикл //все ложные уничтожат
            .filterKeys{ it >= 0} //всё ещё метод
        _coeff.clear()
        //_coeff.putAll(if (c.isEmpty()) mapOf(0 to 0.0) else c) //тож самое
        _coeff.putAll(c.ifEmpty { mapOf(0 to 0.0)})
    }

    operator fun plus(other: Polynomial) = Polynomial( //Без new
        _coeff.toMutableMap().apply { //без this., сразу зная что это они
            other._coeff.forEach{(p,v) ->
                this[p]=(this[p] ?: 0.0) + v //ifelse,также это автоматически this
            }
        }
    )

    operator fun minus(other: Polynomial) = Polynomial(
        _coeff.toMutableMap().apply {
            other._coeff.forEach { (p, v) ->
                this[p] = (this[p] ?: 0.0) - v
            }
        }
    )

    operator fun times(k: Double) = Polynomial(
        _coeff.map{(p,v) -> p to k*v}.toMap()
    )

    operator fun times(other: Polynomial) = Polynomial(
        mutableMapOf<Int,Double>().also {
            _coeff.forEach{ (p1,v1) ->
                other._coeff.forEach { (p2,v2) ->
                    it[p1+p2] = (it[p1+p2] ?: 0.0) + v1*v2

                }
            }
        }

    )

    operator fun div(k: Double): Polynomial {
        if (k == 0.0) {
            throw IllegalArgumentException()
        }
        return Polynomial(_coeff.map{(p,v) -> p to v/k}.toMap())
    }

    operator fun invoke(k: Double) : Double {
        var sum : Double = 0.0
        for ((p, v) in _coeff.asIterable()) {
            sum += v*Math.pow(k, p.toDouble())
        }
        return sum
    }

    operator fun plusAssign(other: Polynomial) {
        val result = this + other
        _coeff.clear()
        _coeff.putAll(result._coeff)
    }

    operator fun minusAssign(other: Polynomial) {
        val result = this - other
        _coeff.clear()
        _coeff.putAll(result._coeff)
    }

    operator fun timesAssign(other: Polynomial) {
        val result = this * other
        _coeff.clear()
        _coeff.putAll(result._coeff)
    }

    operator fun timesAssign(k: Double) {
        val result = this * k
        _coeff.clear()
        _coeff.putAll(result._coeff)
    }

    operator fun divAssign(k: Double) {
        val result = this / k
        _coeff.clear()
        _coeff.putAll(result._coeff)
    }
}

operator fun Double.times(p: Polynomial) = p*this //число это this, умножение определённое