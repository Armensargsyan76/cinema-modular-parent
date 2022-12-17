package am.itspace.cinemamodularcommon.entity.cinemadetail;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private int spaciousness;
    private String pictureUrl;
    @ManyToMany
    @JoinTable(name = "cinema_film",
            joinColumns = {@JoinColumn(name = "cinema_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "film_id", referencedColumnName = "id")})
    private List<Film> films;
    @ManyToMany
    @JoinTable(name = "seat_cinema",
            joinColumns = {@JoinColumn(name = "cinema_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "cinema_seat_id", referencedColumnName = "id")})
    private List<CinemaSeat> seats;
    private double price;

}
