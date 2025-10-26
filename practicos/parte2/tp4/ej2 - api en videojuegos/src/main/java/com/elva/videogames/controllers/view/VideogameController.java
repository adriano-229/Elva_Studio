package com.elva.videogames.controllers.view;

import com.elva.videogames.business.domain.entities.Videogame;
import com.elva.videogames.business.logic.services.GenreService;
import com.elva.videogames.business.logic.services.StudioService;
import com.elva.videogames.business.logic.services.VideogameService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Controller
public class VideogameController {


    private final VideogameService videogameService;
    private final GenreService genreService;
    private final StudioService studioService;

    public VideogameController(VideogameService videogameService, GenreService genreService, StudioService studioService) {
        this.videogameService = videogameService;
        this.genreService = genreService;
        this.studioService = studioService;
    }


    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Videogame> videogames = videogameService.findAllActive();
        model.addAttribute("videogames", videogames);
        return "views/home";
    }

    @GetMapping("/detail/{id}")
    public String videogameDetail(Model model, @PathVariable("id") Long id) {
        Videogame videogame = videogameService.findActiveById(id);
        model.addAttribute("videogame", videogame);
        return "views/videogameDetail";
    }

    // RequestParam is used to get the query parameter from the URL, and required = false makes it optional
    @GetMapping("/search")
    public String videogameSearch(Model model, @RequestParam(value = "query", required = false) String name) {
        List<Videogame> videogames = videogameService.findByContainsNameActiveIgnoreCase(name);
        model.addAttribute("videogames", videogames);
        model.addAttribute("query", name);
        return "views/searchResults";
    }

    @GetMapping("/crud")
    public String crudHome(Model model) {
        List<Videogame> videogames = videogameService.findAll();
        model.addAttribute("videogames", videogames);
        return "views/crudHome";
    }

    @GetMapping("/forms/videogame/{id}")
    public String videogameForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("genres", genreService.findAllActive());
        model.addAttribute("studios", studioService.findAllActive());
        if (id == 0) {
            Videogame newVideogame = new Videogame();
            // Set ID to 0 to indicate it's a new entity
            newVideogame.setId(0L);
            model.addAttribute("videogame", newVideogame);
        } else {
            Videogame existing = videogameService.findById(id);
            model.addAttribute("videogame", existing);
        }
        return "views/forms/videogame";
    }

    @PostMapping("/forms/videogame/{id}")
    public String videogameSubmit(
            @RequestParam("img") MultipartFile img,
            @Valid @ModelAttribute("videogame") Videogame videogame,
            BindingResult bindingResult,
            Model model,
            @PathVariable("id") Long id) throws IOException {

        model.addAttribute("genres", genreService.findAllActive());
        model.addAttribute("studios", studioService.findAllActive());

        if (bindingResult.hasErrors()) {
            return "views/forms/videogame";
        }

        String imageName;

        // Handle image validation and saving
        if (!img.isEmpty()) {

            if (!validateExtension(img)) {
                bindingResult.rejectValue("imageUrl", "Invalid.videogame.imageUrl", "Image format is not valid");
                return "views/forms/videogame";
            }

            if (img.getSize() > 2 * 1024 * 1024) {
                bindingResult.rejectValue("imageUrl", "Invalid.videogame.imageUrl", "Image size exceeds 15 MB limit");
                return "views/forms/videogame";
            }

            // Generate unique filename and save
            String extension = Objects.requireNonNull(img.getOriginalFilename())
                    .substring(img.getOriginalFilename().lastIndexOf("."));
            imageName = Calendar.getInstance().getTimeInMillis() + extension;
            final String UPLOAD_DIR = "/home/adriano/Pictures/videogames/";
            Path absolutePath = Paths.get(UPLOAD_DIR + "/" + imageName);
            Files.write(absolutePath, img.getBytes());
            videogame.setImageUrl(imageName);
        }

        if (id == 0) {
            if (img.isEmpty()) {
                bindingResult.rejectValue("imageUrl", "Invalid.videogame.imageUrl", "Image is required for new videogame");
                return "views/forms/videogame";
            }
            videogame.setId(null); // ensure it's treated as a new entity
            videogameService.saveOne(videogame);
        } else {
            videogameService.updateOne(id, videogame);
        }
        return "redirect:/crud";
    }


    @GetMapping("/delete/videogame/{id}")
    public String deleteVideogame(@PathVariable("id") Long id, Model model) {
        model.addAttribute("videogame", videogameService.findById(id));
        return "views/forms/deleteVideogame";
    }

    @PostMapping("/delete/videogame/{id}")
    public String deleteVideogameSubmit(@PathVariable("id") Long id) {
        videogameService.deleteById(id);
        return "redirect:/crud";
    }

    public boolean validateExtension(MultipartFile file) throws IOException {
        if (file.isEmpty()) return false;
        try (var is = file.getInputStream()) {
            return ImageIO.read(is) != null;
        }
    }
}