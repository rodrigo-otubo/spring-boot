package academy.devdojo.springbootessentials.controller;

import academy.devdojo.springbootessentials.domain.Anime;
import academy.devdojo.springbootessentials.requests.AnimePostRequestBody;
import academy.devdojo.springbootessentials.requests.AnimePutRequestBody;
import academy.devdojo.springbootessentials.service.AnimeService;
import academy.devdojo.springbootessentials.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
        return new ResponseEntity<>(animeService.save(animePostRequestBody),HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
