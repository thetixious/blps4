package org.tix.lab4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tix.lab4.model.DebitOffer;

import java.util.Optional;

@Repository
public interface DebitRepository extends JpaRepository<DebitOffer, Long> {

    @Query("SELECT do FROM DebitOffer do WHERE do.user_id = :id")
    Optional<DebitOffer> findByUserId(@Param("id") Long id);
}
