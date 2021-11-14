package gcg.dent.controller.api;

import gcg.dent.entity.Document;
import gcg.dent.repository.DocumentRepository;
import gcg.dent.service.ReportService;
import gcg.dent.util.Templates;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/api/document")
public class DocumentController {

    @Inject
    DocumentRepository documentRepository;

    @Inject
    Templates templates;

    @Inject
    ReportService reportService;

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
                                         @Nullable Long contract,
                                         @Nullable Long employee,
                                         @Nullable Long history,
                                         @Nullable Long patient)
            throws Exception {
        Document document = documentRepository.findById(uid);

        Map<String, Object> params = new HashMap<>();
        params.put("act", act);
        params.put("card", card);
        params.put("contract", contract);
        params.put("employee", employee);
        params.put("history", history);
        params.put("patient", patient);

        String fileName = URLEncoder.encode(document.getName(), "UTF-8").replace("+", "%20") + ".docx";
        byte[] content;
        switch (document.getCode()) {
            case "sopd":
                content = reportService.getReport(document, "docx", params).getValue();
                break;
            default:
                fileName = URLEncoder.encode(document.getName(), "UTF-8").replace("+", "%20") + ".doc";
                content = templates.load(document, params).getBytes();
        }
        return HttpResponse.ok(content)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"");
    }
}
