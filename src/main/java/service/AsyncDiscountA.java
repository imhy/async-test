package service;

import model.DiscountResult;
import model.EnumDiscount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component("DiscountA")
public class AsyncDiscountA extends DiscountAlgorithmTemplate<Object> {
	private static final Logger LOG = LoggerFactory.getLogger(AsyncDiscountA.class);

	private final EnumDiscount discountType = EnumDiscount.discountA;
	private double value = 0.1;
	private final boolean requireMainAmount = true;

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
				return new DiscountResult(value*deliveryAmount, discountType);
			}
		};
	}

	@Override
	public Object prepareData(Object... args) throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
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
