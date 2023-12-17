package site.qbox.qboxserver.domain.lecturebookmark.query

import org.springframework.boot.test.mock.mockito.MockBean
import site.qbox.qboxserver.config.WebClientDocsTest

class LectureBookmarkQueryCtrlTest : WebClientDocsTest() {
    @MockBean
    lateinit var lectureBookmarkDao: LectureBookmarkDao

    init {
        beforeEach {
            
        }

        it("로그인한 사람의 lecture bookmark를 조회한다.") {

        }
    }
}
