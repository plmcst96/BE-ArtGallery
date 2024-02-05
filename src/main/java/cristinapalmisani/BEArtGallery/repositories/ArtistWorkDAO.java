package cristinapalmisani.BEArtGallery.repositories;

import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtistWorkDAO extends JpaRepository<ArtistWork, UUID> {
}
