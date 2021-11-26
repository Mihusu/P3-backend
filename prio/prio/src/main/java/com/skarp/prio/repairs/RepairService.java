package com.skarp.prio.repairs;

import com.skarp.prio.spareparts.SparePart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public interface RepairService {
    public List<Repair>      getRepairList(@RequestParam(required = false, value = "sortBy") String sortBy, @RequestParam(required = false, value = "LIMIT") String limit);
    public URI               createRepair(@RequestParam(required = true, value = "prod_id") String prod_id, @RequestParam(required = false, value = "tech_id") String tech_id, UriComponentsBuilder uriComponentsBuilder);
    public Repair            getRepairByID(@PathVariable String id);
    public void              pauseRepair(@PathVariable String id);
    public void              resumeRepair(@PathVariable String id);
    public void              finishRepair(@PathVariable String id);
    public void              addSparePart(@PathVariable String id, @RequestParam(required = true, value = "sparepart_id") String sparepart_id);
}
