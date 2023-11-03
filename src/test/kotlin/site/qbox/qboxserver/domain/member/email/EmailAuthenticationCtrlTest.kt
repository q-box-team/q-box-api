package site.qbox.qboxserver.domain.member.email

import io.kotest.core.annotation.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.member.email.dto.RegisterEmailReq
import site.qbox.qboxserver.domain.member.email.svc.EmailAuthenticationSvc

@WebMvcTest(EmailAuthenticationCtrl::class)
@DisplayName("EmailAuthenticationCtrl")
class EmailAuthenticationCtrlTest : WebClientDocsTest() {
    @MockBean
    lateinit var emailAuthenticationSvc: EmailAuthenticationSvc

    init {
        it("이메일 등록을 수행한다") {
            val req = RegisterEmailReq("aaa@example.com")
            val action = performPost("/emails", req)

            action.andExpect(status().isCreated)

            action.andDo(
                print(
                    "register-email", requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                    )
                )
            )
        }

        it("이메일 인증을 수행한다") {
            val params = LinkedMultiValueMap<String, String>()
            params["key"] = "keyValue"
            val action = performGet("/emails", params)

            action.andExpect(status().isOk)

            action.andDo(
                print(
                    "authenticate-email",
                    queryParameters(
                        parameterWithName("key").description("인증 키"),
                    )
                )
            )

        }
    }
}
