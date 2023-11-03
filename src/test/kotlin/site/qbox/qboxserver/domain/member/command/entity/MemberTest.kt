package site.qbox.qboxserver.domain.member.command.entity

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import site.qbox.qboxserver.domain.member.command.exception.NotMatchPasswordException
import site.qbox.qboxserver.fixture.member.MemberFixture


@DisplayName("member")
class MemberTest : DescribeSpec({
    val initPassword = MemberFixture.members[0].password
    val emailDomain = MemberFixture.members[0].emailDomain
    lateinit var member: Member
    val passwordEncoder = NoOpPasswordEncoder.getInstance()
    beforeEach {
        member = MemberFixture.members[0]
    }

    describe("비밀번호 변경을") {
        val afterPassword = "hello"
        it("수행한다.") {
            member.changePassword(initPassword, afterPassword, passwordEncoder)

            passwordEncoder.matches(afterPassword, member.password) shouldBe true
        }
        it("수행 시 이전 비밀번호가 다르면 예외를 반환한다.") {
            shouldThrow<NotMatchPasswordException> {
                member.changePassword("other", afterPassword, passwordEncoder)
            }
        }
    }

    it("닉네임을 수정한다") {
        val newNickname = "hello"
        member.changeNickname(newNickname)

        member.nickname shouldBe newNickname
    }

    it("email 도메인을 제공한다.") {
        member.emailDomain shouldBe emailDomain
    }

})