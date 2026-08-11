package com.eduvault.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
    
    // Lembra da mágica do Spring Data?
    // Só de declarar esse método, o Spring vai no banco buscar um usuário pelo "username".
    // Usamos Optional porque o usuário pode não existir (evita NullPointerException).
    Optional<UserDocument> findByUsername(String username);
}
