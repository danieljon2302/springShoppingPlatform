package dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class CreateOrderRequest {
	//為什麼我還需要使用一個list來裝? 因傳進來的object可能不止一個, 若單只用一個vo來裝的話會不夠,
	//故需使用list來裝前端傳進的參數
	//此為"巢狀json參數, 常使用, 需再熟練" (4-4 
	
	@NotEmpty
	private List<BuyItem> buyItemList;

	public List<BuyItem> getBuyItemList() {
		return buyItemList;
	}

	public void setBuyItemList(List<BuyItem> buyItemList) {
		this.buyItemList = buyItemList;
	}
	
}
