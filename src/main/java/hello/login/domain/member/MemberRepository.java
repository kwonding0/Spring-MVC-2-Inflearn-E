package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Sequence;
import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //인터페이스로 만들면 더 좋겠지만 지금은 그게 핵심이 아니기 때문에 일단 static 사용
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save : member{}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    //Java8에서는 Optional<T> 클래스를 사용해 NullPointError를 방지할 수 있도록 도와줌
    //Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 참조하더라도 NPE가 발생하지 않도록 도와줌
    public Optional<Member> findByLoginId(String loginId){
        /*List<Member> all = findAll();
        for(Member m : all){
            if(m.getLoginId().equals(loginId)){
                //return m;
                return Optional.of(m);
            }
        }
        //return null;
        return Optional.empty();*/

        return findAll().stream().filter(m -> m.getLoginId().equals(loginId)).findFirst();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
