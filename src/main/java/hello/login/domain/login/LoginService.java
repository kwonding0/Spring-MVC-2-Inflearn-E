package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /*
    * @return null 로그인 실패
    * */
    public Member login(String loginId, String password){
        //V1
        /*Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get();

        if(member.getPassword().equals(password)){
            return member;
        }else {
            return null;
        }*/

        //V2
        return memberRepository.findByLoginId(loginId)
                .filter(o -> o.getPassword().equals(password))
                .orElse(null);
    }
}
