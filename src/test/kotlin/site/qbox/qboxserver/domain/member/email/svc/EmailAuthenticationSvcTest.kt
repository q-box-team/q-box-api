package site.qbox.qboxserver.domain.member.email.svc

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationEventPublisher
import site.qbox.qboxserver.config.IntegrationTest
import site.qbox.qboxserver.domain.member.email.dto.CertifyKeyReq
import site.qbox.qboxserver.domain.member.email.dto.RegisterEmailReq
import site.qbox.qboxserver.domain.member.email.exception.EmailNotAuthenticatedException
import site.qbox.qboxserver.domain.member.email.infra.AuthenticatedEmailRepo
import site.qbox.qboxserver.domain.member.email.infra.EmailAuthKeyRepo


@DisplayName("EmailAuthenticationSvc")
class EmailAuthenticationSvcTest : IntegrationTest() {

    @Autowired
    lateinit var emailAuthenticationSvc: EmailAuthenticationSvc

    @Autowired
    lateinit var emailAuthKeyRepo: EmailAuthKeyRepo

    @Autowired
    lateinit var authenticationEmailRepo: AuthenticatedEmailRepo

    @MockBean
    lateinit var applicationEvenProducer: ApplicationEventPublisher

    init {
        it("신규 등록을 수행한다.") {
            val email = "aaa@example.com"
            emailAuthenticationSvc.register(RegisterEmailReq(email))

            val list = emailAuthKeyRepo.findAll().toList()
            list[0].email shouldBe email
        }

        describe("신규 등록이 되어 있을 때 ") {
            val email = "aaa@example.com"
            lateinit var key : CertifyKeyReq
            beforeEach {
                emailAuthenticationSvc.register(RegisterEmailReq(email))
                val keyStr = emailAuthKeyRepo.findAll().toList()[0].key
                key = CertifyKeyReq(keyStr)
            }
            context("key 인증 수행을") {
                it("성공 시 인증 성공 목록에 추가한다.") {
                    emailAuthenticationSvc.authenticate(key)
                    authenticationEmailRepo.findAll().map { it.email }.shouldContain(email)
                }

                it("수행 시 인증 실패 시 예외를 반환한다.") {
                    shouldThrow<EmailNotAuthenticatedException> {
                        emailAuthenticationSvc.authenticate(CertifyKeyReq("asdf"))
                    }
                }
            }

            context("인증 여부를 확인 시 ") {
                beforeEach {
                    emailAuthenticationSvc.authenticate(key)
                }
                it("존재하면 예외를 반환하지 않는다.") {
                    shouldNotThrow<EmailNotAuthenticatedException> {
                        emailAuthenticationSvc.checkAuthenticated(email)
                    }
                }

                it("존재하지 않으면 예외를 반환한다.") {
                    shouldThrow<EmailNotAuthenticatedException> {
                        emailAuthenticationSvc.checkAuthenticated("knownEmail@email.com")
                    }
                }
            }
        }




        afterEach {
            authenticationEmailRepo.deleteAll()
            emailAuthKeyRepo.deleteAll()
        }
    }

}