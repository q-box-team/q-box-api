package site.qbox.qboxserver.study

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec

@DisplayName("DescribeSpec")
class DciTest : DescribeSpec(){

    init {
        beforeEach {
            println("before each")
        }
        it("첫 테스트를 수행한다") {
            println("test1")
        }

        it("두번째 테스트를 수행한다") {
            println("test2")
        }

        context("context 내부에 있을 때") {
            beforeEach {
                println("context before each")
            }
            it("첫 테스트를 수행한다") {
                println("context test1")
            }

            it("두번째 테스트를 수행한다") {
                println("context test2")
            }
        }

        afterEach {
            println("after each")
        }
    }
}