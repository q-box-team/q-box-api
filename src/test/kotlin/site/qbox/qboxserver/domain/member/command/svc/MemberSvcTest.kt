package site.qbox.qboxserver.domain.member.command.svc

import io.kotest.core.spec.DisplayName
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import org.springframework.beans.factory.annotation.Autowired
import site.qbox.qboxserver.config.IntegrationTest
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.MemberSvc
import site.qbox.qboxserver.domain.member.command.dto.SignUpReq
import site.qbox.qboxserver.domain.member.email.entity.Email
import site.qbox.qboxserver.domain.member.email.infra.AuthenticatedEmailRepo


@DisplayName("MemberSvc")
internal class MemberSvcTest : IntegrationTest() {


    @Autowired
    lateinit var memberSvc: MemberSvc

    @Autowired
    lateinit var memberRepo: MemberRepo

    @Autowired
    lateinit var authenticatedEmailRepo: AuthenticatedEmailRepo

    init {
        it("회원가입을 수행한다") {
            val email = "adsf@naver.com"
            val request = SignUpReq(email, "별명", "password", 2L)
            authenticatedEmailRepo.save(Email(email))
            memberSvc.signUp(request)
            val savedMember = memberRepo.findById(Email(email)).get()

            savedMember.nickname shouldBe request.nickname
            savedMember.password.shouldNotBeBlank()
        }

        afterEach {
            memberRepo.deleteAll()
        }
    }
}
