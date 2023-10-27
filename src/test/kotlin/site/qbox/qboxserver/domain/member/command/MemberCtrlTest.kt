package site.qbox.qboxserver.domain.member.command

import io.kotest.core.spec.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.member.command.dto.SignUpReq
import site.qbox.qboxserver.domain.member.command.svc.MemberSvc

@WebMvcTest(MemberCtrl::class)
@DisplayName("MemberCtrl")
class MemberCtrlTest : WebClientDocsTest() {
    @MockBean
    private lateinit var memberSvc: MemberSvc

    init {
        it("회원가입 요청을 수행한다") {
            val req = SignUpReq("aaa@bb.com", "nick")
            val action = performPost("/members", req)

            action.andExpect(status().isCreated())

            action.andDo(
                print(
                    "signup",
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    )
                )
            )
        }
    }
}