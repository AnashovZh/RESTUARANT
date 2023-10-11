package zhanuzak.service;

import zhanuzak.dto.request.StopListRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.StopListResponse;

import java.util.List;

public interface StopListService {
    List<StopListResponse> getAll();

    SimpleResponse save(StopListRequest stopListRequest);

}
