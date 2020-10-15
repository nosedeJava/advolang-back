package advolang.app.controllers;
import advolang.app.services.azure.AzureBlobAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin("*")
public class AzureController {
    private final AzureBlobAdapter azureBlobAdapter;

    public AzureController(AzureBlobAdapter azureBlobAdapter) {
        this.azureBlobAdapter = azureBlobAdapter;
    }

    @PostMapping("/container/{containerName}")
    public ResponseEntity<?> createContainer(@PathVariable String containerName){
        boolean created = azureBlobAdapter.createContainer(containerName);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/blobs")
    public ResponseEntity<?> getAllBlobs(@RequestParam String containerName){
        List<URI> uris = azureBlobAdapter.listBlobs(containerName);
        return ResponseEntity.ok(uris);
    }
}
