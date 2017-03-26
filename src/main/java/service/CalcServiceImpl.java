package service;

import model.DiscountResult;
import model.EnumDiscount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class CalcServiceImpl implements CalcService {

	private static final Logger LOG = LoggerFactory.getLogger(CalcServiceImpl.class);


	@Autowired
	private AsyncMainService asyncMainService;

	@Autowired
	@Qualifier("DiscountA")
	private AsyncDiscount asyncDiscountA;

	@Autowired
	@Qualifier("DiscountB")
	private AsyncDiscount asyncDiscountB;

	@Autowired
	@Qualifier("DiscountC")
	private AsyncDiscount asyncDiscountC;


	@Override
	public Double calculate() {
		Double total = 0.0;

		try {


			Map<EnumDiscount,AsyncDiscount> discountCalculators = findAvailableDiscountCalculators();
			List<CompletableFuture<DiscountResult>> futureDiscountResults = new ArrayList<>();
			List<CompletableFuture> allCalculations = new ArrayList<>();


			CompletableFuture<Double> mainAmount = asyncMainService.calculate();
			allCalculations.add(mainAmount);

			//discountCalculators.values().stream().map(wrapperConsumer(ad-> ad.calculate(mainAmount))).collect(Collectors.toList());

			for (AsyncDiscount asyncDiscount : discountCalculators.values()) {
				futureDiscountResults.add(asyncDiscount.calculate(mainAmount));
			}

			allCalculations.addAll(futureDiscountResults);

			CompletableFuture.anyOf(futureDiscountResults.stream().toArray(CompletableFuture[]::new));


			List<DiscountResult> discountResults = new ArrayList<>();
			for(CompletableFuture<DiscountResult> f : futureDiscountResults){
				discountResults.add(f.get());
			}

			List<DiscountResult> sortedDiscounts = sortDiscounts(discountResults);

			Double discountTotal = 0.0;
			Set<EnumDiscount> applayedDiscounts = new HashSet<>();

			for(DiscountResult d : sortedDiscounts ){
				if(discountCalculators.get(d.getDiscountType()).canApplayAfter(applayedDiscounts)){
					applayedDiscounts.add(d.getDiscountType());
					discountTotal += d.getAmount();
				}
			}

			total  = mainAmount.get() + discountTotal;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	private Map<EnumDiscount,AsyncDiscount> findAvailableDiscountCalculators(Object... args) {
		Map<EnumDiscount,AsyncDiscount> discountCalculators = new HashMap<>();
		discountCalculators.put(asyncDiscountA.getDiscountType(), asyncDiscountA);
		discountCalculators.put(asyncDiscountB.getDiscountType(), asyncDiscountB);
		discountCalculators.put(asyncDiscountC.getDiscountType(), asyncDiscountC);
		return discountCalculators;
	}

	private List<DiscountResult> sortDiscounts(List<DiscountResult> values){
		return values.stream().sorted((v1, v2) -> Double.compare(v1.getAmount(),v2.getAmount())).collect(Collectors.toList());
	}

	private Consumer<AsyncDiscount> wrapperConsumer(Consumer<AsyncDiscount> consumer){
		return ad -> {
			try{
				consumer.accept(ad);
			}catch (Exception e){
				throw new RuntimeException(e.getMessage());
			}
		};
	}
}
