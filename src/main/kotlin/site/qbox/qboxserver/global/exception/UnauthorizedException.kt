package site.qbox.qboxserver.global.exception

abstract class UnauthorizedException
    : RuntimeException {
    constructor() : super("unauthorized")
    constructor(message: String) : super(message)
}