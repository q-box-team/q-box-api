package site.qbox.qboxserver.domain.question.query

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.DisplayName
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.lecture.query.dto.LectureRes
import site.qbox.qboxserver.domain.member.query.dto.MemberSummary
import site.qbox.qboxserver.domain.question.query.dto.QuestionRes
import site.qbox.qboxserver.domain.question.query.dto.QuestionSummary

@WebMvcTest(QuestionQueryCtrl::class)
@DisplayName("QuestionQueryCtrl")
class QuestionQueryCtrlTest : WebClientDocsTest() {
    @MockkBean
    lateinit var questionDao: QuestionDao

    init {
        it("Lecture를 통한 목록 조회를 수행한다") {
            val code = "code"
            val depart = 5L
            val writer = MemberSummary("aaa@bb.com", "닉넴")
            every { questionDao.findAllByLecture(code, depart, any()) } returns
                    listOf(
                        QuestionSummary(1, "제목1", writer),
                        QuestionSummary(2, "제목2", writer),
                        QuestionSummary(3, "제목3", writer),
                        QuestionSummary(4, "제목4", writer),
                    )
            val params = LinkedMultiValueMap<String, String>()
            params["code"] = code
            params["depart"] = depart.toString()
            params["size"] = "100"
            params["page"] = "0"

            val action = performGet("/questions", params)

            action.andExpect(status().isOk())

            action.andDo(
                print(
                    "find-all-questions",
                    queryParameters(
                        parameterWithName("code").description("강의 코드"),
                        parameterWithName("depart").description("강의 해당 학과 ID"),
                        parameterWithName("page").description("page 번호"),
                        parameterWithName("size").description("페이지 당 보여줄 컨텐츠 수(default: 10)"),
                    ),
                    responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("ID"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("[].writer.email").type(JsonFieldType.STRING).description("작성자 email"),
                        fieldWithPath("[].writer.nickname").type(JsonFieldType.STRING).description("작성자 닉네임"),
                    )
                )

            )
        }

        it("id를 통한 단일 조회를 수행한다.") {
            val id = 5L
            every { questionDao.getBy(id) } returns QuestionRes(
                id,
                "제목",
                "내용",
                LectureRes("과목명", "과목코드", 4),
                MemberSummary("aaa@bb.com", "닉넴")
            )

            val action = performGet("/questions/{id}", id.toString())

            action.andExpect(status().isOk)

            action.andDo(print(
                "find-questions-by-id",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("body").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("lecture.name").type(JsonFieldType.STRING).description("강의명"),
                    fieldWithPath("lecture.code").type(JsonFieldType.STRING).description("강의 코드"),
                    fieldWithPath("lecture.departId").type(JsonFieldType.NUMBER).description("해당 강의 학과 ID"),
                    fieldWithPath("writer.email").type(JsonFieldType.STRING).description("작성자 이메일"),
                    fieldWithPath("writer.nickname").type(JsonFieldType.STRING).description("작성지 닉네임"),
                )
            ))
        }

    }

}
