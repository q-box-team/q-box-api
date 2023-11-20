package site.qbox.qboxserver.study

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec

class RequireTest : DescribeSpec() {


    fun requireIn(value: String) {
        val list = listOf("A", "B", "C", "D", "E", "F")
        require(value in list)
    }

    init {
        it("in 값에 포함되면 예외를 반환하지 않는다.") {
            shouldNotThrow<IllegalArgumentException> {
                requireIn("A")
            }
        }
        it("in 값에 포함되지 않으면 예외를 반환한다.") {
            shouldThrow<IllegalArgumentException> {
                requireIn("KK")
            }
        }
    }
}