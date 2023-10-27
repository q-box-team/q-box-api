package site.qbox.qboxserver.domain.member.command.svc

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.dto.SignUpReq


@SpringBootTest
@DisplayName("MemberSvc")
internal class MemberSvcTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var memberSvc: MemberSvc

    @Autowired
    lateinit var memberRepo: MemberRepo

    init {
        it("회원가입을 수행한다") {
            val request = SignUpReq("adsf@naver.com", "별명")
            memberSvc.signUp(request)
            val savedMember = memberRepo.findById(request.email).get()

            savedMember.nickname shouldBe request.nickname
            savedMember.password.shouldNotBeBlank()
        }
    }
}
