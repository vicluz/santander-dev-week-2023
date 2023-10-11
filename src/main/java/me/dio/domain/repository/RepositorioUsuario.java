package me.dio.domain.repository;

import me.dio.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<User, Long> {
    boolean existsByAccountNumber(String number);

    boolean existsByCardNumber(String number);
}
