package com.ngl.clone.repositories;

import com.ngl.clone.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> saveAll(Iterable<Question> questions);
    Question save(Question question);

    Question findById(Long id);

    @Modifying
    @Query("UPDATE Question q SET q.isRead = :isRead WHERE q.id = :id")
    void updateIsReadById(Long id, boolean isRead);


}
