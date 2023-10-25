package site.qbox.qboxserver.domain.member.query

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.core.spec.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.entity.Member

@SpringBootTest
@DisplayName("MemberDao")
class MemberDaoTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var memberDao: MemberDao

    @Autowired
    lateinit var memberRepo: MemberRepo

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    init {
        context("이미 맴버들이 저장되어 있을 때") {
            val targetEmail = "aaa@naver.com"
            val targetNickname = "n1"
            val targetPassword = "pw1"
            beforeEach {
                memberRepo.saveAll(
                    listOf(
                        Member(targetEmail, targetNickname, targetPassword, passwordEncoder),
                        Member("bbb@naver.com", "n2", "pw2", passwordEncoder),
                        Member("ccc@naver.com", "n3", "pw3", passwordEncoder),
                    )
                )
            }

            it("이메일을 통한 조회를 수행한다") {
                val result = memberDao.getByEmail(targetEmail)

                result.email shouldBe targetEmail
                result.nickname shouldBe targetNickname
            }
            it("이메일을 통해서 인증용 정보를 조회한다.") {
                val result = memberDao.getAuth(targetEmail)

                result.email shouldBe targetEmail
                passwordEncoder.matches(targetPassword, result.password) shouldBe true
            }
        }

        afterEach {
            memberRepo.deleteAll()
        }
    }
}