package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final EntityManager entityManager;

    private Specification<Question> search(String kw) {
        return new Specification<>() {
          private static final long serialVersionUID = 1L;
          public Predicate toPredicate(Root<Question> q,
                                       CriteriaQuery<?> query,
                                       CriteriaBuilder cb){
              query.distinct(true); // 중복 제거
              Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
              Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
              Join<Question, SiteUser> u2 = a.join("author", JoinType.LEFT);
              return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                      cb.like(q.get("content"), "%" + kw + "%"), // 내용
                      cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
                      cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
                      cb.like(u2.get("username"), "%" + kw + "%") // 답변 작성자
              );
          }
        };
    }

    public List<Question> getList(){
        return questionRepository.findAll();
    }

    public Question getQuestion(Integer id){
        Optional<Question> oq = this.questionRepository.findById(id);
        if(oq.isPresent()){
            return oq.get();
        }
        else{
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser siteUser){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(siteUser);
        this.questionRepository.save(q);
    }

    @Transactional
    public void createBatch(List<Question> questions){
        final int batchSize = 50;

        for(int i = 0 ; i < questions.size() ; i++){
            questionRepository.save(questions.get(i));

            if(i % batchSize == 0 && i > 0){
                questionRepository.flush();
                entityManager.clear();
            }
        }

        questionRepository.flush();
        entityManager.clear();
    }

    public Page<Question> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
//        return this.questionRepository.findAll(spec, pageable);
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
