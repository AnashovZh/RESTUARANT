package zhanuzak.repository.dao;

import zhanuzak.dto.response.ChequeResponse;

import java.util.List;

public interface ChequeJdbcTemplate {
    List<ChequeResponse>getAll();
}
