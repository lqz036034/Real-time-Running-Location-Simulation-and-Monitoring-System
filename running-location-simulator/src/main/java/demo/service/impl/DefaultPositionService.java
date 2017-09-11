package demo.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import demo.model.CurrentPosition;
import demo.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class DefaultPositionService implements PositionService {

//    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPositionService.class);

    @Autowired
    private RestTemplate restTemplate;

    //POST to localhost:9006/api/locations body currentPosition
//    @Value("${com.ross.running.location.distribution}")
//    private String runningLocationDistribution;

    public DefaultPositionService() {
        super();
    }

    @Override
    @HystrixCommand(fallbackMethod = "processPositionInfoFallback")
    public void processPositionInfo(long id, CurrentPosition currentPosition,
                                    boolean sendPositionsToDistributionService) {

        String runningLocationDistribution = "http://running-location-distribution";

        if (sendPositionsToDistributionService) {
            log.info(String
                    .format("Thread %d Simulator is callling distribution REST API", Thread.currentThread().getId()));
            this.restTemplate.postForLocation(runningLocationDistribution + "/api/locations", currentPosition);

        }

    }

    public void processPositionInfoFallback(long id, CurrentPosition currentPosition,
                                            boolean sendPositionsToDistributionService) {
        log.error("Hystrix Fallback Method. Unable to send message for distribution.");
    }
}
