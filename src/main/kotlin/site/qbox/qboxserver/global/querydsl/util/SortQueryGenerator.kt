package site.qbox.qboxserver.global.querydsl.util

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.Expressions
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class SortQueryGenerator {
    fun generate(sort: Sort): Array<OrderSpecifier<String>> {
        val specs = sort.map {
            val order = when {
                it.isAscending -> Order.ASC
                else -> Order.DESC
            }
            OrderSpecifier(order, Expressions.stringPath(it.property))
        }.toList()

        return specs.toTypedArray()
    }
}