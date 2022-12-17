package am.itspace.cinemamodularcommon.entity.userdetail;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "favorite_movies")
public class FavoriteMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Film film;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FavoriteMovies favoriteMovies = (FavoriteMovies) o;
        return Objects.equals(id, favoriteMovies.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
