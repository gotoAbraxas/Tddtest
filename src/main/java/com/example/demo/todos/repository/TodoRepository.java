package com.example.demo.todos.repository;

import com.example.demo.todos.domain.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("select t from Todo t where t.title like :title or t.content like :content")
    Page<Todo> findAllByTitleAndContent(
            @Param("title") String title,
            @Param("content") String content,
            PageRequest pageRequest);
}
