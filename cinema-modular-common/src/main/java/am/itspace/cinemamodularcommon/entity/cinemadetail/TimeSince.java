package am.itspace.cinemamodularcommon.entity.cinemadetail;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "time_since")
public class TimeSince {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TimeSince timeSince = (TimeSince) o;
        return Objects.equals(id, timeSince.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
