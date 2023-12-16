package site.qbox.qboxserver.domain.lecturebookmark.command

import io.kotest.core.annotation.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import site.qbox.qboxserver.config.WebClientDocsTest
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.lecturebookmark.command.dto.CreateLectureBookmarkReq

@WebMvcTest(LectureBookmarkCtrl::class)
@DisplayName("LectureBookmarkCtrl")
class LectureBookmarkCtrlTest : WebClientDocsTest() {
    @MockBean
    lateinit var lectureBookmarkSvc: LectureBookmarkSvc

    init {
        it("lectureBookmark 생성을 수행한다") {
            val req = CreateLectureBookmarkReq("asdf@gmail.com", LectureId("aaa", 4))

            val action = performPost("/lecture-bookmarks", req)

            action.andExpect(status().isCreated)

            action.andDo(print("create-lecture-bookmark",
                    requestFields(
                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("북마크 등록자 email"),
                        fieldWithPath("lecture.code").type(JsonFieldType.STRING).description("강의 코드"),
                        fieldWithPath("lecture.departId").type(JsonFieldType.NUMBER).description("강의 소속 학과 ID")
                    )
                ))
        }
    }
}
