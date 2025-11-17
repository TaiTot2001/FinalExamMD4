package vn.codegym.moviepromotion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @Future(message = "Ngày bắt đầu phải lớn hơn ngày hiện tại")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải lớn hơn ngày bắt đầu")
    private LocalDate endDate;

    @NotNull(message = "Mức giảm giá không được để trống")
    @Min(value = 10000, message = "Mức giảm giá phải lớn hơn 10.000 VNĐ")
    private Double discountAmount;

    private String details;
}
