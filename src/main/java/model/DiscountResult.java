package model;

public class DiscountResult {
	private Double amount;
	private EnumDiscount discountType;

	public DiscountResult(Double amount, EnumDiscount discount) {
		this.amount = amount;
		this.discountType = discount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public EnumDiscount getDiscountType() {
		return discountType;
	}

	public void setDiscountType(EnumDiscount discountType) {
		this.discountType = discountType;
	}
}
