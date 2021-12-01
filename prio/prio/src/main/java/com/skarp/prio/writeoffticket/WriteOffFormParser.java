package com.skarp.prio.writeoffticket;

import com.skarp.prio.spareparts.Enums.SparePartType;

import java.util.List;

public class WriteOffFormParser {

    public static List<SparePartType> parseTypes(WriteOffTicketForm woForm)
    {
        return woForm.getMarkedParts().stream().map(SparePartType::valueOf).toList();

    }
}
