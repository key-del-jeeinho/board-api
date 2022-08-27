package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.domain.account.util.AccountPolicyValidator
import gg.dak.board_api.global.common.exception.PolicyValidationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountPolicyValidatorImpl(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
): AccountPolicyValidator {
    //계정 도메인에 관련된 정책들을 검사합니다.
    override fun validate(operation: OperationType, dto: AccountDto) =
        when(operation) {
            //수행할 작업이 "회원가입"일 경우, 이에대한 정책을 검증합니다.
            OperationType.REGISTER -> validateRegisterNickname(dto.nickname)
            OperationType.LOGIN -> validateLoginAuthInfo(dto.id, dto.password)
        }

    //유저의 닉네임은 2~5자 사이여야합니다.
    private fun validateRegisterNickname(nickname: String) {
        //Trade-off
        //부등호를 사용하면 가독성을 떨어져도 연산속도가 빨라진다
        //소규모 사용자를 대상으로 하는 시스템이므로 가독성을 우선으로한다.
        if((2..5).contains(nickname.length)) return
        else throw PolicyValidationException("회원가입 정책을 위반하였습니다!", "닉네임은 2-5자 사이여야합니다.")
    }

    //인증정보의 id를 소유한 계정이 존재해야합니다.
    //인증정보의 password가 id를 소유한 계정의 암호와 동일해야합니다.
    private fun validateLoginAuthInfo(id: String, password: String) {
        accountRepository.findById(id).let {
            if(it.isPresent) it.get()
            else throw PolicyValidationException("로그인 정책을 위반하였습니다!", "존재하지 않는 ID입니다.")
        }.let {
            passwordEncoder.matches(password, it.encodedPassword)
        }.let { isMatch ->
            if(isMatch) return
            else throw PolicyValidationException("로그인 정책을 위반하였습니다!", "잘못된 비밀번호입니다.")
        }
    }
}