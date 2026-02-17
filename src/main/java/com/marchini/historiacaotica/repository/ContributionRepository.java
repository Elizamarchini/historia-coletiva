package com.marchini.historiacaotica.repository;

import com.marchini.historiacaotica.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findAllByOrderByDataDesc();
    void deleteAll();
}
