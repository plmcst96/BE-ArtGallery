package cristinapalmisani.BEArtGallery.repositories;

import cristinapalmisani.BEArtGallery.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationDAO extends JpaRepository<Location, UUID> {
    Optional<Location> findByExhibitionUuid(UUID exhibitionUuid);
    Optional<Location> findByUserUuid(UUID userUuid);
}
