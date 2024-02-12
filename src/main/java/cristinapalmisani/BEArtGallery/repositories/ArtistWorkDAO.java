package cristinapalmisani.BEArtGallery.repositories;

import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtistWorkDAO extends JpaRepository<ArtistWork, UUID> {
    /*Page<ArtistWork> findByArtistName(Artist artist, Pageable pageable);
    Page<ArtistWork> findByYear(int year, Pageable pageable);*/
}
