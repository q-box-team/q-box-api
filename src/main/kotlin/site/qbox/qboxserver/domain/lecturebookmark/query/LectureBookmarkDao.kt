package site.qbox.qboxserver.domain.lecturebookmark.query

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityNotFoundException
import site.qbox.qboxserver.domain.lecture.command.entity.QLecture.lecture
import site.qbox.qboxserver.domain.lecture.query.dto.QLectureRes
import site.qbox.qboxserver.domain.lecturebookmark.command.entity.QLectureBookmark.lectureBookmark
import site.qbox.qboxserver.domain.lecturebookmark.query.dto.LectureBookmarkRes
import site.qbox.qboxserver.domain.lecturebookmark.query.dto.QLectureBookmarkRes
import site.qbox.qboxserver.global.annotation.QueryService

@QueryService
class LectureBookmarkDao(
    private val queryFactory: JPAQueryFactory,
) {
    fun findAllByMemberId(memberId: String): LectureBookmarkRes {
        return queryFactory.from(lectureBookmark)
            .where(lectureBookmark.id.memberId.eq(memberId))
            .join(lecture).on(lectureBookmark.id.lectureId.eq(lecture.id))
            .transform(groupBy(lectureBookmark.id.memberId).`as`(
                QLectureBookmarkRes(
                    lectureBookmark.id.memberId,
                    list(
                        QLectureRes(
                            lecture.name,
                            lecture.id.code,
                            lecture.id.departId)
                    ))
            ))[memberId] ?: throw EntityNotFoundException()
    }
}