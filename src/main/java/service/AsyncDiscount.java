package service;

import model.DiscountResult;
import model.EnumDiscount;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public interface AsyncDiscount<T> {
	public CompletableFuture<DiscountResult> calculate(CompletableFuture<Double> mainAmount) throws InterruptedException, ExecutionException;
	public EnumDiscount getDiscountType();
	public boolean canApplayAfter(Set<EnumDiscount> applayed);
	public List<EnumDiscount> getConflictDiscounts();
	public boolean canApplay(CompletableFuture<Double> mainAmount, Object... args);
	public Function<Double,DiscountResult> getCalculationFunction(T data);
	public DiscountResult doCalculation(CompletableFuture<Double> mainAmount, T data) throws ExecutionException, InterruptedException;
	public T prepareData(Object... args) throws InterruptedException;
	public Logger getLOG();
	public boolean isRequireMainAmount();
}
