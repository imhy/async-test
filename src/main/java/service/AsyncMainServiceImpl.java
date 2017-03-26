package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class AsyncMainServiceImpl implements AsyncMainService {

	private static final Logger LOG = LoggerFactory.getLogger(AsyncMainServiceImpl.class);
	@Async
	@Override
	public CompletableFuture<Double> calculate() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		LOG.info("completed Main amount calculation");
		return CompletableFuture.completedFuture(100.0);
	}
}
