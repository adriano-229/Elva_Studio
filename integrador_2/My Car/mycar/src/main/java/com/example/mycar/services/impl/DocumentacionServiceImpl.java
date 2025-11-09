package com.example.mycar.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.mycar.dto.DocumentacionDTO;
import com.example.mycar.entities.Documentacion;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.services.DocumentacionService;
import com.example.mycar.services.mapper.BaseMapper;

import jakarta.transaction.Transactional;

@Service
public class DocumentacionServiceImpl extends BaseServiceImpl<Documentacion, DocumentacionDTO, Long> implements DocumentacionService{

	@Value("${app.upload.dir}")
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
			entity.setNombreArchivo(entityDto.getPathArchivo());
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

	@Override
	public String almacenarPdf(MultipartFile pdf) throws Exception {
		try {
	        // Guardar archivo en carpeta local
	        String rutaDestino = uploadDir; 
	        File carpeta = new File(rutaDestino);
	        if (!carpeta.exists()) carpeta.mkdirs();
	        
	        String nombreArchivo = System.currentTimeMillis() + "_" + pdf.getOriginalFilename();

	        Path pathDestino = Paths.get(rutaDestino, nombreArchivo);
	        
	        pdf.transferTo(pathDestino.toFile());
	        
	        return pathDestino.toString();

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al subir archivo");
	       
	    }
	  
	
	}

	@Override
	@Transactional
	public DocumentacionDTO update(Long id, DocumentacionDTO documentacion, MultipartFile archivo)
			throws Exception {
		
		try {
			
			validate(documentacion);
			
			Documentacion doc = convertToEntity(documentacion);
			
			doc.setObservacion(documentacion.getObservacion());
			doc.setTipoDocumentacion(documentacion.getTipoDocumentacion());
			
			//podria hacer que se elimine del directorio el archivo que estaba cargado antes
			if (archivo != null && !archivo.isEmpty()) {
		        String ruta = almacenarPdf(archivo);

		        doc.setNombreArchivo(documentacion.getNombreArchivo());
		        doc.setPathArchivo(ruta);
		        
		    }
			
			saveToRepository(doc);
			
			return baseMapper.toDto(doc);
		
		} catch (Exception e) {
			throw new Exception("Error al actualizar documentacion", e);
		}
		
		
	}

}
