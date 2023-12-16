package site.qbox.qboxserver.domain.lecturebookmark.command

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.lecturebookmark.command.dto.CreateLectureBookmarkReq

@SpringBootTest
@DisplayName("LectureBookmarkSvc")
class LectureBookmarkSvcTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var lectureBookmarkRepo: LectureBookmarkRepo

    @Autowired
    lateinit var lectureBookmarkSvc: LectureBookmarkSvc

    init {
        it("등록을 수행한다") {
            val req = CreateLectureBookmarkReq("asf@gamil.com", LectureId("leCode", 123))

            lectureBookmarkSvc.register(req)

            lectureBookmarkRepo.findAll().size shouldBe 1
            lectureBookmarkRepo.findAll().get(0).id.memberId shouldBe req.memberId
            lectureBookmarkRepo.findAll().get(0).id.lectureId shouldBe req.lecture
        }
    }
}