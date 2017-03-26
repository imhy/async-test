package service;

import java.util.concurrent.CompletableFuture;


public interface AsyncMainService {

	/**
	 *  Расчет стоимости доставки по геозонам
	 * @return
	 * @throws InterruptedException
	 */
	public CompletableFuture<Double> calculate() throws InterruptedException;
}
