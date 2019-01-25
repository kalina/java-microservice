package com.example.microservice.metrics;

import com.codahale.metrics.*;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpParser.RequestHandler;

public class Metrics {
	static final MetricRegistry metrics = new MetricRegistry();
	private final Timer responses = metrics.timer(MetricRegistry.name(RequestHandler.class, "responses"));


    public static void initialize() {
      startReport();
      Meter requests = metrics.meter("requests");
      requests.mark();
      wait5Seconds();
    }
    public static MetricRegistry getMetricRegistry() {
    	return metrics;
    }

  static void startReport() {
      ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
          .convertRatesTo(TimeUnit.SECONDS)
          .convertDurationsTo(TimeUnit.MILLISECONDS)
          .build();
      reporter.start(1, TimeUnit.SECONDS);
  }

  static void wait5Seconds() {
      try {
          Thread.sleep(5*1000);
      }
      catch(InterruptedException e) {}
  }
  
	public String handleRequest(Request request, Response response) {
	    final Timer.Context context = responses.time();
	    try {
	        // etc;
	        return "OK";
	    } finally {
	        context.stop();
	    }
	}
}
