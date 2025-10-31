package com.elva.videogames.configs;

import com.elva.videogames.business.domain.entities.Genre;
import com.elva.videogames.business.domain.entities.Studio;
import com.elva.videogames.business.domain.entities.Videogame;
import com.elva.videogames.business.persistence.repositories.GenreRepository;
import com.elva.videogames.business.persistence.repositories.StudioRepository;
import com.elva.videogames.business.persistence.repositories.VideogameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final StudioRepository studioRepository;
    private final GenreRepository genreRepository;
    private final VideogameRepository videogameRepository;

    public DataInitializer(StudioRepository studioRepository, GenreRepository genreRepository, VideogameRepository videogameRepository) {
        this.studioRepository = studioRepository;
        this.genreRepository = genreRepository;
        this.videogameRepository = videogameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if database is empty
        if (studioRepository.count() == 0 && genreRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Create Studios
        Studio studio1 = new Studio();
        studio1.setName("Rockstar Games");
        studioRepository.save(studio1);

        Studio studio2 = new Studio();
        studio2.setName("Naughty Dog");
        studioRepository.save(studio2);

        Studio studio3 = new Studio();
        studio3.setName("FromSoftware");
        studioRepository.save(studio3);

        Studio studio4 = new Studio();
        studio4.setName("CD Projekt Red");
        studioRepository.save(studio4);

        // Create Genres
        Genre genre1 = new Genre();
        genre1.setName("Action");
        genreRepository.save(genre1);

        Genre genre2 = new Genre();
        genre2.setName("RPG");
        genreRepository.save(genre2);

        Genre genre3 = new Genre();
        genre3.setName("Adventure");
        genreRepository.save(genre3);

        Genre genre4 = new Genre();
        genre4.setName("Shooter");
        genreRepository.save(genre4);

        // Create Videogames
        Videogame vg1 = new Videogame();
        vg1.setName("Grand Theft Auto V");
        vg1.setDescription("Open world action-adventure game set in a fictional Los Santos");
        vg1.setPrice(59.99);
        vg1.setStock((short) 100);
        vg1.setReleaseDate(dateFormat.parse("2013-09-17"));
        vg1.setStudio(studio1);
        vg1.setGenre(genre1);
        vg1.setImageUrl("/images/gta5.jpg");
        videogameRepository.save(vg1);

        Videogame vg2 = new Videogame();
        vg2.setName("The Last of Us Part II");
        vg2.setDescription("Action-adventure game featuring intense combat and emotional storytelling");
        vg2.setPrice(49.99);
        vg2.setStock((short) 80);
        vg2.setReleaseDate(dateFormat.parse("2020-06-19"));
        vg2.setStudio(studio2);
        vg2.setGenre(genre3);
        vg2.setImageUrl("/images/thelastofus2.jpg");
        videogameRepository.save(vg2);

        Videogame vg3 = new Videogame();
        vg3.setName("Elden Ring");
        vg3.setDescription("Dark fantasy action RPG with challenging combat and open-world exploration");
        vg3.setPrice(59.99);
        vg3.setStock((short) 120);
        vg3.setReleaseDate(dateFormat.parse("2022-02-25"));
        vg3.setStudio(studio3);
        vg3.setGenre(genre2);
        vg3.setImageUrl("/images/eldenring.jpg");
        videogameRepository.save(vg3);

        Videogame vg4 = new Videogame();
        vg4.setName("Cyberpunk 2077");
        vg4.setDescription("Sci-fi action RPG set in a dystopian night city with immersive gameplay");
        vg4.setPrice(39.99);
        vg4.setStock((short) 150);
        vg4.setReleaseDate(dateFormat.parse("2020-12-10"));
        vg4.setStudio(studio4);
        vg4.setGenre(genre2);
        vg4.setImageUrl("/images/cyberpunk2077.jpg");
        videogameRepository.save(vg4);

        Videogame vg5 = new Videogame();
        vg5.setName("Red Dead Redemption 2");
        vg5.setDescription("Immersive western action-adventure with dynamic open world gameplay");
        vg5.setPrice(44.99);
        vg5.setStock((short) 90);
        vg5.setReleaseDate(dateFormat.parse("2018-10-26"));
        vg5.setStudio(studio1);
        vg5.setGenre(genre1);
        vg5.setImageUrl("/images/rdr2.jpg");
        videogameRepository.save(vg5);

        System.out.println("Database initialized with sample data!");
    }
}

