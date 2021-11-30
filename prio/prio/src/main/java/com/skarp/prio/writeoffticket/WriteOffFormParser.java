package com.skarp.prio.writeoffticket;

import com.skarp.prio.spareparts.Enums.SparePartType;

import java.util.Arrays;
import java.util.List;

public class WriteOffFormParser {

    public static List<SparePartType> parseTypes(WriteOffTicketForm woForm)
    {
        return woForm.markedParts.stream().map(SparePartType::valueOf).toList();

    }
}
