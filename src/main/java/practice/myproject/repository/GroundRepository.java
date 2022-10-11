package practice.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.myproject.domain.Ground;

public interface GroundRepository extends JpaRepository<Ground, Long> {
}
