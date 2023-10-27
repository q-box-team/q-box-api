package site.qbox.qboxserver.domain.depart.command

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.kotest.core.spec.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.depart.command.dto.CreateDepartReq
import site.qbox.qboxserver.global.dto.IdRes


@WebMvcTest(DepartCtrl::class)
@DisplayName("DepartCtrl")
class DepartCtrlTest : WebClientDocsTest() {

    @MockkBean
    private lateinit var departSvc: DepartSvc

    init {
        it("depart 생성을 수행한다") {
            val req = CreateDepartReq("컴퓨터공학과", "test.ac.kr")
            every { departSvc.create(req) } returns IdRes(4L)

            val action = performPost("/departs", req)

            action.andExpect(status().isCreated())

            action.andDo(
                print(
                    "create-depart",
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING)
                            .description("학과 이름"),
                        fieldWithPath("univId").type(JsonFieldType.STRING)
                            .description("대학교 ID(email domain)"),
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("학과 ID"),
                    )
                )
            )
        }
    }

}
