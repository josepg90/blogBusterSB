package net.ausiasmarch.blogBusterSB;

import java.net.http.HttpClient;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")

public class PostController {

    @Autowired
    PostRepository oPostRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> get(@PathVariable(value = "id") Long id) {
        
        if(oPostRepository.existsById(id)){
        return new ResponseEntity<PostEntity>(oPostRepository.getById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
    
    @GetMapping("")
    public ResponseEntity<Page<PostEntity>> getPage(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable){
        
        Page<PostEntity> oPage=null;
        oPage = oPostRepository.findAll(oPageable);
        return new ResponseEntity<Page<PostEntity>>(oPage, HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        
        return new ResponseEntity<Long>(oPostRepository.count(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PostEntity> create(@RequestBody PostEntity oPostEntity) {
        try {
            PostEntity _datos = oPostRepository.save(new PostEntity(oPostEntity.getTitulo(), oPostEntity.getCuerpo(), oPostEntity.getFecha(), oPostEntity.getEtiquetas(), oPostEntity.getVisible()));
            return new ResponseEntity<>(_datos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostEntity> update(@PathVariable("id") long id, @RequestBody PostEntity oPostEntity) {
        Optional<PostEntity> datos = oPostRepository.findById(id);

        if (datos.isPresent()) {
            PostEntity oDatos = datos.get();
            oDatos.setTitulo(oPostEntity.getTitulo());
            oDatos.setCuerpo(oPostEntity.getCuerpo());
            oDatos.setFecha(oPostEntity.getFecha());
            oDatos.setEtiquetas(oPostEntity.getEtiquetas());
            oDatos.setVisible(oPostEntity.getVisible());
            return new ResponseEntity<>(oPostRepository.save(oDatos), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            oPostRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
