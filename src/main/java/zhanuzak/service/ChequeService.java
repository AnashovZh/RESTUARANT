package zhanuzak.service;

import zhanuzak.dto.request.ChequeRequest;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface ChequeService {
    List<ChequeResponse> getAll();

    SimpleResponse save(ChequeRequest chequeRequest);

    ChequeResponse getById(Long id);

    SimpleResponse update(Long id, ChequeRequest chequeRequest);

    SimpleResponse delete(Long id);

    List<ChequeResponse> getAllChequeByUserEmail(String email);

    List<ChequeResponse> getAllChequesByUserEmail2(String email);
}
