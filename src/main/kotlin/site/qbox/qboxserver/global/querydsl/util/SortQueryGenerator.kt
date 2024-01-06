package site.qbox.qboxserver.global.querydsl.util

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.*
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class SortQueryGenerator {
    fun generate(sort: Sort): Array<OrderSpecifier<*>> {
        val specs = sort.map {
            val direction = when {
                it.isAscending -> Order.ASC
                else -> Order.DESC
            }
            val path = Expressions.comparablePath(Comparable::class.java, it.property)
            OrderSpecifier(direction, path)
        }.toList()

        return specs.toTypedArray()
    }
}