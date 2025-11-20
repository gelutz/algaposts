package com.lutz.algaposts.infrastructure.rabbitmq;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {
	// private final TemperatureMonitorService temperatureMonitorService;
	// private final SensorAlertService sensorAlertService;

	// @Transactional
	// @RabbitListener(queues = RabbitMQConfig.PROCESS_TEMPERATURE_QUEUE,
	// concurrency = "2-3")
	// public void handleProcessTemperature(@Payload TemperatureLogData data) {
	// temperatureMonitorService.handleProcessTemperature(data);
	// }

	// @Transactional
	// @RabbitListener(queues = RabbitMQConfig.ALERT_QUEUE, concurrency = "2-3")
	// public void handleAlert(@Payload TemperatureLogData data) {
	// sensorAlertService.handleAlert(data);
	// }
}
