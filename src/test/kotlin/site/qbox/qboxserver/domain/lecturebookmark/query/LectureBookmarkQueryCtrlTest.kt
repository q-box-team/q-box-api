package site.qbox.qboxserver.domain.lecturebookmark.query

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.DisplayName
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.lecture.query.dto.LectureRes
import site.qbox.qboxserver.domain.lecturebookmark.query.dto.LectureBookmarkRes

@WebMvcTest(LectureBookmarkQueryCtrl::class)
@DisplayName("LectureBookmarkQueryCtrl")
class LectureBookmarkQueryCtrlTest : WebClientDocsTest() {
    @MockkBean
    lateinit var lectureBookmarkDao: LectureBookmarkDao

    init {

        it("로그인한 사람의 lecture bookmark를 조회한다.") {
            val res = LectureBookmarkRes(
                "kkk@gg.com", listOf(
                    LectureRes("소프트웨어공학", "aaaa", 4),
                    LectureRes("디자인원리", "bbbb", 5),
                    LectureRes("C언어기초", "cccc", 6),
                ))

            every { lectureBookmarkDao.findAllByMemberId(any(String::class)) } returns res

            val action = performGet("/lecture-bookmarks/me")

            action.andExpect(status().isOk)

            action.andDo(
                print(
                    "find-all-lecture-bookmarks-by-me",
                    responseFields(
                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("조회자 ID"),
                        fieldWithPath("lectures.[].name").type(JsonFieldType.STRING).description("강의명"),
                        fieldWithPath("lectures.[].code").type(JsonFieldType.STRING).description("강의코드"),
                        fieldWithPath("lectures.[].departId").type(JsonFieldType.NUMBER).description("강의 소속 학과 ID"),
                    )
                )
            )

        }
    }
}
