package am.itspace.cinemamodularcommon.entity.cinemadetail;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.userdetail.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class BoxOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = ("HH-mm-ss"))
    private LocalTime timeSince;
    private double price;
    @ManyToOne
    private User user;
    @ManyToOne
    private Cinema cinema;
    @ManyToOne
    private Film film;
    private int seatingNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
