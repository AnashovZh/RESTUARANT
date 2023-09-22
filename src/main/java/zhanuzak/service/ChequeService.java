package zhanuzak.service;

import zhanuzak.dto.request.ChequeRequest;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface ChequeService {
    List<ChequeResponse> getAll();

    SimpleResponse save(ChequeRequest chequeRequest);

    ChequeResponse getById(Long id);
}
