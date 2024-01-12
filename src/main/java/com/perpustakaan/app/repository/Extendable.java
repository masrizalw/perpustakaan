package com.perpustakaan.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Extendable<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	List<T> findAll(Specification<T> specs, Sort sort);

	Page<T> findAll(Specification<T> specs, Pageable page);

}
