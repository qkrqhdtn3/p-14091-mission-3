package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {
    @Autowired
    private QuestionRepository questionRepository;

//	@Test
//	void testJpa() {
//        Question question1 = new Question();
//        question1.setSubject("sbb가 무엇인가요");
//        question1.setContent("sbb에 대해서 알고 싶습니다");
//        question1.setCreateDate(LocalDateTime.now());
//        this.questionRepository.save(question1);
//
//        Question question2 = new Question();
//        question2.setSubject("스프링부트 모델 질문");
//        question2.setContent("id는 자동 생성 되나요?");
//        question2.setCreateDate(LocalDateTime.now());
//        this.questionRepository.save(question2);
//	}
//
//    @Test
//    void testJpa2() {
//        List<Question> all = this.questionRepository.findAll();
//        assertEquals(2, all.size());
//
//        Question q = all.get(0);
//        assertEquals("sbb가 무엇인가요", q.getSubject());
//    }
//
//    @Test
//    void testJpa3(){
//        Optional<Question> oq = this.questionRepository.findById(1);
//        if(oq.isPresent()){
//            Question q = oq.get();
//            assertEquals("sbb가 무엇인가요", q.getSubject());
//        }
//    }

    @Test
    void testJpa4() {
        Question q = this.questionRepository.findBySubject("sbtatb가 무엇인가요");
        assertEquals(1, q.getId());
    }
}
