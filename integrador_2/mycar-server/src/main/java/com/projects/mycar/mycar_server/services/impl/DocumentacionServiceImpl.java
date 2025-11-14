package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.DocumentacionDTO;
import com.projects.mycar.mycar_server.entities.Documentacion;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.services.DocumentacionService;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class DocumentacionServiceImpl extends BaseServiceImpl<Documentacion, DocumentacionDTO, Long> implements DocumentacionService {

    private static final Logger log = LoggerFactory.getLogger(DocumentacionServiceImpl.class);
    @Value("${app.upload.docs.dir}")
    private String uploadDir;

    public DocumentacionServiceImpl(BaseRepository<Documentacion, Long> baseRepository,
                                    BaseMapper<Documentacion, DocumentacionDTO> baseMapper) {
        super(baseRepository, baseMapper);

    }

    @Override
    public List<DocumentacionDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Documentacion updateEntityFromDto(Documentacion entity, DocumentacionDTO entityDto) throws Exception {

        if (entityDto.getNombreArchivo() != null) {
            entity.setNombreArchivo(entityDto.getNombreArchivo());
        }

        if (entityDto.getPathArchivo() != null) {
            entity.setPathArchivo(entityDto.getPathArchivo()); // corregido: antes seteaba nombreArchivo
        }

        entity.setObservacion(entityDto.getObservacion());
        entity.setTipoDocumentacion(entityDto.getTipoDocumentacion());

        return entity;


    }

    @Override
    protected void validate(DocumentacionDTO entityDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(DocumentacionDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterSave(Documentacion entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterUpdate(Documentacion entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeDelete(Documentacion doc) throws Exception {
        // Movemos el archivo físico (si existe) a la carpeta de eliminados
        if (doc.getPathArchivo() != null) {
            Path origen = Paths.get(doc.getPathArchivo());
            Path destino = Paths.get(uploadDir, "Eliminados", doc.getNombreArchivo());

            if (Files.exists(origen)) {
                Files.createDirectories(destino.getParent());
                Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);

                doc.setPathArchivo(destino.toString());
            }
        }


    }

    @Override
    protected void afterDelete(Documentacion entity) throws Exception {
        // TODO Auto-generated method stub

    }

    /*@Override
    public String almacenarPdf(MultipartFile pdf) throws Exception {
        if (pdf == null || pdf.isEmpty()) {
            throw new IllegalArgumentException("El archivo PDF está vacío");
        }
        try {
            String rutaDestino = uploadDir;
            File carpeta = new File(rutaDestino);
            if (!carpeta.exists() && !carpeta.mkdirs()) {
                throw new IllegalStateException("No se pudo crear el directorio de subida: " + rutaDestino);
            }
            String original = pdf.getOriginalFilename();
            String seguro = (original == null ? "archivo" : original).replaceAll("[^a-zA-Z0-9._-]", "_");
            String nombreArchivo = System.currentTimeMillis() + "_" + seguro;
            Path pathDestino = Paths.get(rutaDestino, nombreArchivo).normalize();
            if (!pathDestino.startsWith(Paths.get(rutaDestino).toAbsolutePath())) {
                throw new SecurityException("Nombre de archivo inválido");
            }
            pdf.transferTo(pathDestino.toFile());
            log.info("Archivo PDF almacenado en {}", pathDestino);
            return pathDestino.toString();
        } catch (Exception e) {
            log.error("Error al subir archivo PDF", e);
            throw new Exception("Error al subir archivo", e);
        }
    }*/

    @Override
    public String almacenarPdf(MultipartFile pdf) throws Exception {
        if (pdf == null || pdf.isEmpty()) {
            throw new IllegalArgumentException("El archivo PDF está vacío");
        }
        try {
            File carpeta = new File(uploadDir);
            if (!carpeta.exists() && !carpeta.mkdirs()) {
                throw new IllegalStateException("No se pudo crear el directorio: " + uploadDir);
            }

            String original = pdf.getOriginalFilename();
            String seguro = (original == null ? "archivo" : original).replaceAll("[^a-zA-Z0-9._-]", "_");
            String nombreArchivo = System.currentTimeMillis() + "_" + seguro;

            Path pathDestino = Paths.get(uploadDir, nombreArchivo).normalize();
            pdf.transferTo(pathDestino.toFile());

            log.info("Archivo PDF almacenado en {}", pathDestino);
            return nombreArchivo; // 🔥 DEVOLVEMOS SOLO EL NOMBRE
        } catch (Exception e) {
            log.error("Error al subir archivo PDF", e);
            throw new Exception("Error al subir archivo", e);
        }
    }


    @Override
    @Transactional
    public DocumentacionDTO updateDocumentacion(Long id, DocumentacionDTO documentacion, MultipartFile archivo)
            throws Exception {

        try {
            System.out.println("ESTOY EN DOCUMENTACION CONTROLLER - UPDATE");
            validate(documentacion);

            Documentacion doc = convertToEntity(documentacion);

            doc.setObservacion(documentacion.getObservacion());
            doc.setTipoDocumentacion(documentacion.getTipoDocumentacion());

            //podria hacer que se elimine del directorio el archivo que estaba cargado antes
            if (archivo != null && !archivo.isEmpty()) {

                String nombreArchivo = almacenarPdf(archivo);
                doc.setNombreArchivo(nombreArchivo);
                doc.setPathArchivo(uploadDir + File.separator + nombreArchivo);


                doc.setNombreArchivo(nombreArchivo);
            }

            saveToRepository(doc);

            return baseMapper.toDto(doc);

        } catch (Exception e) {
            throw new Exception("Error al actualizar documentacion", e);
        }


    }

}
