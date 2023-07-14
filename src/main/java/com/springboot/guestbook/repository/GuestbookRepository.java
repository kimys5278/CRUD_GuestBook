package com.springboot.guestbook.repository;

import com.springboot.guestbook.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook,Integer> {
    Page<Guestbook> findByTitleContaining(String searchKeyword , Pageable pageable);
}
