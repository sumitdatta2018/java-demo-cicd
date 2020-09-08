package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.Sample;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@Value("${app.version:0.0.1}")
	private String version;

	private MetricsEndpoint metricsEndpoint;

	@Autowired
	private void setMetricsEndpoint(MetricsEndpoint metricsEndpoint) {
		this.metricsEndpoint = metricsEndpoint;
	}

	@RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHealth() {

		HealthModel healthModel = new HealthModel();
		healthModel.setStatus("OK");
		healthModel.setVersion(version);
		// healthModel.setStatus(new);

		MetricResponse metric = metricsEndpoint.metric("process.uptime", null);
		List<Sample> measurements = metric.getMeasurements();
		// long currentTime = new Date().getTime();

		Date currentDate = Calendar.getInstance().getTime();
		Sample processUptime = measurements.get(0);
		Long processUptimeInMillies = Math.round(processUptime.getValue() * 1000);
		Long runningSince = currentDate.getTime() - processUptimeInMillies;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		currentDate.setTime(runningSince);
		String runningSinceDate = dateFormat.format(currentDate);
		healthModel.setUptime("up since " + runningSinceDate);
		return new ResponseEntity<HealthModel>(healthModel, HttpStatus.OK);
	}

}
