package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.domain.account.repository.AccountRepository
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.domain.account.util.AccountPolicyValidator
import gg.dak.board_api.domain.account.util.AccountProcessor
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val accountPolicyValidator: AccountPolicyValidator,
    private val accountProcessor: AccountProcessor,
    private val accountConverter: AccountConverter,
    private val accountRepository: AccountRepository,
): AccountService {
    override fun register(dto: AccountDto): AccountDto =
        accountPolicyValidator.validate(OperationType.REGISTER, dto) //회원가입 정책을 검사합니다.
            .let { accountProcessor.process(OperationType.REGISTER, dto) } //인자로 받은 정보에 대한 전처리 과정을 실행합니다.
            .let { accountConverter.toEntity(it) }
            .let { accountRepository.save(it) }
            .let { accountConverter.toDto(it) }
}