package com.example.demo;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.entity.QMember;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.repository.MemberRepository;
import com.example.demo.members.service.MemberService;
import com.example.demo.todos.domain.dto.TodoCondition;
import com.example.demo.todos.domain.entity.QTodo;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.repository.TodoRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class DemoApplicationTests {



	@Test
	void contextLoads() {
	}

	@Test
	void test(){
		QMember member = new QMember("member");
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		// 쿼리를 자바 문법으로 쓸 수 있다는 장점이 있다.

		//
		String name = null;
		BooleanExpression nameEq = name !=null ? member.name.eq(name) : null;

		Integer age= 20;
		BooleanExpression ageloe = age ==null ? null : member.age.loe(20);

		member.name.like("%" + name + "%");


		JPAQuery<Member> from = queryFactory
				.select(member)
				.from(member)
				.where(nameEq.and(ageloe));
//				.where(nameEq, ageloe); // 이렇게 해도됨


		List<Member> fetch = from.fetch();// fetch 를 해줘야함
		System.out.println();
		NumberExpression<Long> count = member.count();
	}

	@Test
	void test2(){
		QMember member = new QMember("member");
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		JPAQuery<Member> na = queryFactory.select(member)
				.from(member)
				.innerJoin(member.todos)
				.fetchJoin()
				.where(member.age.gt(5),
						member.age.loe(10),
						member.name.ne("name"))
				.offset(0)// 이런식으로 페이지 만들어줘야함
				.limit(20)
				.orderBy(member.age.desc()); // Order by 도 할 수 있음




		queryFactory.select(member.count()).from(member); // 이렇게 전체 글 가져와야함

		List<Member> fetch = na.fetch();
		System.out.println();

	}

	@Test
	void test3(){
		QMember member = new QMember("member");
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		List<String> 님 = queryFactory
				.select(member.name.concat("님").concat(member.age.stringValue()))
				.from(member)
				.fetch();

	}
	@Test
	void test4(){
		QMember member = new QMember("member");
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		Member member1 = entityManager.find(Member.class, this.member.getId());

		Member member2 = memberRepository.findById(this.member.getId()).get();
		Member member3 = queryFactory.select(member).from(member).where(member.id.eq(this.member.getId())).fetchFirst();

		System.out.println();
		Assertions.assertEquals(member1,member2);
		Assertions.assertEquals(member1,member3);
		Assertions.assertEquals(member3,member2); //JPA 의 장점. 데이터 베이스를 덜 들린다.
		Assertions.assertNotEquals(member1,this.member);
	}

	@Test
	void test5(){
		//작성자 이름이 name이고,좋아요를 10개 이상 받았고,내용에 t가 들어간 게시물
		QTodo todo = new QTodo("todo");
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		JPAQuery<Todo> where = queryFactory
				.select(todo)
				.from(todo)
				.where(todo.member.name.eq("name"), todo.likeCount.goe(10), todo.content.contains("t"));

		List<Todo> todos = where.fetch();
		System.out.println(todos.size());
		System.out.println();
	}
	@Test
	void test6(){
		//작성자 이름이 name이고,좋아요를 10개 이상 받았고,내용에 t가 들어간 게시물
		QMember qMember = new QMember("member");
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QTodo qTodo  =QTodo.todo;
		List<Member> fetch = queryFactory
				.select(qMember)
				.from(qMember)
				.leftJoin(qMember.todos,qTodo)
				.fetchJoin()
				.where(qMember.name.eq("name"),
						qTodo.content.contains("t"),
						qTodo.likeCount.goe(10))
				.fetch();
		Assertions.assertEquals(fetch.get(0).getTodos().size(),30);
		System.out.println(fetch.size());
		System.out.println();
	}

	@Test
	void test7(){
		QMember qMember = QMember.member;
//select case when ~~ then ~~
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		queryFactory.select(qMember.age.sum(),qMember.age.avg()).from(qMember);

		queryFactory
				.select(qMember)
				.from(qMember)
				.where(qMember.id.eq(JPAExpressions
						.select(QTodo.todo.member.id)
						.from(QTodo.todo)
						.where(QTodo.todo.content.contains("t"))));

		queryFactory
				.select(new CaseBuilder()
						.when(qMember.age.between(10,20))
						.then("10대")
						.otherwise("노인"))
				.from(qMember).fetchOne();
	}

	QMember qMember = QMember.member;
	QTodo qTodo = QTodo.todo;
	@Test
	void test10(){
		// init data , given
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		TodoCondition condition = TodoCondition.builder()
				.build();
		PageRequest request = PageRequest.of(0,20);


		JPAQuery<Todo> limit = queryFactory
				.select(qTodo)
				.from(qTodo)
				.leftJoin(qTodo.member, qMember)
				.fetchJoin()
				.where(
						contentContains(condition.getContent()),
						titleEq(condition.getTitle())
				)
				.offset(request.getPageNumber())
				.limit(request.getPageSize());
		List<Todo> fetch = limit.fetch();
		Assertions.assertEquals(fetch.size(),20);
	}

	private BooleanExpression contentContains(String content) {
		return content == null
				? null
				: qTodo.content.contains(content);
	}

	private BooleanExpression titleEq(String title) {
		return title == null
				? null
				: qTodo.title.eq(title);
	}


	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TodoRepository todoRepository;
	@Autowired
	MemberLoginRepository memberLoginRepository;
	String email = "1111";
	String password = "1234";
	Member member;
	@Autowired
	EntityManager entityManager;
	@Autowired
	MemberService memberService;
	String token;
	Todo todo;
	@BeforeEach
	void init(){
		Member member =
				new Member(null, email, password
						, "name", 10, new ArrayList<>(), null);

		this.member = memberRepository.save(member);
		this.todo = todoRepository.save(
				new Todo(null, "a", "a"
						, false, 0, member,null)
		);
		for (int i = 0; i < 40; i++) {
			todoRepository.save(
					new Todo(null, "t" + i,"t" + i
							, false, i, member,null)
			);
		}

		MemberLogin entity = new MemberLogin(this.member, LocalDateTime.now());
		memberLoginRepository.save(entity);
		entityManager.flush();
		entityManager.clear();
		LoginResponse login = memberService.login(new LoginRequest(email, password));
		token = login.token();

	}
	@AfterEach
	void clean(){
		todoRepository.deleteAll();
		memberLoginRepository.deleteAll();
		memberRepository.deleteAll();
	}


}
