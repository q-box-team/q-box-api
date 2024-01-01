package site.qbox.qboxserver.domain.lecturebookmark.query

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.lecture.command.LectureRepo
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.lecturebookmark.command.LectureBookmarkRepo
import site.qbox.qboxserver.domain.lecturebookmark.command.entity.LectureBookmark

@SpringBootTest
@DisplayName("LectureBookmarkDao")
class LectureBookmarkDaoTest : DescribeSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var lectureBookmarkDao: LectureBookmarkDao

    @Autowired
    lateinit var lectureBookmarkRepo: LectureBookmarkRepo

    @Autowired
    lateinit var lectureRepo: LectureRepo

    init {
        beforeEach {
            val lectureIds = listOf(
                LectureId("111", 1),
                LectureId("222", 2),
                LectureId("333", 3),
                LectureId("444", 4),
                LectureId("555", 5),
                LectureId("666", 5),
            )
            lectureRepo.saveAll(
                listOf(
                    Lecture(lectureIds[0], "디자인기초"),
                    Lecture(lectureIds[1], "한국사기초"),
                    Lecture(lectureIds[2], "수학기초"),
                    Lecture(lectureIds[3], "영어기초"),
                    Lecture(lectureIds[4], "서양사기초"),
                    Lecture(lectureIds[5], "동양사기초"),
                ),
            )

            lectureBookmarkRepo.saveAll(
                listOf(
                    LectureBookmark("aaa", lectureIds[0]),
                    LectureBookmark("aaa", lectureIds[1]),
                    LectureBookmark("aaa", lectureIds[2]),
                    LectureBookmark("aaa", lectureIds[3]),
                    LectureBookmark("aaa", lectureIds[4]),
                    LectureBookmark("bbb", lectureIds[4]),
                    LectureBookmark("bbb", lectureIds[5]),
                    LectureBookmark("ccc", lectureIds[5]),
                )
            )
        }

        it("memberId를 통한 북마크 목록 조회를 수행한다.") {
           val result = lectureBookmarkDao.findAllByMemberId("aaa")

            result.lectures.count() shouldBe 5

        }

        it("북마크 등록을 안한 유저의 letcure list는 비어있다.") {
            val result = lectureBookmarkDao.findAllByMemberId("kkk")
            result.lectures shouldBe emptyList()
        }

    }


}
