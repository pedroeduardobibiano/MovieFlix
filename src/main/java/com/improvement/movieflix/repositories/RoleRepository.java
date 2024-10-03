package com.improvement.movieflix.repositories;

import com.improvement.movieflix.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
