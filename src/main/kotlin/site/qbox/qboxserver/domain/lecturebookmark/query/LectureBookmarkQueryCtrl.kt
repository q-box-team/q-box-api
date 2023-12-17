package site.qbox.qboxserver.domain.lecturebookmark.query

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.qbox.qboxserver.domain.lecturebookmark.query.dto.LectureBookmarkRes

@RestController
@RequestMapping("lecture-bookmarks")
class LectureBookmarkQueryCtrl (
    private val lectureBookmarkDao: LectureBookmarkDao
) {
    @GetMapping("me")
    fun getByMe(auth: Authentication) : LectureBookmarkRes =
        lectureBookmarkDao.findAllByMemberId(auth.name)
}