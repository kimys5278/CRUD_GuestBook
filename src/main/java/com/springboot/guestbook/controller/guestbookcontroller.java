package com.springboot.guestbook.controller;

import com.springboot.guestbook.entity.Guestbook;
import com.springboot.guestbook.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class guestbookcontroller {
    @Autowired
    private  GuestbookService guestbookService;

    @GetMapping("/guestbook/write") //localhos:8080
    public String  guestbookWriteForm(){

        return "guestbookWrite";
    }
    @PostMapping("/guestbook/writePro")
    public String guestbookWritePro(Guestbook guestbook , Model model) {

        guestbookService.write(guestbook);

        System.out.println("제목 : " + guestbook.getTitle());
        System.out.println("내용 : " + guestbook.getContent());

        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/guestbook/list");


        return "message";
    }
    @GetMapping("/guestbook/list")
    public String guestbookList(Model model,
                                @PageableDefault(page = 0, size=10, sort="id",direction = Sort.Direction.DESC) Pageable pageable,
                                String searchKeyword){

       Page<Guestbook> list = null;

        if(searchKeyword == null){
             list = guestbookService.guestbookList(pageable);
        }else{
             list = guestbookService.guestbookSerachList(searchKeyword,pageable);
        }


        int currentPage = list.getNumber() + 1; // 현재 페이지 번호
        int totalPages = list.getTotalPages(); // 전체 페이지 수

        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        model.addAttribute("list", list);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        int nowPage = list.getPageable().getPageNumber();

//        //getPageable()로 가져온 getPageNumber() 페이지 넘버를 가져올 수 있다.
//        int startPage = Math.max(nowPage - 4,1);
//        int endPage = Math.min(nowPage+5, list.getTotalPages());
//        //현재 페이지가 1 이면 1-4 = -3 이기때문에 Math 클래스의 max를
//
//        model.addAttribute("list",list);
//        model.addAttribute("nowPage",nowPage+1);
//        // +1 하는이유는 페이지는 0으로 시작하기때문에
//        model.addAttribute("startPage",startPage);
//        model.addAttribute("endPage",endPage);


        return "guestbookList";
    }
    @GetMapping("/guestbook/view")
    public String geuestbookView(Model model,Integer id){
        model.addAttribute("guestbook",guestbookService.guestbookView(id));

        return "guestbookView";
    }
    @GetMapping("/guestbook/delete")
    public String guestbookDelete(Integer id, Model model){

        guestbookService.guestbookDelete(id);

        model.addAttribute("message","글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl","/guestbook/list");

        return "message";
    }

    @GetMapping("/guestbook/modify/{id}")
    public String guestbookModify(@PathVariable("id") Integer id,
                                  Model model){
        model.addAttribute("guestbook",guestbookService.guestbookView(id));

        return "guestbookModify";
    }

    @PostMapping("/guestbook/update/{id}")
    public String guestbookUpdate(@PathVariable("id") Integer id, Guestbook guestbook,
                                  Model model ) {

        Guestbook guestbookTemp = guestbookService.guestbookView(id);
        guestbookTemp.setTitle(guestbook.getTitle());
        guestbookTemp.setContent(guestbook.getContent());

        guestbookService.write(guestbookTemp);

        model.addAttribute("message","글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl","/guestbook/list");



        return "message";
    }

}
