package clinic.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import clinic.dto.SlotDto;
import clinic.service.SlotService;

@RestController
@RequestMapping("/api/v1/doctors")
public class SlotController {
    private final SlotService slotService;
    public SlotController(SlotService slotService){
        this.slotService=slotService;
    }
    @GetMapping("/{id}/slots")
    public List<SlotDto> getAvailablesSlots(@PathVariable Long id, @RequestParam LocalDate date){
        return slotService.getAvailableSlots(id, date);
    }
}
