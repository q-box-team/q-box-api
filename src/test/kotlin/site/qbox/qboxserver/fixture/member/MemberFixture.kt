package site.qbox.qboxserver.fixture.member

import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.member.email.entity.Email

class MemberFixture {
    companion object {
        val members = listOf(
            Member(Email("aaa@bbb.com"), "n1", "p1", 1L),
            Member(Email("bbb@bbb.com"), "n2", "p2", 1L),
            Member(Email("ccc@bbb.com"), "n3", "p3", 1L),
            Member(Email("ddd@bbb.com"), "n4", "p4", 1L),
            Member(Email("eee@bbb.com"), "n5", "p5", 1L),
        )
    }

}