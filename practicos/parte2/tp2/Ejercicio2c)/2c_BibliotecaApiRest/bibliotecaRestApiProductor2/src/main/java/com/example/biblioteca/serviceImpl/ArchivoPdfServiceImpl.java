package com.example.biblioteca.serviceImpl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.entities.ArchivoPdf;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.service.ArchivoPdfService;

@Service
public class ArchivoPdfServiceImpl extends BaseServiceImpl<ArchivoPdf, Long> implements ArchivoPdfService{
	
	@Value("${app.upload.dir}")
	private String uploadDir;

	public ArchivoPdfServiceImpl(BaseRepository<ArchivoPdf, Long> dao) {
		super(dao);
	}
	
	public void validar(ArchivoPdf archivo) throws Exception {
		//Si o si deber tener autores asociados
	}

	@Override
	public ArchivoPdf save(ArchivoPdf archivo) throws Exception {
		try {
			
			validar(archivo);
			
			return baseRepository.save(archivo);
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ArchivoPdf update(Long id, ArchivoPdf entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		return false;
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

}
