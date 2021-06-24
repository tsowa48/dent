package gcg.dent.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import gcg.dent.entity.Document;
import gcg.dent.repository.DocumentRepository;
import gcg.dent.util.Templates;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.server.types.files.StreamedFile;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/api/document")
public class DocumentController {

    @Inject
    DocumentRepository documentRepository;

    @Inject
    Templates templates;

    /*@Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Document get(Long id) {
        return documentRepository.findById(id);
    }*/

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Document> getAll() {
        return documentRepository.getAll();
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public Document add(Document document) {
        return documentRepository.addDocument(document);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public Document change(Document document) {
        return documentRepository.update(document);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        documentRepository.removeById(id);
        return HttpResponse.ok();
    }

    @Get(uri = "/download")
    public HttpResponse<byte[]> download(Long uid,
                                         @Nullable Long act,
                                         @Nullable Long card,
                                         @Nullable Long client,
                                         @Nullable Long contract,
                                         @Nullable Long employee,
                                         @Nullable Long history,
                                         @Nullable Long patient)
            throws Exception {
        Document document = documentRepository.findById(uid);
        String fileName = URLEncoder.encode(document.getName(), "UTF-8").replace("+", "%20") + ".doc";

        Map<String, Object> params = new HashMap<>();
        params.put("act", act);
        params.put("card", card);
        params.put("client", client);
        params.put("contract", contract);
        params.put("employee", employee);
        params.put("history", history);
        params.put("patient", patient);

        String content = templates.load(document, params);

        return HttpResponse.ok(content.getBytes())
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"");
    }
}
