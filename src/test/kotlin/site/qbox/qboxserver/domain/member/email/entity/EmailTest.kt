package site.qbox.qboxserver.domain.member.email.entity

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe


@DisplayName("member - email")
class EmailTest : DescribeSpec({

    describe("올바르지 않은 이메일 형식") {

        it("빈 문자열일 때 예외를 반환한다.") {
            shouldThrow<IllegalArgumentException> {
                Email("")
            }
        }

        it("유효하지 않은 형식일 때 예외를 반환한다.") {
            shouldThrow<IllegalArgumentException> {
                Email("www")
            }

            shouldThrow<IllegalArgumentException> {
                Email("www.example.com")
            }
        }

        it("유효한 이메일 형식일 때 예외를 반환하지 않는다.") {
            shouldNotThrow<IllegalArgumentException> {
                Email("aaa@example.com")
            }
        }
    }

    it("도메인을 반환한다.") {
        val domain = "example.com"
        val email = Email("aaa@${domain}")

        email.domain shouldBe domain
    }
})
