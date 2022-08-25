package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.domain.account.util.AccountPolicyValidator
import gg.dak.board_api.domain.account.util.AccountProcessor
import gg.dak.board_api.domain.account.util.LoginTokenGenerator
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val accountPolicyValidator: AccountPolicyValidator,
    private val accountProcessor: AccountProcessor,
    private val accountConverter: AccountConverter,
    private val accountRepository: AccountRepository,
    private val loginTokenGenerator: LoginTokenGenerator,
): AccountService {
    override fun register(dto: AccountDto): AccountDto =
        accountPolicyValidator.validate(OperationType.REGISTER, dto) //회원가입 정책을 검사합니다.
            .let { accountProcessor.process(OperationType.REGISTER, dto) } //인자로 받은 정보에 대한 전처리 과정을 실행합니다.
            .let { accountConverter.toEntity(it) }
            .let { accountRepository.save(it) }
            .let { accountConverter.toDto(it) }

    override fun login(dto: AccountDto): LoginTokenDto =
        accountPolicyValidator.validate(OperationType.LOGIN, dto) //로그인 정책을 검사합니다.
            .let { loginTokenGenerator.generate(dto.id) }//로그인 토큰을 발급합니다.
}