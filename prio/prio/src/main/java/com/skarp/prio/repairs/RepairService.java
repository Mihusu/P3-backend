package com.skarp.prio.repairs;

import com.skarp.prio.spareparts.SparePart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

/**
 * {@code RepairService} is the interface responsible for providing our {@link RepairController} with methods.
 * The logic for these methods is then implemented in our {@link RepairServiceImpl} class.
 *
 * @author Team Skarp
 * @see RepairController
 * @see RepairServiceImpl
 */

public interface RepairService {
    List<Repair>      getRepairList(@RequestParam(required = false, value = "sortBy") String sortBy, @RequestParam(required = false, value = "LIMIT") String limit);
    Repair            createRepair(@RequestParam(required = true, value = "prod_id") String prod_id, @RequestParam(required = false, value = "tech_id") String tech_id);
    Repair            getRepairByID(@PathVariable String id);
    Repair            pauseRepair(@PathVariable String id);
    Repair            resumeRepair(@PathVariable String id);
    void              cancelRepair(@PathVariable String id);
    void              finishRepair(@PathVariable String id);
    SparePart         addSparePart(@PathVariable String repairId, @PathVariable String sparepartId);
    SparePart         removeSparePart(@PathVariable String repairId, @PathVariable  String sparepartId);
}
