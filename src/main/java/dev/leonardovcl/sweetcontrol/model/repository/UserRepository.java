package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.leonardovcl.sweetcontrol.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);
	
}
