
package cn.spdb.harrier.api.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitorConfig {

    @Value("${traffic.control.global.switch:false}")
    private boolean trafficGlobalControlSwitch;
    @Value("${traffic.control.max.global.qps.rate:300}")
    private Integer maxGlobalQpsRate;
    @Value("${traffic.control.tenant.switch:false}")
    private boolean trafficTenantControlSwitch;
    @Value("${traffic.control.default.tenant.qps.rate:10}")
    private Integer defaultTenantQpsRate;
    @Value("#{'${traffic.control.customize.tenant.qps.rate:}'.empty?null:'${traffic.control.customize.tenant.qps.rate:}'}")
    private Map<String, Integer> customizeTenantQpsRate;

    public boolean isTrafficGlobalControlSwitch() {
        return trafficGlobalControlSwitch;
    }

    public void setTrafficGlobalControlSwitch(boolean trafficGlobalControlSwitch) {
        this.trafficGlobalControlSwitch = trafficGlobalControlSwitch;
    }

    public Integer getMaxGlobalQpsRate() {
        return maxGlobalQpsRate;
    }

    public void setMaxGlobalQpsRate(Integer maxGlobalQpsRate) {
        this.maxGlobalQpsRate = maxGlobalQpsRate;
    }

    public boolean isTrafficTenantControlSwitch() {
        return trafficTenantControlSwitch;
    }

    public void setTrafficTenantControlSwitch(boolean trafficTenantControlSwitch) {
        this.trafficTenantControlSwitch = trafficTenantControlSwitch;
    }

    public Integer getDefaultTenantQpsRate() {
        return defaultTenantQpsRate;
    }

    public void setDefaultTenantQpsRate(Integer defaultTenantQpsRate) {
        this.defaultTenantQpsRate = defaultTenantQpsRate;
    }

    public Map<String, Integer> getCustomizeTenantQpsRate() {
        return customizeTenantQpsRate;
    }

    public void setCustomizeTenantQpsRate(Map<String, Integer> customizeTenantQpsRate) {
        this.customizeTenantQpsRate = customizeTenantQpsRate;
    }
}
