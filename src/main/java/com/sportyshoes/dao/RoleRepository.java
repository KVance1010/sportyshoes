package com.sportyshoes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.model.Role;

public interface RoleRepository extends JpaRepository< Role, Integer> {

}
