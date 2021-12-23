package com.ckp.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ckp.blog.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}
