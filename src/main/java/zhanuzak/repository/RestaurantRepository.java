package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.RestaurantResponse;
import zhanuzak.models.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new zhanuzak.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service)from Restaurant r")
    List<RestaurantResponse> getAll();

}
