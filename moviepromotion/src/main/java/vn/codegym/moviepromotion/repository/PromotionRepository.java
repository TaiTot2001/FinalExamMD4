package vn.codegym.moviepromotion.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.codegym.moviepromotion.model.Promotion;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("SELECT p FROM Promotion p WHERE " +
            "(:discount IS NULL OR p.discountAmount = :discount) AND " +
            "(" +
            "   (:start IS NOT NULL AND :end IS NOT NULL AND p.startDate >= :start AND p.endDate <= :end) OR " +
            "   (:start IS NOT NULL AND :end IS NULL AND p.startDate = :start) OR " +
            "   (:start IS NULL AND :end IS NOT NULL AND p.endDate = :end) OR " +
            "   (:start IS NULL AND :end IS NULL) " +
            ")")
    List<Promotion> search(@Param("discount") Double discount,
                           @Param("start") LocalDate start,
                           @Param("end") LocalDate end);
}
