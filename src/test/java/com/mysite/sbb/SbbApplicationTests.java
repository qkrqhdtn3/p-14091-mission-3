package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
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
//
//    @Test
//    void testJpa4() {
//        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요");
//        assertEquals(1, q.getId());
//    }
//
//    @Test
//    void testJpa5(){
//        Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요", "sbb에 대해서 알고 싶습니다");
//        assertEquals(1, q.getId());
//    }
//
//    @Test
//    void testJpa6(){
//        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//        Question q = qList.get(0);
//        assertEquals("sbb가 무엇인가요", q.getSubject());
//    }
//
//    @Test
//    void testJpa7(){
//        Optional<Question> oq = this.questionRepository.findById(1);
//        assertTrue(oq.isPresent());
//        Question q = oq.get();
//        q.setSubject("수정된 제목1");
//        this.questionRepository.save(q);
//    }
//
//    @Test
//    void testJpa8(){
//        assertEquals(2, this.questionRepository.count());
//        Optional<Question> oq = this.questionRepository.findById(1);
//        assertTrue(oq.isPresent());
//        Question q = oq.get();
//        this.questionRepository.delete(q);
//        assertEquals(1, this.questionRepository.count());
//    }
//
//    @Test
//    void testJpa9(){
//        Optional<Question> oq = this.questionRepository.findById(2);
//        assertTrue(oq.isPresent());
//        Question q = oq.get();
//
//        Answer a = new Answer();
//        a.setContent("네");
//        a.setQuestion(q);
//        a.setCreateDate(LocalDateTime.now());
//        this.answerRepository.save(a);
//    }
//
//    @Test
//    void testJpa10(){
//        Optional<Answer> oa = this.answerRepository.findById(1);
//        assertTrue(oa.isPresent());
//        Answer a = oa.get();
//        assertEquals(2, a.getQuestion().getId());
//    }
//
//    @Transactional
//    @Test
//    void testJpa11(){
//        Optional<Question> oq = this.questionRepository.findById(2);
//        assertTrue(oq.isPresent());
//        Question q = oq.get();
//
//        List<Answer> answerList = q.getAnswerList();
//
//        assertEquals(1, answerList.size());
//        assertEquals("네", answerList.get(0).getContent());
//    }

    @Test
    void testJpa(){
        for(int i = 1 ; i <= 300 ; i++){
            Question question = new Question();
            String subject = String.format("테스트 데이터 : [%03d]", i);
            String content = String.format("내용 없음 : [%03d]", i);
            LocalDateTime createDate = LocalDateTime.now();
            question.setSubject(subject);
            question.setContent(content);
            question.setCreateDate(createDate);
            question.setAuthor(null);
            this.questionRepository.save(question);
        }
    }
}
