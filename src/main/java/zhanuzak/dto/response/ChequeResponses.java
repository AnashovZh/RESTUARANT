package zhanuzak.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ChequeResponses {
    private Long id;
    private String fullName;
    private String email;
    private List<ChequeResponse> chequeResponses;

    public ChequeResponses(Long id, String fullName, String email, List<ChequeResponse> chequeResponses) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.chequeResponses = chequeResponses;
    }
}
