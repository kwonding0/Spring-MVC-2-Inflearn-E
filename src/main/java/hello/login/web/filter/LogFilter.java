package hello.login.web.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter 초기화");
        //Filter.super.init(filterConfig);
    }

    /*
    * ServletRequest -> HttpServletRequest의 부모,
    * ervletRequest request 는 HTTP 요청이 아닌 경우까지 고려해서 만든 인터페이스이다.
    * HTTP를 사용하면 HttpServletRequest httpRequest = (HttpServletRequest) request; 와 같이 다운 케스팅 하면 된다.
    * */
    @Override //HTTP 요청이 올때마다 doFilter가 호출됨
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try{
            log.info("REQUEST [{}{}]", uuid, requestURI);

            //filter는 서블릿 호출 전 여러개 실행될 수 있는데 chain을 안하면 다음 filter가 호출되지 않음
            //서블릿,컨트롤러가 다호출안됨
            chain.doFilter(request,response);

        }catch (Exception e){
            throw e;
        }finally{
            log.info("RESPONSE [{}{}]", uuid, requestURI);
        }


    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
        //Filter.super.destroy();
    }
}
