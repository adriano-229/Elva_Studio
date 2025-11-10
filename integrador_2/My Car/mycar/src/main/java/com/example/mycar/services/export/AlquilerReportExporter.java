package com.example.mycar.services.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.example.mycar.dto.reportes.AlquilerReporteDTO;
import com.example.mycar.enums.ReportFormat;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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
                row.createCell(0).setCellValue(nullSafe(dto.clienteNombreCompleto()));
                row.createCell(1).setCellValue(nullSafe(dto.clienteDocumento()));
                row.createCell(2).setCellValue(nullSafe(dto.vehiculoPatente()));
                row.createCell(3).setCellValue(nullSafe(dto.vehiculoModelo()));
                row.createCell(4).setCellValue(nullSafe(dto.vehiculoMarca()));

                Date fechaDesde = dto.fechaDesde();
                Date fechaHasta = dto.fechaHasta();

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
                double monto = dto.montoTotal() == null ? 0.0 : dto.montoTotal();
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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (AlquilerReporteDTO dto : datos) {
                table.addCell(valueOrDash(dto.clienteNombreCompleto()));
                table.addCell(valueOrDash(dto.clienteDocumento()));
                table.addCell(valueOrDash(dto.vehiculoPatente()));
                table.addCell(valueOrDash(dto.vehiculoModelo()));
                table.addCell(valueOrDash(dto.vehiculoMarca()));
                table.addCell(formatDate(dto.fechaDesde(), formatter));
                table.addCell(formatDate(dto.fechaHasta(), formatter));
                table.addCell(formatMonto(dto.montoTotal()));
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

    private String formatDate(Date date, SimpleDateFormat formatter) {
        return date == null ? "-" : formatter.format(date);
    }

    private String formatMonto(Double monto) {
        double safeMonto = monto == null ? 0.0 : monto;
        return String.format("%.2f", safeMonto);
    }
}
