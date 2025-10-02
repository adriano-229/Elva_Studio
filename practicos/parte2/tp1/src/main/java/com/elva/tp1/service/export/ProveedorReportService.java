package com.elva.tp1.service.export;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.domain.Localidad;
import com.elva.tp1.domain.Proveedor;
import com.elva.tp1.service.ProveedorService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ProveedorReportService {

    private final ProveedorService proveedorService;

    public ProveedorReportService(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    public byte[] generarPdf() {
        List<Proveedor> proveedores = proveedorService.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Listado de Proveedores", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(12f);
            document.add(title);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.2f, 2f, 2f, 2f, 3f, 3.5f, 1f});

            addHeader(table, "ID");
            addHeader(table, "CUIT");
            addHeader(table, "Nombre");
            addHeader(table, "Apellido");
            addHeader(table, "Email");
            addHeader(table, "Dirección");
            addHeader(table, "Activo");

            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            for (Proveedor proveedor : proveedores) {
                table.addCell(createCell(proveedor.getId() != null ? proveedor.getId().toString() : "", bodyFont));
                table.addCell(createCell(defaultString(proveedor.getCuit()), bodyFont));
                table.addCell(createCell(defaultString(proveedor.getNombre()), bodyFont));
                table.addCell(createCell(defaultString(proveedor.getApellido()), bodyFont));
                table.addCell(createCell(defaultString(proveedor.getEmail()), bodyFont));
                table.addCell(createCell(buildDireccion(proveedor.getDireccion()), bodyFont));
                table.addCell(createCell(Boolean.TRUE.equals(proveedor.getActivo()) ? "Sí" : "No", bodyFont));
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new IllegalStateException("No se pudo generar el PDF de proveedores", e);
        }
    }

    private void addHeader(PdfPTable table, String text) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
        PdfPCell header = new PdfPCell(createPhrase(text, headerFont));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setBackgroundColor(new Color(75, 101, 173));
        header.setPadding(6f);
        table.addCell(header);
    }

    private PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell(createPhrase(value, font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(4f);
        return cell;
    }

    private com.lowagie.text.Phrase createPhrase(String text, Font font) {
        return new com.lowagie.text.Phrase(text != null ? text : "", font);
    }

    private String defaultString(String value) {
        return value != null ? value : "";
    }

    private String buildDireccion(Direccion direccion) {
        if (direccion == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        if (direccion.getCalle() != null) {
            builder.append(direccion.getCalle());
        }

        if (direccion.getAltura() != null) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(direccion.getAltura());
        }

        Localidad localidad = direccion.getLocalidad();
        if (localidad != null && localidad.getNombre() != null) {
            if (builder.length() > 0) {
                builder.append(" - ");
            }
            builder.append(localidad.getNombre());
        }

        return builder.toString();
    }
}
