package com.example.mycar.services.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.example.mycar.dto.reportes.RecaudacionModeloDTO;
import com.example.mycar.enums.ReportFormat;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class RecaudacionModeloReportExporter {

    private static final String[] HEADERS = {
            "Modelo",
            "Marca",
            "Vehículos Totales",
            "Cantidad Alquileres",
            "Monto Total"
    };

    public byte[] export(List<RecaudacionModeloDTO> datos, ReportFormat formato) {
        return switch (formato) {
            case PDF -> exportarPdf(datos);
            case XLSX -> exportarExcel(datos);
        };
    }

    private byte[] exportarExcel(List<RecaudacionModeloDTO> datos) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("RecaudacionModelo");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }

            int rowIndex = 1;
            for (RecaudacionModeloDTO dto : datos) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(nullSafe(dto.modelo()));
                row.createCell(1).setCellValue(nullSafe(dto.marca()));
                row.createCell(2).setCellValue(dto.vehiculosTotales());
                row.createCell(3).setCellValue(dto.cantidadAlquileres());

                Cell montoCell = row.createCell(4);
                double montoTotal = dto.montoTotal() == null ? 0.0 : dto.montoTotal();
                montoCell.setCellValue(montoTotal);
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error al generar el archivo Excel de recaudación", e);
        }
    }

    private byte[] exportarPdf(List<RecaudacionModeloDTO> datos) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Recaudación por modelo", titleFont);
            title.setSpacingAfter(12);
            document.add(title);

            PdfPTable table = new PdfPTable(HEADERS.length);
            table.setWidthPercentage(100);

            for (String header : HEADERS) {
                PdfPCell cell = new PdfPCell(new Paragraph(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                table.addCell(cell);
            }

            for (RecaudacionModeloDTO dto : datos) {
                table.addCell(valueOrDash(dto.modelo()));
                table.addCell(valueOrDash(dto.marca()));
                table.addCell(String.valueOf(dto.vehiculosTotales()));
                table.addCell(String.valueOf(dto.cantidadAlquileres()));
                table.addCell(formatMonto(dto.montoTotal()));
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Error al generar el PDF de recaudación", e);
        }
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

    private String valueOrDash(Object value) {
        return value == null ? "-" : value.toString();
    }

    private String formatMonto(Double monto) {
        double safeMonto = monto == null ? 0.0 : monto;
        return String.format("%.2f", safeMonto);
    }
}
