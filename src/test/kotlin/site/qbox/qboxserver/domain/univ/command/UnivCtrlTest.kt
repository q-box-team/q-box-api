package site.qbox.qboxserver.domain.univ.command

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.kotest.core.spec.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.univ.command.dto.CreateUnivReq
import site.qbox.qboxserver.global.dto.IdRes

@WebMvcTest(UnivCtrl::class)
@DisplayName("UnivCtrl")
class UnivCtrlTest : WebClientDocsTest() {

    @MockkBean
    private lateinit var univSvc: UnivSvc

    init {
        it("univ 생성을 수행한다") {
            val req = CreateUnivReq("test.ac.kr", "test대학교")
            every { univSvc.create(req) } returns IdRes(req.name)

            val action = performPost("/univ", req)

            action.andExpect(status().isCreated())

            action.andDo(
                print(
                    "create-univ",
                    requestFields(
                        fieldWithPath("emailDomain").type(JsonFieldType.STRING).description("학교 이메일 도메인"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("학교 이름"),
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.STRING).description("대학 ID (이메일 도메인)"),
                    )
                )
            )
        }
    }
}
