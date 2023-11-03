package site.qbox.qboxserver.domain.member.query

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.depart.command.DepartRepo
import site.qbox.qboxserver.domain.depart.command.entity.Depart
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.fixture.member.MemberFixture
import site.qbox.qboxserver.global.exception.NotFoundException

@SpringBootTest
@DisplayName("MemberDao")
class MemberDaoTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var memberDao: MemberDao

    @Autowired
    lateinit var memberRepo: MemberRepo

    @Autowired
    lateinit var departRepo: DepartRepo

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    init {
        describe("이미 맴버들이 저장되어 있을 때") {
            val targetEmail = "aaa@naver.com"
            val targetNickname = "nick target"
            val targetPassword = "pw1"
            beforeEach {
                val depart = departRepo.save(Depart("게임 공학", "naver.com"))
                memberRepo.saveAll(MemberFixture.members)
                memberRepo.save(Member(targetEmail, targetNickname ,targetPassword, depart.id, passwordEncoder))

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

            it("등록된 email로 찾을 수 없으면 예외를 반환한다.") {
                shouldThrow<NotFoundException> {
                    memberDao.getByEmail("whoareyou@gmail.com")
                }
            }
        }
        afterEach {
            memberRepo.deleteAll()
            departRepo.deleteAll()
        }
    }
}