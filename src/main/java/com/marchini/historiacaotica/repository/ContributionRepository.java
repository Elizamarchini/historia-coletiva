package com.marchini.historiacaotica.repository;

import com.marchini.historiacaotica.model.Contribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribuicao, Long> {
    List<Contribuicao> findAllByOrderByDataAsc();
    void deleteAll();
}
