package cristinapalmisani.BEArtGallery.repositories;

import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ArtistWorkDAO extends JpaRepository<ArtistWork, UUID> {
    Page<ArtistWork> findByGalleryArtistUuid(UUID artistUuid, Pageable pageable);
    Page<ArtistWork> findByYearStartWork(long yearStartWork, Pageable pageable);

    @Query("SELECT aw.yearStartWork, aw FROM ArtistWork aw " +
            "JOIN aw.gallery g " +
            "JOIN g.artist a " +
            "WHERE g.uuid = :galleryId " +
            "GROUP BY aw.yearStartWork, aw " +
            "ORDER BY aw.yearStartWork")
    Page<ArtistWork> findOperePerArtistaRaggruppatePerAnno(@Param("galleryId") UUID galleryId, Pageable pageable);
}
