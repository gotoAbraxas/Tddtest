package com.example.demo.todos.repository;

import com.example.demo.members.domain.entity.QMember;
import com.example.demo.todos.domain.dto.TodoCondition;
import com.example.demo.todos.domain.entity.QTodo;
import com.example.demo.todos.domain.entity.Todo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public class CustomTodoRepositoryImpl implements CustomTodoRepository{

  

    private final JPAQueryFactory queryFactory;
    private final QTodo qTodo = QTodo.todo;
    private final QMember qMember = QMember.member;

    public CustomTodoRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public List<Todo> findAllByCondition(PageRequest request,
                                         TodoCondition condition){

        JPAQuery<Todo> todoList = queryFactory
                .select(qTodo)
                .from(qTodo)
                .leftJoin(qTodo.member, qMember) // 이 멤버는 얘다
                .fetchJoin()
                .where(contentContains(condition.getContent()),
                        titleEq(condition.getTitle()))
                .offset(request.getPageNumber())
                .limit(request.getPageSize());

        return todoList.fetch();

    }

    private BooleanExpression contentContains(String content) {
        return content == null
                ? null :
                qTodo.content.contains(content);
    }
    private BooleanExpression titleEq(String title) {
        return title == null
                ? null :
                qTodo.title.eq(title);
    }
}
