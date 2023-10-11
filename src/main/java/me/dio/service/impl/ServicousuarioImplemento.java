package me.dio.service.impl;

import me.dio.service.ServicoUsuario;
import me.dio.domain.model.User;
import me.dio.domain.repository.RepositorioUsuario;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;
@Service
public class ServicousuarioImplemento implements ServicoUsuario {
    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final RepositorioUsuario userRepository;

    public ServicousuarioImplemento(RepositorioUsuario userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User create(User userToCreate) {
        ofNullable(userToCreate).orElseThrow(() -> new BusinessException("O usuário a ser criado não deve ser nulo."));
        ofNullable(userToCreate.getConta()).orElseThrow(() -> new BusinessException("a conta a ser criado não deve ser nulo."));
        ofNullable(userToCreate.getCartao()).orElseThrow(() -> new BusinessException("o cartão a ser criado não deve ser nulo."));

        this.validateChangeableId(userToCreate.getId(), "criar");
        if (userRepository.existsByAccountNumber(userToCreate.getConta().getNumero())) {
            throw new BusinessException("A conta ja existe.");
        }
        if (userRepository.existsByCardNumber(userToCreate.getCartao().getNumero())) {
            throw new BusinessException("numero do cartão ja existe.");
        }
        return this.userRepository.save(userToCreate);
    }

    @Transactional
    public User update(Long id, User userToUpdate) {
        this.validateChangeableId(id, "atualizar");
        User dbUser = this.findById(id);
        if (!dbUser.getId().equals(userToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbUser.setNome(userToUpdate.getNome());
        dbUser.setConta(userToUpdate.getConta());
        dbUser.setCartao(userToUpdate.getCartao());
        dbUser.setFeatures(userToUpdate.getFeatures());
        dbUser.setNews(userToUpdate.getNews());

        return this.userRepository.save(dbUser);
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deletar");
        User dbUser = this.findById(id);
        this.userRepository.delete(dbUser);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("O usuário com ID %d não pode ser %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}



