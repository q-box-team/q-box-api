package site.qbox.qboxserver.domain.depart.query

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.depart.command.DepartRepo
import site.qbox.qboxserver.domain.depart.command.entity.Depart

@SpringBootTest
@DisplayName("DepartDao")
class DepartDaoTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var departDao: DepartDao

    @Autowired
    lateinit var departRepo: DepartRepo

    init {
        context("이미 depart들이 저장되어 있을 때") {
            val targetGroup = "aaa.ac.kr"
            beforeEach {
                departRepo.saveAll(
                    listOf(
                        Depart("경영학과", targetGroup),
                        Depart("컴퓨터공학과", targetGroup),
                        Depart("디자인공학과", targetGroup),
                        Depart("전자공학과", targetGroup),

                        Depart("디자인공학과", "bbb.ac.kr"),
                        Depart("전자공학과", "bbb.ac.kr"),
                        Depart("게임공학과", "bbb.ac.kr"),
                        Depart("임베디드시스템공학과", "bbb.ac.kr"),
                        Depart("심리학과", "bbb.ac.kr"),
                    )
                )
            }

            it("univ별 조회를 수행한다") {
                val result = departDao.getAllByGroup(targetGroup)

                result.size shouldBe 4
            }
        }

        afterEach {
            departRepo.deleteAll()
        }
    }
}
