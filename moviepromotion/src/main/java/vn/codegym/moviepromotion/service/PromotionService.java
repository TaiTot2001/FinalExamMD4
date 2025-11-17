package vn.codegym.moviepromotion.service;

import org.springframework.stereotype.Service;
import vn.codegym.moviepromotion.model.Promotion;
import vn.codegym.moviepromotion.repository.PromotionRepository;

import java.util.List;

@Service
public class PromotionService {
    private final PromotionRepository repo;

    public PromotionService(PromotionRepository repo) {
        this.repo = repo;
    }

    public List<Promotion> findAll() {
        return repo.findAll();
    }

    public List<Promotion> search(Double discount, java.time.LocalDate start, java.time.LocalDate end) {
        return repo.search(discount, start, end);
    }

    public Promotion save(Promotion promotion) {
        return repo.save(promotion);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Promotion findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

}