package zhanuzak.repository.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.repository.dao.ChequeJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class ChequeJdbcTemplateImpl implements ChequeJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;
//    private ChequeResponse rowMapper(ResultSet rs, int rowName) throws SQLException {
//        return new ChequeResponse(
//                rs.getLong("id"),
//                rs.getString("fullName"),
//                rs.getString("menuItemResponse"),
//                rs.getDate("createdAt"),
//                rs.getBigDecimal("service"),
//                rs.getBigDecimal("grandTotal"));
//
//    }

    @Override
    public List<ChequeResponse> getAll() {
        return null;
    }
}
