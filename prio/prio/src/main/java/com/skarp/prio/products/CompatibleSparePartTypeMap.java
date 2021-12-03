package com.skarp.prio.products;

import com.skarp.prio.spareparts.Enums.SparePartType;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The {@code CompatibleSparePartTypeMap} class is responsible for structuring compatible spare-part types for a single
 * product category from {@link Category}. The {@code CompatibleSparePartTypeMap} contains compatible spare-parts types
 * for the following categories: Iphone, Ipad, Smartphone, Macbook, Laptop, Tablet.
 *
 *<p>
 * The {@code CompatibleSparePartTypeMap} structures Category from {@link Category} and SparePartType in a hashtable
 * called "conversionMap", which stores each object Category in an array slot associated with a compatible
 * list of SparePartType.
 * Using Map, which stores each object Category as a key with a list of SpartPartType as a value pair.
 * <p>
 *
 *
 * @author Team-Skarp
 * @see Product
 * @see Category
 * @since ???
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
