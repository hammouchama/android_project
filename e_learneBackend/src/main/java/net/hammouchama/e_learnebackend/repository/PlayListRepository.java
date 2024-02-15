package net.hammouchama.e_learnebackend.repository;

import net.hammouchama.e_learnebackend.model.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListRepository extends JpaRepository<PlayList,Long> {
}
