package site.qbox.qboxserver.domain.member.query

import site.qbox.qboxserver.global.exception.NotFoundException

class MemberNotFoundException :
    NotFoundException("member를 찾을 수 없습니다.")