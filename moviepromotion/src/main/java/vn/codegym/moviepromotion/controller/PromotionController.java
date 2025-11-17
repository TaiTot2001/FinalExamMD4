package vn.codegym.moviepromotion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.codegym.moviepromotion.model.Promotion;
import vn.codegym.moviepromotion.service.PromotionService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService service;

    public PromotionController(PromotionService service) {
        this.service = service;
    }

    @GetMapping
    public String listPromotions(@RequestParam(required = false) Double discount,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                 Model model) {
        List<Promotion> promotions = service.search(discount, start, end);
        model.addAttribute("promotions", promotions);
        return "promotion/list";
    }

    @GetMapping("/promotions")
    public String listPromotions(Model model) {
        List<Promotion> promotions = service.findAll(); // hoặc service tìm kiếm
        model.addAttribute("promotions", promotions);
        return "promotion/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "promotion/add";
    }

    @PostMapping("/add")
    public String addPromotion(@Valid @ModelAttribute Promotion promotion,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "promotion/add";
        }
        if (!promotion.getEndDate().isAfter(promotion.getStartDate())) {
            result.rejectValue("endDate", "error.promotion", "Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày");
            return "promotion/add";
        }
        service.save(promotion);
        redirectAttributes.addFlashAttribute("message", "Thêm khuyến mãi thành công!");
        return "redirect:/promotions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("promotion", service.findById(id));
        return "promotion/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePromotion(@PathVariable("id") Long id,
                                  @Valid @ModelAttribute Promotion promotion,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "promotion/edit";
        }
        service.save(promotion);
        redirectAttributes.addFlashAttribute("message", "Cập nhật khuyến mãi thành công!");
        return "redirect:/promotions";
    }

    @GetMapping("/delete/{id}")
    public String deletePromotion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Promotion promotion = service.findById(id);
        service.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa khuyến mãi " + promotion.getTitle() + " thành công!");
        return "redirect:/promotions";
    }
}