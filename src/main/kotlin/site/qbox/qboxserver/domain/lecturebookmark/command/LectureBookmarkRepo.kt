package site.qbox.qboxserver.domain.lecturebookmark.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.lecturebookmark.command.entity.LectureBookmark
import site.qbox.qboxserver.domain.lecturebookmark.command.entity.LectureBookmarkId

interface LectureBookmarkRepo : JpaRepository<LectureBookmark, LectureBookmarkId>