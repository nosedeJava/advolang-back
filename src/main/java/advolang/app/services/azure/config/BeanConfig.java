package advolang.app.services.azure.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Objects;

@Configuration
public class BeanConfig {
    private final Environment environment;

    public BeanConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public CloudBlobClient cloudBlobClient() throws URISyntaxException, InvalidKeyException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(Objects.requireNonNull(environment.getProperty("azure.storage.ConnectionString")));
        return storageAccount.createCloudBlobClient();
    }
}