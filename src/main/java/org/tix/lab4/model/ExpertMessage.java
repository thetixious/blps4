package org.tix.lab4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ExpertMessage implements Serializable {
    private Long creditOfferId;
    private Long userId;
    private String candidateName;
    private String candidateSurname;
    private String candidatePassport;
    private Double candidateCreditLimit;
    private Double candidateSalary;
    private Set<Cards> preferredCards;


}
