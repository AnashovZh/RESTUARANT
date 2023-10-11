package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.StopListResponse;
import zhanuzak.models.StopList;

import java.util.List;

@Repository
public interface StopListRepository extends JpaRepository<StopList,Long> {
    @Query("select new zhanuzak.dto.response.StopListResponse(s.id,s.reason,s.date)from StopList s")
    List<StopListResponse>getAll();
}
