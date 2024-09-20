package org.somemate.demo.mbti.controller;

import org.somemate.demo.mbti.dto.Mbti;
import org.somemate.demo.mbti.service.MbtiService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/mbti")
public class MbtiController {
    MbtiService mbtiService;

    public MbtiController(MbtiService mbtiService) {
        this.mbtiService = mbtiService;
    }

    @GetMapping("/{mbti}")
    public Mbti getMbti(@PathVariable("mbti")String mbti) {

        Mbti mbtiInfo = null;
        System.out.println("requested mbti : "+mbti);

        try {
            mbtiInfo = mbtiService.getMbtiInfo(mbti);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mbtiInfo;
    }


}
