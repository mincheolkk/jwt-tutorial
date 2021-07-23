package me.prac.tutorial.repository;

import me.prac.tutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    // @EntityGraph 는 쿼리가 수행이 될 때, Lazy 조회가 아니고 Eager 조회로 authorities 정보를 같이 가져옴.
    // findOneWithAuthoritiesByUsername 메소드는 username 을 기준으로 User 정보를 가져올 때, 권한 정보도 같이 가져옴.
}
