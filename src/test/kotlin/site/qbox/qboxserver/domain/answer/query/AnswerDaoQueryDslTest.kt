package site.qbox.qboxserver.domain.answer.query

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.answer.command.AnswerCommentRepo
import site.qbox.qboxserver.domain.answer.command.AnswerRepo
import site.qbox.qboxserver.domain.answer.command.entity.Answer
import site.qbox.qboxserver.domain.answer.command.entity.AnswerComment
import site.qbox.qboxserver.domain.answer.command.entity.AnswerId
import site.qbox.qboxserver.domain.answer.command.entity.QAnswer.*
import site.qbox.qboxserver.domain.answer.command.entity.QAnswerComment.answerComment
import site.qbox.qboxserver.domain.answer.query.dto.QAnswerCommentRes
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.member.command.entity.QMember.member
import site.qbox.qboxserver.domain.member.query.dto.QMemberRes

@DisplayName("querydsl-answer")
@SpringBootTest
class AnswerDaoQueryDslTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @Autowired
    lateinit var answerRepo: AnswerRepo

    @Autowired
    lateinit var answerCommentRepo: AnswerCommentRepo

    @Autowired
    lateinit var memberRepo: MemberRepo

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    lateinit var members: List<Member>
    lateinit var answers: List<Answer>
    lateinit var answerComments: List<AnswerComment>

    init {
        describe("querydsl 에서") {
            beforeEach {
                members = memberRepo.saveAll(
                    listOf(
                        Member("aaa@ccc.ac.kr", "n1", "p1", passwordEncoder),
                        Member("bbb@ccc.ac.kr", "n2", "p2", passwordEncoder),
                        Member("fff@ccc.ac.kr", "n3", "p4", passwordEncoder),
                        Member("ggg@ccc.ac.kr", "n4", "32", passwordEncoder),
                        Member("hhh@ccc.ac.kr", "n5", "42", passwordEncoder),
                    )
                )

                answers = answerRepo.saveAll(
                    listOf(
                        Answer("내용1", AnswerId(1, members[0].email)),
                        Answer("내용2", AnswerId(1, members[1].email)),
                        Answer("내용3", AnswerId(1, members[2].email)),
                        Answer("내용4", AnswerId(1, members[3].email)),
                        Answer("내용5", AnswerId(1, members[4].email)),
                        Answer("내용6", AnswerId(2, members[0].email)),
                        Answer("내용7", AnswerId(2, members[1].email)),
                        Answer("내용8", AnswerId(2, members[2].email)),
                    )
                )

                answerComments = answerCommentRepo.saveAll(
                    listOf(
                        AnswerComment("댓글1", members[0].email, answers[1].id),
                        AnswerComment("댓글2", members[1].email, answers[1].id),
                        AnswerComment("댓글3", members[1].email, answers[1].id),
                        AnswerComment("댓글4", members[2].email, answers[1].id),
                        AnswerComment("댓글5", members[1].email, answers[2].id),
                        AnswerComment("댓글6", members[3].email, answers[2].id),
                        AnswerComment("댓글7", members[3].email, answers[7].id),
                    )
                )
            }

            it("question별 answer comment 조회를 수행한다.") {
                val result = queryFactory.from(answerComment)
                    .where(answerComment.answer.questionId.eq(1))
                    .join(member).on(answerComment.commentWriterId.eq(member.email))
                    .transform(
                        groupBy(answerComment.answer).`as`(list(
                            QAnswerCommentRes(
                                answerComment.id,
                                answerComment.content,
                                QMemberRes(member.email, member.nickname)
                            )
                        ))
                    )

                result[answers[1].id]?.size shouldBe 4
                result[answers[1].id]?.map { it.content }?.containsAll(listOf("댓글1", "댓글2","댓글3", "댓글4"))
                result[answers[1].id]?.map { it.writer.nickname }?.containsAll(listOf("n1", "n2","n3", "n4"))
            }
        }


        afterEach {
            memberRepo.deleteAll()
            answerRepo.deleteAll()
            answerCommentRepo.deleteAll()
        }
    }


}
