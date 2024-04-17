package kg.nurtelecom.internlabs.taskmanager.model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class LocalDateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
