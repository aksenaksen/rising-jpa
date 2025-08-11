package com.example.demo.repository;

import com.example.demo.repository.entity.QMessage;
import com.example.demo.repository.entity.QTeam;
import com.example.demo.repository.entity.QUser;
import com.example.demo.repository.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findByName(String name) {
        return jpaQueryFactory.selectFrom(QUser.user)
                .leftJoin(QUser.user.team, QTeam.team).fetchJoin()
                .leftJoin(QUser.user.messages,QMessage.message).fetchJoin()
                .where(QUser.user.name.trim().eq(name))
                .fetch();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(QUser.user)
                        .leftJoin(QUser.user.team, QTeam.team).fetchJoin()
                        .leftJoin(QUser.user.messages, QMessage.message).fetchJoin()
                        .where(QUser.user.id.eq(id)) // id로 비교
                        .fetchOne() // 단일 결과 조회
        );
    }

    @Override
    public List<User> findAll() {
        return jpaQueryFactory.selectFrom(QUser.user)
                .leftJoin(QUser.user.team, QTeam.team).fetchJoin()
                .leftJoin(QUser.user.messages, QMessage.message).fetchJoin()
                .fetch();
    }
}
