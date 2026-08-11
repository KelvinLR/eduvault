package com.eduvault.student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// @Repository indica que esta é uma interface de acesso a dados.
// Ao estender MongoRepository, ganhamos métodos prontos: save(), findById(), findAll(), deleteById(), etc.
// Os genéricos são <Tipo da Entidade, Tipo do ID>
@Repository
public interface StudentRepository extends MongoRepository<StudentDocument, String> {
    
    // Podemos criar queries customizadas só pelo nome do método!
    // Por exemplo, quando tivermos userId, faremos:
    // StudentDocument findByUserId(String userId);
}
