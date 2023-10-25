package site.qbox.qboxserver.domain.depart.query

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.kotest.core.spec.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.depart.query.dto.DepartRes

@WebMvcTest(DepartQueryCtrl::class)
@DisplayName("DepartQueryCtrl")
class DepartQueryCtrlTest : WebClientDocsTest() {

    @MockkBean
    lateinit var departDao: DepartDao

    init {
        it("univ 별 depart 조회를 수행한다") {
            val univ = "aaa.ac.kr"
            every { departDao.getAllByGroup(any()) } returns
                    listOf(
                        DepartRes(1, "경영학과", univ),
                        DepartRes(1, "디자인학과", univ),
                        DepartRes(1, "컴퓨터공학과", univ),
                    )

            val action = performGet("/departs")

            action.andExpect(status().isOk())

            action.andDo(
                print(
                    "find-all-departs",
                    responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("학과 ID"),
                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("학과 이름"),
                        fieldWithPath("[].univId").type(JsonFieldType.STRING).description("학과 소속 대학 ID"),
                    )
                )
            )
        }
    }
}
