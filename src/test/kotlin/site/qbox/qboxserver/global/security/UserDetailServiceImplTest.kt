package site.qbox.qboxserver.global.security

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.core.spec.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.member.command.entity.Role

@SpringBootTest
@DisplayName("UserDetailServiceImpl")
class UserDetailServiceImplTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var userDetailService: UserDetailServiceImpl

    @Autowired
    lateinit var memberRepo: MemberRepo

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    init {
        context("이미 맴버들이 저장되어 있을 때") {
            val targetEmail = "aaa@naver.com"
            val targetPassword = "pw1"
            beforeEach {
                memberRepo.saveAll(
                    listOf(
                        Member(targetEmail, "n1", targetPassword, passwordEncoder),
                        Member("bbb@naver.com", "n2", "pw2", passwordEncoder),
                        Member("ccc@naver.com", "n3", "pw3", passwordEncoder),
                    )
                )
            }

            it("username에 해당하는 member를 반환한다") {
                val result = userDetailService.loadUserByUsername(targetEmail)
                result.username shouldBe targetEmail
                passwordEncoder.matches(targetPassword, result.password) shouldBe true

                result.authorities shouldContain SimpleGrantedAuthority(Role.USER.name)
                result.name shouldBe result.name
                result.group shouldBe result.group
                result.isAccountNonExpired shouldBe true
                result.isAccountNonLocked shouldBe true
                result.isCredentialsNonExpired shouldBe true
                result.isEnabled shouldBe true
            }
        }

        afterEach {
            memberRepo.deleteAll()
        }
    }


}