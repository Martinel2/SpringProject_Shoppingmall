package com.shoppingmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        // 여기서 query는 요청 파라미터의 이름입니다.
        // 예를 들어, /search?query=검색어 형식으로 요청이 들어온다면,
        // "검색어" 부분이 query 매개변수로 전달됩니다.

        // 이후 검색어에 따라 데이터를 조회하거나 다른 비즈니스 로직을 수행할 수 있습니다.
        // 이 예제에서는 간단히 검색어를 모델에 추가하여 검색결과 페이지로 전달합니다.
        model.addAttribute("query", query);

        // 검색결과를 표시할 템플릿 이름을 반환합니다.
        return "searchResult"; // search-result.html과 같은 템플릿 파일을 찾게 됩니다.
    }
}

