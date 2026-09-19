package clinic.service;

import java.time.LocalDate;
import java.util.List;

import clinic.dto.SlotDto;

public interface SlotService {
    List<SlotDto> getAvailableSlots(Long doctorId, LocalDate date);
    
}
