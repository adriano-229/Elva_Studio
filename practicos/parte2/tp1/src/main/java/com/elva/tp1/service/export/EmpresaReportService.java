package com.elva.tp1.service.export;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.domain.Empresa;
import com.elva.tp1.domain.Localidad;
import com.elva.tp1.service.EmpresaService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class EmpresaReportService {

    private static final String[] HEADERS = {"ID", "Razón Social", "Dirección", "Localidad", "Activo"};

    private final EmpresaService empresaService;

    public EmpresaReportService(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    public byte[] generarExcel() {
        List<Empresa> empresas = empresaService.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Empresas");

            CellStyle headerStyle = createHeaderStyle(workbook);
            createHeaderRow(sheet, headerStyle);

            int rowIdx = 1;
            for (Empresa empresa : empresas) {
                Row row = sheet.createRow(rowIdx++);
                fillRow(row, empresa);
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo generar el Excel de empresas", e);
        }
    }

    private void fillRow(Row row, Empresa empresa) {
        row.createCell(0).setCellValue(empresa.getId() != null ? empresa.getId().toString() : "");
        row.createCell(1).setCellValue(defaultString(empresa.getRazonSocial()));
        row.createCell(2).setCellValue(buildDireccion(empresa.getDireccion()));

        Localidad localidad = empresa.getDireccion() != null ? empresa.getDireccion().getLocalidad() : null;
        row.createCell(3).setCellValue(localidad != null && localidad.getNombre() != null ? localidad.getNombre() : "");
        row.createCell(4).setCellValue(Boolean.TRUE.equals(empresa.getActivo()) ? "Sí" : "No");
    }

    private void createHeaderRow(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
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

        return builder.toString();
    }
}
