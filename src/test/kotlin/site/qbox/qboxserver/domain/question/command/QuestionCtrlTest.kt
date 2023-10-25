package site.qbox.qboxserver.domain.question.command

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.kotest.core.spec.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.question.command.dto.CreateQuestionReq
import site.qbox.qboxserver.global.dto.IdRes


@WebMvcTest(QuestionCtrl::class)
@DisplayName("QuestionCtrl")
class QuestionCtrlTest : WebClientDocsTest() {
    @MockkBean
    private lateinit var questionSvc: QuestionSvc

    init {
        it("question 생성을 수행한다") {
            val req = CreateQuestionReq("제목", "내용", LectureId("LECCODE", 5L))
            every { questionSvc.create(req, any(String::class)) } returns IdRes(59L)

            val action = performPost("/questions", req)

            action.andExpect(status().isCreated())

            action.andDo(
                print(
                    "create-question",
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                        fieldWithPath("lecture.code").type(JsonFieldType.STRING).description("강의 코드"),
                        fieldWithPath("lecture.departId").type(JsonFieldType.NUMBER).description("강의 학과번호"),
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("질문 ID")
                    )
                )
            )
        }
    }
}