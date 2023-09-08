package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    /*@GetMapping("/")*/
    public String home() {
        return "home";
    }

    /*@GetMapping("/")*/ //@CookieValue : typeConverting 자동으로 해줌 (쿠키의 String memberId -> Long memberId)
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if(memberId == null){
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);
        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";

    }

    /*@GetMapping("/")*/
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션 관리자에 저장된 회원 정보 조회
        Member loginMember = (Member)sessionManager.getSession(request);
        if (loginMember == null) {
            return "home";
        }
        //로그인
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /*@GetMapping("/")*/
    public String homeLoginV3(HttpServletRequest request, Model model) {

        //세션값이 없을 수도 있기 때문에 false를 해서 로그인이 실패해도, 새로운 세션이 생성되지 않게 처리
        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }
        //세션에 회원 데이터가 없으면 home
        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /*@GetMapping("/")*/
    //이미 로그인 된 사용자를 찾을 때 @SessionAttribute 사용
    public String homeLoginV3Spring(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    //이미 로그인 된 사용자를 찾을 때 @SessionAttribute 사용
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
