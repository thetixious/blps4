package org.tix.lab4.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tix.lab4.model.Cards;
import org.tix.lab4.utils.Bonus;
import org.tix.lab4.utils.CardType;
import org.tix.lab4.utils.Goal;

import java.util.List;
import java.util.Set;
@Repository
public interface CardRepository extends JpaRepository<Cards,Long> {

    @Query("SELECT c FROM Cards c WHERE c.type = :type AND (c.goal = :goal OR c.bonus = :bonus)")
    Set<Cards> findAllByTypeAndGoalOrBonus(@Param("type") CardType type, @Param("goal") Goal goal, @Param("bonus") Bonus bonus);

    Set<Cards> findAllByIdIn(List<Long> cardIds);
}
