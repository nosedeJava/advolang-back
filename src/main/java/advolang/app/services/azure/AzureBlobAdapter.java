package advolang.app.services.azure;

import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AzureBlobAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AzureBlobAdapter.class);

    private final CloudBlobClient cloudBlobClient;

    public AzureBlobAdapter(CloudBlobClient cloudBlobClient) {
        this.cloudBlobClient = cloudBlobClient;
    }

    public boolean createContainer(String containerName){
        boolean containerCreated = false;
        CloudBlobContainer container = null;
        try {
            container = cloudBlobClient.getContainerReference(containerName);
        } catch (URISyntaxException | StorageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        try {
            assert container != null;
            containerCreated = container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (StorageException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return containerCreated;
    }

    public List<URI> listBlobs(String containerName){
        List<URI> uris = new ArrayList<>();
        try {
            CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
            for (ListBlobItem blobItem : container.listBlobs()) {
                uris.add(blobItem.getUri());
            }
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
        return uris;
    }
}
