package com.sportyshoes.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sportyshoes.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}