package site.qbox.qboxserver.domain.member.query

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import site.qbox.qboxserver.domain.depart.command.entity.QDepart.depart
import site.qbox.qboxserver.domain.depart.query.dto.QDepartRes
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.member.query.dto.MemberAuthRes
import site.qbox.qboxserver.domain.member.query.dto.MemberRes
import site.qbox.qboxserver.domain.member.query.dto.QMemberRes
import site.qbox.qboxserver.global.exception.NotFoundException

@Service
class MemberDao(
    private val queryFactory: JPAQueryFactory,
) {
    fun getByEmail(email: String): MemberRes {
        return queryFactory.select(
            QMemberRes(
                MemberQuery.id,
                member.nickname,
                QDepartRes(depart.id, depart.name, depart.univId)
            )
        )
            .from(member)
            .join(depart).on(member.departId.eq(depart.id))
            .where(MemberQuery.id.eq(email))
            .fetchOne() ?: throw MemberNotFoundException()
    }

    fun getAuth(email: String): MemberAuthRes =
        MemberAuthRes(getEntity(email))

    private fun getEntity(id: String): Member =
        queryFactory.selectFrom(member).where(MemberQuery.id.eq(id)).fetchFirst()
}