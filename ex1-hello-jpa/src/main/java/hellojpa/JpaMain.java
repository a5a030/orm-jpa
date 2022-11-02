package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        //엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        //트랙젝션 획득
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            logic(em);
            et.commit();
        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(EntityManager em) {
        Member member = new Member();
        member.setId("1L");
        member.setName("hello");
        member.setAge(20);

        em.persist(member); //등록

        member.setAge(21); //수정

        //한 건 조회
        Member findMember = em.find(Member.class, "1L");
        System.out.println("findMember = "+findMember.getName()+" age = "+findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = "+members.size());

        em.remove(member); //삭제

    }
}
