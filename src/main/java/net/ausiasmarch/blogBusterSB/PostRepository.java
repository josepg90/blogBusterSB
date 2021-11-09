package net.ausiasmarch.blogBusterSB;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long>{
    
}
