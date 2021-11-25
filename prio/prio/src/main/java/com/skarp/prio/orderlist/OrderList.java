package com.skarp.prio.orderlist;

import com.skarp.prio.spareparts.SparePart;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
  List<SparePart> sparePartList = new ArrayList<>();

  public List<SparePart> getSparePartList() {
    return sparePartList;
  }

  public void setSparePartList(List<SparePart> sparePartList) {
    this.sparePartList = sparePartList;
  }


  public void addSparePart(SparePart part){
    sparePartList.add(part);
  }


}
