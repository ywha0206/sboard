package com.sboard.repository;

import com.sboard.entity.Terms;
import com.sboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<Terms, String> {
    @Query("select t from Terms t")
    Terms findterms();
}
