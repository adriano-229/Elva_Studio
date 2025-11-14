package com.projects.mycar.mycar_server.services.export;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.projects.mycar.mycar_server.dto.reportes.AlquilerReporteDTO;
import com.projects.mycar.mycar_server.enums.ReportFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AlquilerReportExporter {

    private static final String[] HEADERS = {
            "Cliente",
            "Documento",
            "Patente",
            "Modelo",
            "Marca",
            "Desde",
            "Hasta",
            "Monto"
    };

    public byte[] export(List<AlquilerReporteDTO> datos, ReportFormat formato) {
        return switch (formato) {
            case PDF -> exportarPdf(datos);
            case XLSX -> exportarExcel(datos);
        };
    }

    private byte[] exportarExcel(List<AlquilerReporteDTO> datos) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Alquileres");
            CreationHelper creationHelper = workbook.getCreationHelper();

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }

            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));

            int rowIdx = 1;
            for (AlquilerReporteDTO dto : datos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(nullSafe(dto.getClienteNombreCompleto()));
                row.createCell(1).setCellValue(nullSafe(dto.getClienteDocumento()));
                row.createCell(2).setCellValue(nullSafe(dto.getVehiculoPatente()));
                row.createCell(3).setCellValue(nullSafe(dto.getVehiculoModelo()));
                row.createCell(4).setCellValue(nullSafe(dto.getVehiculoMarca()));

                LocalDate fechaDesde = dto.getFechaDesde();
                LocalDate fechaHasta = dto.getFechaHasta();

                Cell desdeCell = row.createCell(5);
                if (fechaDesde != null) {
                    desdeCell.setCellValue(fechaDesde);
                    desdeCell.setCellStyle(dateStyle);
                }

                Cell hastaCell = row.createCell(6);
                if (fechaHasta != null) {
                    hastaCell.setCellValue(fechaHasta);
                    hastaCell.setCellStyle(dateStyle);
                }

                Cell montoCell = row.createCell(7);
                double monto = dto.getMontoTotal() == null ? 0.0 : dto.getMontoTotal();
                montoCell.setCellValue(monto);
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error al generar el archivo Excel", e);
        }
    }

    private byte[] exportarPdf(List<AlquilerReporteDTO> datos) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Reporte de alquileres por período", titleFont);
            title.setSpacingAfter(12);
            document.add(title);

            PdfPTable table = new PdfPTable(HEADERS.length);
            table.setWidthPercentage(100);
            for (String header : HEADERS) {
                PdfPCell cell = new PdfPCell(new Paragraph(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                table.addCell(cell);
            }

            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (AlquilerReporteDTO dto : datos) {
                table.addCell(valueOrDash(dto.getClienteNombreCompleto()));
                table.addCell(valueOrDash(dto.getClienteDocumento()));
                table.addCell(valueOrDash(dto.getVehiculoPatente()));
                table.addCell(valueOrDash(dto.getVehiculoModelo()));
                table.addCell(valueOrDash(dto.getVehiculoMarca()));
                table.addCell(formatDate(dto.getFechaDesde(), formatter));
                table.addCell(formatDate(dto.getFechaHasta(), formatter));
                table.addCell(formatMonto(dto.getMontoTotal()));
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Error al generar el archivo PDF", e);
        }
    }

    private String valueOrDash(Object value) {
        return value == null ? "-" : value.toString();
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

    /*private String formatDate(Date date, SimpleDateFormat formatter) {
        return date == null ? "-" : formatter.format(date);
    }*/

    private String formatDate(LocalDate date, DateTimeFormatter formatter) {
        return date == null ? "-" : date.format(formatter);
    }

    private String formatMonto(Double monto) {
        double safeMonto = monto == null ? 0.0 : monto;
        return String.format("%.2f", safeMonto);
    }
}
