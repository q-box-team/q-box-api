package site.qbox.qboxserver.domain.lecturebookmark.query

import com.querydsl.core.group.GroupBy.list
import com.querydsl.jpa.impl.JPAQueryFactory
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
        return queryFactory.select(
            QLectureBookmarkRes(
                lectureBookmark.id.memberId,
                list(
                    QLectureRes(
                        lecture.name,
                        lecture.id.code,
                        lecture.id.departId)
                )))
            .from(lectureBookmark)
            .join(lecture).on(lectureBookmark.id.lectureId.eq(lecture.id))
            .groupBy(lectureBookmark.id.memberId)
            .having(lectureBookmark.id.memberId.eq(memberId))
            .fetchFirst()


    }
}