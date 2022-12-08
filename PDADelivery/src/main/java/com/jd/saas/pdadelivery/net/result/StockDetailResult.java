package com.jd.saas.pdadelivery.net.result;

import java.util.ArrayList;

public class StockDetailResult {
    private WarehouseResult warehouseDto;
    private RcvHeaderInfoResult rcvHeaderInfoDto;
    private ArrayList<RcvStockInfoResult> rcvStockInfoDtos;

    public WarehouseResult getWarehouseDto() {
        return warehouseDto;
    }

    public void setWarehouseDto(WarehouseResult warehouseDto) {
        this.warehouseDto = warehouseDto;
    }

    public RcvHeaderInfoResult getRcvHeaderInfoDto() {
        return rcvHeaderInfoDto;
    }

    public void setRcvHeaderInfoDto(RcvHeaderInfoResult rcvHeaderInfoDto) {
        this.rcvHeaderInfoDto = rcvHeaderInfoDto;
    }

    public ArrayList<RcvStockInfoResult> getRcvStockInfoDtos() {
        return rcvStockInfoDtos;
    }

    public void setRcvStockInfoDtos(ArrayList<RcvStockInfoResult> rcvStockInfoDtos) {
        this.rcvStockInfoDtos = rcvStockInfoDtos;
    }
}
