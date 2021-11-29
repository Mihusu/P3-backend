package com.skarp.prio.products;

import com.skarp.prio.spareparts.Enums.SparePartType;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Responsible for getting compatible spare-part types for a single product category
 */
public class CompatibleSparePartTypeMap {

    public static final Map<Category, List<SparePartType>> conversionMap = new Hashtable<>();
    static {
        conversionMap.put(Category.IPHONE, List.of(SparePartType.SCREEN, SparePartType.BATTERY,
                                                   SparePartType.HOUSING, SparePartType.CHARGING_DOCK,
                                                   SparePartType.FRONT_CAMERA, SparePartType.BACK_CAMERA,
                                                   SparePartType.LOGICBOARD));

        conversionMap.put(Category.IPAD, List.of(SparePartType.SCREEN, SparePartType.BATTERY,
                                                 SparePartType.HOUSING, SparePartType.CHARGING_DOCK,
                                                 SparePartType.FRONT_CAMERA, SparePartType.BACK_CAMERA,
                                                 SparePartType.LOGICBOARD));

        conversionMap.put(Category.SMARTPHONE, List.of(SparePartType.SCREEN, SparePartType.BATTERY,
                                                       SparePartType.HOUSING, SparePartType.CHARGING_DOCK,
                                                       SparePartType.FRONT_CAMERA, SparePartType.BACK_CAMERA,
                                                       SparePartType.LOGICBOARD));

        conversionMap.put(Category.MACBOOK, List.of(SparePartType.SCREEN, SparePartType.KEYBOARD,
                                                    SparePartType.SSD, SparePartType.TRACKPAD,
                                                    SparePartType.WEBCAM));

        conversionMap.put(Category.LAPTOP, List.of(SparePartType.SCREEN, SparePartType.KEYBOARD,
                                                   SparePartType.SSD, SparePartType.RAM,
                                                   SparePartType.WEBCAM));

    }
}
