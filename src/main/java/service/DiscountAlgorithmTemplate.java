package service;

import model.DiscountResult;
import model.EnumDiscount;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class DiscountAlgorithmTemplate<T> implements AsyncDiscount<T> {
	@Async
	@Override
	public CompletableFuture<DiscountResult> calculate(CompletableFuture<Double> mainAmount) throws InterruptedException, ExecutionException {
		T data = prepareData("1","2");
		getLOG().info("completed preparing for {}", getDiscountType().name());
		DiscountResult result = doCalculation(mainAmount,data);
		getLOG().info("completed calculation for {}", getDiscountType().name());
		return CompletableFuture.completedFuture(result);
	}

	@Override
	public boolean canApplayAfter(Set<EnumDiscount> applayed) {
		List<EnumDiscount> conflicts = getConflictDiscounts();
		for (EnumDiscount conflict : conflicts){
			if(applayed.contains(conflict)) return false;
		}
		return true;
	}


	@Override
	public DiscountResult doCalculation(CompletableFuture<Double> mainAmount, T data ) throws ExecutionException, InterruptedException {
		if(isRequireMainAmount()){
			return  mainAmount.thenApply(getCalculationFunction(data)).get();
		}else{
			return getCalculationFunction(data).apply(0.0);
		}
	}
}
