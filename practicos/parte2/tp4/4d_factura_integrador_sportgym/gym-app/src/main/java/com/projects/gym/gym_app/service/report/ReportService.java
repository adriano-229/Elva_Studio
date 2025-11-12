package com.projects.gym.gym_app.service.report;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.ReportBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.ComponentBuilders;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;

import com.projects.gym.gym_app.domain.DetalleFactura;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.service.impl.FacturaServiceImpl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@Service
public class ReportService {
	
	@Autowired
	private FacturaServiceImpl facturaService;
	
	public byte[] generarReport(Factura factura) throws JRException {
	    try {
	        BigDecimal subtotal = BigDecimal.ZERO;
	        for (DetalleFactura detalle : factura.getDetalles()) {
	            BigDecimal valor = detalle.getCuotaMensual().getValorCuota().getValorCuota();
	            if (valor != null) {
	                subtotal = subtotal.add(valor);
	            }
	        }

	        JasperReportBuilder report = diseñarReporte(factura, subtotal);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        report.toPdf(baos);
	        return baos.toByteArray();

	    } catch (DRException e) {
	        e.printStackTrace();
	        throw new JRException("Error al generar reporte PDF", e);
	    }
	}


	public JasperReportBuilder diseñarReporte(Factura factura, BigDecimal subtotal) {
	    StyleBuilders stl = DynamicReports.stl;
	    ComponentBuilders cmp = DynamicReports.cmp;
	    
	    // ====== Estilos ======
	    var boldStyle = stl.style().bold();
	    var bold12Style = stl.style(boldStyle).setFontSize(12);
	    var bold18Style = stl.style(boldStyle).setFontSize(18);
	    var columnTitleStyle = stl.style(boldStyle)
	            .setBorder(stl.pen1Point())
	            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
	            .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
	    var columnStyle = stl.style()
	            .setBorder(stl.pen1Point())
	            .setPadding(2);
	    
	    JasperReportBuilder report = report();

	    BigDecimal iva = subtotal.multiply(new BigDecimal("0.21"));
	    BigDecimal total = subtotal.add(iva);
	    String fechaFormateada = factura.getFechaFactura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    String nroFactura = String.format("%08d", factura.getNumeroFactura());

	    // ====== Construcción del reporte ======
	    report
	        // Título
	        .title(
	            cmp.horizontalList()
	                .add(
	                    cmp.image("src/main/resources/logo_sportgym.png")
	                        .setFixedDimension(80, 80),
	                    cmp.verticalList(
	                        cmp.text(factura.getSocio().getSucursal().getEmpresa().getNombre())
	                            .setStyle(bold18Style),
	                        cmp.text("CUIT: 12-17259372-01")
	                            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
	                        cmp.text(factura.getSocio().getSucursal().getDireccion().getDireccionCompleta())
	                            .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
	                    ).setFixedWidth(400)
	                ),
	            cmp.verticalGap(10),
	            
	            // Datos de la factura
	            cmp.horizontalList()
	                .add(
	                    cmp.text("Factura Nº: " + nroFactura).setStyle(boldStyle),
	                    cmp.text("Fecha: " + fechaFormateada)
	                        .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
	                ),
	            cmp.verticalGap(10),

	            // Datos del socio
	            cmp.verticalList(
	                cmp.text("Datos del socio:").setStyle(boldStyle),
	                cmp.text("N° Socio: " + factura.getSocio().getNumeroSocio()),
	                cmp.text("Nombre: " + factura.getSocio().getNombre() + " " + factura.getSocio().getApellido()),
	                cmp.text("DNI: " + factura.getSocio().getNumeroDocumento()),
	                cmp.text("Domicilio: " + factura.getSocio().getDireccion().getDireccionCompleta()),
	                cmp.text("Condición IVA: Consumidor Final")
	            )
	            .setStyle(stl.style()
	                .setBorder(stl.pen1Point())
	                .setPadding(5)
	            )
	            .setGap(2),
	            
	            cmp.verticalGap(10),
	            
	            // Forma de pago
	            cmp.text("Forma de pago: " + factura.getFormaDePago().getTipoPago())
	                .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
	            cmp.verticalGap(10)
	        )

	        // Columnas de detalle
	        .columns(
	            Columns.column("Concepto", "cuotaMensual.descripcion", DataTypes.stringType())
	                .setStyle(columnStyle)
	                .setTitleStyle(columnTitleStyle)
	                .setFixedWidth(280),
	            Columns.column("Precio Unitario", "cuotaMensual.valorCuota.valorCuota", DataTypes.bigDecimalType())
	                .setStyle(columnStyle)
	                .setTitleStyle(columnTitleStyle)
	                .setFixedWidth(100),
	            Columns.column("Importe", "cuotaMensual.valorCuota.valorCuota", DataTypes.bigDecimalType())
	                .setStyle(columnStyle)
	                .setTitleStyle(columnTitleStyle)
	                .setFixedWidth(100)
	        )
	        
	        // Data source correctamente aplicado
	        .setDataSource(new JRBeanCollectionDataSource(factura.getDetalles()))
	        
	        // Totales
	        .summary(
	            cmp.verticalGap(15),
	            cmp.horizontalList()
	                .add(
	                    cmp.text("Subtotal:").setStyle(boldStyle)
	                        .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
	                        .setFixedWidth(380),
	                    cmp.text("$" + subtotal).setStyle(boldStyle)
	                ),
	            cmp.horizontalList()
	                .add(
	                    cmp.text("IVA (21%):").setStyle(boldStyle)
	                        .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
	                        .setFixedWidth(380),
	                    cmp.text("$" + iva).setStyle(boldStyle)
	                ),
	            cmp.horizontalList()
	                .add(
	                    cmp.text("TOTAL:").setStyle(bold12Style)
	                        .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
	                        .setFixedWidth(380),
	                    cmp.text("$" + total).setStyle(bold12Style)
	                )
	        )
	        
	        // Pie de página y márgenes
	        .pageFooter(cmp.pageXofY())
	        .setPageMargin(DynamicReports.margin(30));
	    
	    return report;
	}


	
	public byte[] obtenerFacturaPDF(String facturaId) throws Exception {
	    String reportsDir = "C:/reports/pdf/";
	    File folder = new File(reportsDir);
	    if (!folder.exists()) folder.mkdirs();

	    String fileName = "factura_" + facturaId + ".pdf";
	    File pdfFile = new File(reportsDir + fileName);

	    if (pdfFile.exists()) {
	        return Files.readAllBytes(pdfFile.toPath());
	    } else {
	        Factura factura = facturaService.buscarPorIdFactura(facturaId);// obtener de BD
	        byte[] report = generarReport(factura); // genera con DynamicReports
	        Files.write(pdfFile.toPath(), report);
	        return report;
	    }
	}



}
