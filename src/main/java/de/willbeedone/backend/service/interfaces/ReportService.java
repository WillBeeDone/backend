package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.report_dto.request_dto.ReportRequestDto;
import de.willbeedone.backend.domain.dto.report_dto.response_dto.ReportResponseDto;
import de.willbeedone.backend.domain.entity.Offer;

import java.util.List;

public interface ReportService {

    void addNewReport (ReportRequestDto reportRequestDto);
    void activateReportById(Long id);
    void getReportById(Long id);
    List <ReportResponseDto> getAllReport ();
    List <ReportResponseDto> getAllReportByReporterEmail (String email);
    List <ReportResponseDto> getAllReportByReportedEmail (String email);
}
