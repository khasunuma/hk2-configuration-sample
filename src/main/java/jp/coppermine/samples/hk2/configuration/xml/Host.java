package jp.coppermine.samples.hk2.configuration.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jvnet.hk2.annotations.Contract;

@Contract
@XmlRootElement
public interface Host {
    
    @XmlElement(required = true)
    int getId();
    
    @XmlElement(defaultValue = "localhost")
    String getName();
    
    void setName(String name);
    
    @XmlElement(defaultValue = "0.0.0.0")
    String getAddress();
    
    void setAddress(String address);
}
