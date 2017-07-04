package jp.coppermine.samples.hk2.configuration.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import javax.inject.Inject;
import javax.inject.Provider;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;
import org.glassfish.hk2.xml.api.XmlServiceUtilities;

public class ReadOnlyMain {

    @Inject
    private Provider<XmlService> xmlServiceProvider;

    public void execute() {
        XmlService xmlService = xmlServiceProvider.get();

        try (InputStream in = getClass().getResourceAsStream("/host.xml")) {
            XmlRootHandle<Host> rootHandle = xmlService.unmarshal(in, Host.class);
            Host host = rootHandle.getRoot();
            System.out.println("id: " + host.getId());
            System.out.println("name: " + host.getName());
            System.out.println("address: " + host.getAddress());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    public static void main(String[] args) {
        ServiceLocatorFactory factory = ServiceLocatorFactory.getInstance();
        ServiceLocator locator = factory.create("default");

        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        config.bind(BuilderHelper.link(ReadOnlyMain.class).build());
        config.commit();

        XmlServiceUtilities.enableXmlService(locator);

        ReadOnlyMain instance = locator.getService(ReadOnlyMain.class);
        instance.execute();
    }

}
