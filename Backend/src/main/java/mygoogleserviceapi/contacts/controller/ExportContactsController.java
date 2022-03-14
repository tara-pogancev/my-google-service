package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.service.interfaces.ExportContactsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/export")
public class ExportContactsController {

    private final ExportContactsService exportService;

    @GetMapping("/csv/all")
    public void exportAllContactsCsv(HttpServletResponse servletResponse, @RequestHeader(name = "Authorization") String jwt) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"contacts.csv\"");
        exportService.exportAllContactsByUserToCsv(servletResponse.getWriter(), jwt);
    }

    @PutMapping("/csv")
    public void exportSelectedContactsCsv(HttpServletResponse servletResponse, @RequestHeader(name = "Authorization") String jwt, @RequestBody List<Long> ids) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"contacts.csv\"");
        exportService.exportSelectedContactsByUserToCsv(servletResponse.getWriter(), jwt, ids);
    }

    @GetMapping("/json/all")
    public void exportAllContactsJson(HttpServletResponse servletResponse, @RequestHeader(name = "Authorization") String jwt) throws IOException {
        servletResponse.setContentType("application/json");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"contacts.json\"");
        exportService.exportAllContactsByUserToJson(servletResponse.getWriter(), jwt);
    }

    @PutMapping("/json")
    public void exportSelectedContactsJson(HttpServletResponse servletResponse, @RequestHeader(name = "Authorization") String jwt, @RequestBody List<Long> ids) throws IOException {
        servletResponse.setContentType("application/json");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"contacts.json\"");
        exportService.exportSelectedContactsByUserToJson(servletResponse.getWriter(), jwt, ids);
    }

}
