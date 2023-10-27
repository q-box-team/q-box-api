package site.qbox.qboxserver.domain.lecture.command

import io.kotest.core.spec.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.lecture.command.dto.CreateLectureReq

@WebMvcTest(LectureCtrl::class)
@DisplayName("LectureCtrl")
class LectureCtrlTest : WebClientDocsTest() {

    @MockBean
    private lateinit var lectureSvc: LectureSvc

    init {
        it("lecture 생성을 수행한다") {
            val req = CreateLectureReq("ABC123", "자료구조", 4)

            val action = performPost("/lectures", req)

            action.andExpect(status().isCreated())

            action.andDo(
                print(
                    "create-lecture",
                    requestFields(
                        fieldWithPath("code").type(JsonFieldType.STRING)
                            .description("과목 코드"),
                        fieldWithPath("name").type(JsonFieldType.STRING)
                            .description("과목 이름"),
                        fieldWithPath("departId").type(JsonFieldType.NUMBER)
                            .description("학과 ID"),
                    )
                )
            )
        }
    }

}
