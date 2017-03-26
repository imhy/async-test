package service;

import model.DiscountResult;
import model.EnumDiscount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Component("DiscountC")
public class AsyncDiscountC extends DiscountAlgorithmTemplate<Object> {
	private static final Logger LOG = LoggerFactory.getLogger(AsyncDiscountC.class);

	private final EnumDiscount discountType = EnumDiscount.discountC;
	private double value = 15;
	private final boolean requireMainAmount = false;

	@Override
	public EnumDiscount getDiscountType() {
		return this.discountType;
	}


	@Override
	public List<EnumDiscount> getConflictDiscounts() {
		return Arrays.asList(new EnumDiscount[]{discountType});
	}

	@Override
	public boolean canApplay(CompletableFuture<Double> mainAmount, Object... args) {
		return true;
	}

	@Override
	public Function<Double,DiscountResult> getCalculationFunction(Object data){
		return new Function<Double, DiscountResult>() {
			@Override
			public DiscountResult apply(Double deliveryAmount) {
				return new DiscountResult(value, discountType);
			}
		};
	}

	@Override
	public Object prepareData(Object... args) throws InterruptedException {
		return null;
	}

	@Override
	public Logger getLOG() {
		return LOG;
	}

	@Override
	public boolean isRequireMainAmount() {
		return requireMainAmount;
	}
}
