package zhanuzak.service;

import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserResponse> getAll();

    List<UserResponse> getAllOnlyByRestaurants();

    List<UserResponse> getAllWaitingEmployees();

    SimpleResponse delete(Long id);

    SimpleResponse updateMap(Long id, Map<String, Object> fields);
}
