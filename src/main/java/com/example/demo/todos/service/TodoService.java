package com.example.demo.todos.service;

import com.example.demo.config.repository.LikeRepository;
import com.example.demo.config.service.MemberLoginService;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.dto.TodoCondition;
import com.example.demo.todos.domain.entity.Likes;
import com.example.demo.todos.domain.entity.QTodo;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.domain.request.UpdateRequest;
import com.example.demo.todos.domain.response.TodoResponse;
import com.example.demo.todos.repository.TodoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberLoginService memberLoginService;
    private final LikeRepository likeRepository;

    private final EntityManager entityManager;

    public void insert(Long id,TodoRequest request){

        todoRepository.save(request.toEntity(new Member(id,null,null,null,null,null,null)));

    }

    public void update(Long id, UpdateRequest updateRequest){

        Todo byId = findById(id);

        byId.toUpdate(updateRequest.getTitle(), updateRequest.getContent(), updateRequest.isDone());

    }

    public void delete(Long id){

        Todo byId1 = findById(id);
        todoRepository.delete(byId1);
    }

    public Todo findById(Long id){

        return todoRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void like(Long tId, Long mId){
        Optional<Todo> byId = todoRepository.findById(tId);

        Todo todo = byId.orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        Likes likes = new Likes(null, mId, todo);

        likeRepository.save(likes);

    }

    public void check(Long id,Long memberId){
        Optional<Todo> byId = todoRepository.findById(id);

        byId.orElseThrow(()->new RuntimeException("TODOS_NOT_FOUND"));
        byId.get().setDone(true);
    }

    public Page<TodoResponse> findAllBy(String title, String Content,boolean isDone, PageRequest pageRequest){


        Page<Todo> allByTitleAndContent = todoRepository.findAllByTitleAndContent("%"+ title+"%", "%"+Content+"%", pageRequest);

        return  allByTitleAndContent.map(TodoResponse::new);
    }

    public List<TodoResponse> test(String title, String Content,Boolean isDone,Integer likeGoe,Integer likeLoe,Integer page,Integer size){
        QTodo qTodo= new QTodo("todo");
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        BooleanExpression isDoneEx = isDone != null ? qTodo.isDone.eq(isDone) : null;

        BooleanExpression containsEx = Content != null ? qTodo.content.contains(Content):null;

        BooleanExpression titleEx = title != null ? qTodo.title.eq(title) : null;



        JPAQuery<Todo> todoJPAQuery = jpaQueryFactory
                .select(qTodo)
                .from(qTodo)
                .where(titleEx,
                        containsEx,
                        isDoneEx,
                        qTodo.likeCount.loe(likeLoe),
                        qTodo.likeCount.goe(likeGoe))
                .offset(page)
                .limit(size);


        List<Todo> todos = todoJPAQuery.fetch();

        return  todos.stream().map(TodoResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getAll(PageRequest pageRequest, TodoCondition condition){
        List<Todo> allByCondition = todoRepository.findAllByCondition(pageRequest, condition);

        return allByCondition.stream().map(TodoResponse::new).toList();
    }








}
