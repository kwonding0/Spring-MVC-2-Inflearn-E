package hello.login.web.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null){
            return "세션이 없습니다.";
        }

        //세션에 설정된 값들
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name = {}, value = {}", name, session.getAttribute(name)));

        //세션 데이터 출력
        log.info("sessionId={}", session.getId());
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval()); //비활성화 시킬 interval time
        log.info("creationTime={}", new Date(session.getCreationTime())); //생성시간 (기본이 long이어서 date로 변경)
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime())); //세션에 마지막으로 접근한 시간
        log.info("isNew={}", session.isNew()); //새로운 세션인지 여부
        return "세션 출력";
    }
}
