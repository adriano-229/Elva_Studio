package com.elva.tp1.service;

import com.elva.tp1.domain.*;
import com.elva.tp1.repository.EmpresaRepository;
import com.elva.tp1.repository.ProveedorRepository;
import com.lowagie.text.pdf.PdfPTable;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    private final ProveedorRepository proveedorRepo;
    private final EmpresaRepository empresaRepo;

    public ExportService(ProveedorRepository proveedorRepo, EmpresaRepository empresaRepo) {
        this.proveedorRepo = proveedorRepo;
        this.empresaRepo = empresaRepo;
    }

    private static PdfPTable getPdfPTable(List<Proveedor> proveedores) {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Apellido");
        table.addCell("CUIT");

        for (Proveedor p : proveedores) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getNombre() == null ? "" : p.getNombre());
            table.addCell(p.getApellido() == null ? "" : p.getApellido());
            table.addCell(p.getCuit() == null ? "" : p.getCuit());
        }
        return table;
    }

    public byte[] generarPdfProveedores() throws IOException {
        List<Proveedor> proveedores = proveedorRepo.findAll(Sort.by("nombre"));

        // Uso OpenPDF
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            com.lowagie.text.pdf.PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new com.lowagie.text.Paragraph("Listado de Proveedores"));
            document.add(new com.lowagie.text.Paragraph(" "));
            PdfPTable table = getPdfPTable(proveedores);
            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (com.lowagie.text.DocumentException e) {
            throw new IOException("Error generando PDF", e);
        }
    }

    public byte[] generarExcelEmpresas() throws IOException {
        List<Empresa> empresas = empresaRepo.findAll(Sort.by("razonSocial"));

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Empresas");
            int rowIdx = 0;

            // Cabecera
            Row header = sheet.createRow(rowIdx++);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Razón Social");
            header.createCell(2).setCellValue("Pais");
            header.createCell(3).setCellValue("Provincia");
            header.createCell(4).setCellValue("Departamento");
            header.createCell(5).setCellValue("Dirección");

            // Filas
            for (Empresa e : empresas) {
                Direccion d = e.getDireccion();
                Departamento dep = d != null ? d.getDepartamento() : null;
                Provincia prov = dep != null ? dep.getProvincia() : null;
                Pais pais = prov != null ? prov.getPais() : null;

                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(e.getId() == null ? 0 : e.getId());
                row.createCell(1).setCellValue(e.getRazonSocial() == null ? "" : e.getRazonSocial());
                row.createCell(2).setCellValue(pais != null ? pais.getNombre() : "");
                row.createCell(3).setCellValue(prov != null ? prov.getNombre() : "");
                row.createCell(4).setCellValue(dep != null ? dep.getNombre() : "");
                row.createCell(5).setCellValue(d != null ? d.getCalle() + " " + d.getAltura() : "");
            }

            // Auto-ajusta columnas (opcional)
            for (int i = 0; i < 4; i++) sheet.autoSizeColumn(i);

            workbook.write(baos);
            return baos.toByteArray();
        }
    }
}
