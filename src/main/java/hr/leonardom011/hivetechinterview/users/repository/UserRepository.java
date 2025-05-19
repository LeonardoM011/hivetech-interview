package hr.leonardom011.hivetechinterview.users.repository;

import hr.leonardom011.hivetechinterview.users.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByIsDeletedFalseAndUsername(String username);

    List<UserEntity> findByIsDeletedFalse();
}
