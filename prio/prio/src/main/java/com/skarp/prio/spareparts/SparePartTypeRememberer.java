package com.skarp.prio.spareparts;

import java.util.ArrayList;
// This is a proposal class
/* This class is used to remember all the different types of spare part types the system knows about */
public class SparePartTypeRememberer {
    private ArrayList<NewSparePart> allSparePartTypesInSystem = new ArrayList<>();

    public void addNewTypeOfSparePartToSystem(NewSparePart newSparePartType){
        allSparePartTypesInSystem.add(newSparePartType);
    }

    public ArrayList<NewSparePart> getSparePartTypesInSystem(){
        return allSparePartTypesInSystem;
    }
}
