package com.springboot.guestbook.service;

import com.springboot.guestbook.entity.Guestbook;
import com.springboot.guestbook.repository.GuestbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class GuestbookService {
    @Autowired
    private GuestbookRepository guestbookRepository;
    //글 작성 처리
    public void write(Guestbook guestbook  )   {

        guestbookRepository.save(guestbook);
    }
    //게시물 리스트 처리
    public Page<Guestbook> guestbookList(Pageable pageable){

        return guestbookRepository.findAll(pageable);
    }

    public Page<Guestbook> guestbookSerachList(String searchKeyword,Pageable pageable){

        return guestbookRepository.findByTitleContaining(searchKeyword,pageable);
    }

    //특정 게시물 불러오기
    public Guestbook guestbookView(Integer id){

        return guestbookRepository.findById(id).get();
        //get()사용이유: get()을 사용하지 않으면 Optional 값으로 들어오기 때문이다.
    }
    //특정 게시글 삭제
    public void guestbookDelete(Integer id){

        guestbookRepository.deleteById(id);
    }

}
